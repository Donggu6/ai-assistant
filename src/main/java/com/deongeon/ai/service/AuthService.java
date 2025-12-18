package com.deongeon.ai.service;

import com.deongeon.ai.domain.AppUser;
import com.deongeon.ai.domain.RefreshToken;
import com.deongeon.ai.dto.LoginRequest;
import com.deongeon.ai.dto.LoginResponse;
import com.deongeon.ai.dto.RegisterRequest;
import com.deongeon.ai.dto.RegisterResponse;
import com.deongeon.ai.repository.AppUserRepository;
import com.deongeon.ai.repository.RefreshTokenRepository;
import com.deongeon.ai.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

@Service
public class AuthService {

	private final AppUserRepository userRepo;
	private final RefreshTokenRepository refreshRepo;
	private final PasswordEncoder encoder;
	private final JwtTokenProvider jwt;

	// 🔹 반드시 선언돼야 하는 필드들
	private static final long REFRESH_TTL_SECONDS = 14L * 24 * 60 * 60; // 14일
	private final SecureRandom random = new SecureRandom();

	public AuthService(AppUserRepository userRepo, RefreshTokenRepository refreshRepo, PasswordEncoder encoder,
			JwtTokenProvider jwt) {
		this.userRepo = userRepo;
		this.refreshRepo = refreshRepo;
		this.encoder = encoder;
		this.jwt = jwt;
	}

	/*
	 * ===================== 회원가입 =====================
	 */
	public RegisterResponse register(RegisterRequest req) {

		if (userRepo.findByEmail(req.email()).isPresent()) {
			throw new RuntimeException("EMAIL_ALREADY_EXISTS");
		}

		AppUser user = new AppUser();
		user.setEmail(req.email());
		user.setPassword(encoder.encode(req.password()));
		// 기본값 (없으면 주석)
		// user.setRole(Role.USER);
		// user.setPlanType("FREE");
		// user.setUsageCount(0);

		AppUser saved = userRepo.save(user);
		String role = (saved.getRole() == null) ? "USER" : saved.getRole().name();

		return new RegisterResponse(saved.getId(), saved.getEmail(), role);
	}

	/*
	 * ===================== 로그인 (Access + Refresh 발급) =====================
	 */
	public LoginResponse login(LoginRequest req) {

		AppUser user = userRepo.findByEmail(req.email()).orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

		if (!encoder.matches(req.password(), user.getPassword())) {
			throw new RuntimeException("INVALID_PASSWORD");
		}

		String accessToken = jwt.createToken(user.getEmail());
		long expiresAt = jwt.getAccessTokenExpiresAtEpochMs();

		String refreshToken = generateRefreshToken();
		RefreshToken rt = new RefreshToken();
		rt.setToken(refreshToken);
		rt.setUser(user);
		rt.setExpiresAt(Instant.now().plusSeconds(REFRESH_TTL_SECONDS));
		rt.setRevoked(false);

		refreshRepo.save(rt);

		return new LoginResponse("Bearer", accessToken, expiresAt, refreshToken);
	}

	/*
	 * ===================== Refresh (회전 방식) =====================
	 */
	public LoginResponse refresh(String refreshToken) {

		RefreshToken rt = refreshRepo.findByToken(refreshToken)
				.orElseThrow(() -> new RuntimeException("REFRESH_NOT_FOUND"));

		if (rt.isRevoked() || rt.getExpiresAt().isBefore(Instant.now())) {
			throw new RuntimeException("REFRESH_INVALID");
		}

		// 기존 토큰 폐기
		rt.setRevoked(true);
		refreshRepo.save(rt);

		AppUser user = rt.getUser();

		String newAccess = jwt.createToken(user.getEmail());
		long expiresAt = jwt.getAccessTokenExpiresAtEpochMs();

		String newRefresh = generateRefreshToken();
		RefreshToken newRt = new RefreshToken();
		newRt.setToken(newRefresh);
		newRt.setUser(user);
		newRt.setExpiresAt(Instant.now().plusSeconds(REFRESH_TTL_SECONDS));
		newRt.setRevoked(false);

		refreshRepo.save(newRt);

		return new LoginResponse("Bearer", newAccess, expiresAt, newRefresh);
	}

	/*
	 * ===================== 로그아웃 =====================
	 */
	public void logout(String refreshToken) {
		refreshRepo.findByToken(refreshToken).ifPresent(rt -> {
			rt.setRevoked(true);
			refreshRepo.save(rt);
		});
	}

	/*
	 * ===================== 내부 유틸 =====================
	 */
	private String generateRefreshToken() {
		byte[] bytes = new byte[48];
		random.nextBytes(bytes);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	}
}
