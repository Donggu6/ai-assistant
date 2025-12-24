package com.deongeon.ai.auth.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.deongeon.ai.auth.domain.AppUser;
import com.deongeon.ai.auth.dto.LoginRequest;
import com.deongeon.ai.auth.dto.RegisterRequest;
import com.deongeon.ai.auth.dto.response.LoginResponse;
import com.deongeon.ai.auth.repository.AppUserRepository;
import com.deongeon.ai.auth.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AppUserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public AppUser register(RegisterRequest req) {

		if (userRepository.findByEmail(req.getEmail()).isPresent()) {
			throw new RuntimeException("이미 존재하는 이메일입니다.");
		}

		AppUser user = new AppUser();
		user.setEmail(req.getEmail());
		user.setPassword(encoder.encode(req.getPassword()));

		return userRepository.save(user);
	}

	public LoginResponse login(LoginRequest req) {

		AppUser user = userRepository.findByEmail(req.getEmail())
				.orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

		if (!encoder.matches(req.getPassword(), user.getPassword())) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}

		// 🔥 여기서 email만 넘겨주는 게 핵심
		String accessToken = jwtTokenProvider.createToken(user.getEmail());

		return LoginResponse.builder()
				.accessToken(accessToken)
				.refreshToken(null) // 나중에 RefreshToken 붙이면 변경
				.userId(user.getId())
				.email(user.getEmail())
				.build();
	}
}
