package com.deongeon.ai.auth.service;

import com.deongeon.ai.auth.domain.PasswordResetToken;
import com.deongeon.ai.auth.dto.*;
import com.deongeon.ai.auth.jwt.JwtProvider;
import com.deongeon.ai.user.domain.AppUser;
import com.deongeon.ai.user.domain.Plan;
import com.deongeon.ai.user.domain.SubscriptionStatus;
import com.deongeon.ai.user.repository.AppUserRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final TwoFactorService twoFactorService;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest req) {
        userRepository.findByEmail(req.getEmail()).ifPresent(u -> {
            throw new RuntimeException("이미 존재하는 이메일");
        });

        AppUser u = new AppUser();
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setPlan(Plan.FREE);
        u.setSubscriptionStatus(SubscriptionStatus.NONE);
        u.setTwoFactorEnabled(req.isTwoFactorEnabled());

        userRepository.save(u);
    }

    public LoginResponse login(LoginRequest request) {
        AppUser user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        // OAuth 계정은 일반 로그인 막고 싶으면 여기서 막아도 됨
        if ("{OAUTH2}".equals(user.getPassword())) {
            throw new RuntimeException("OAuth 계정입니다. 소셜 로그인 사용");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호 틀림");
        }

        if (user.isTwoFactorEnabled()) {
            twoFactorService.createChallenge(user.getEmail());
            String twoFaToken = jwtProvider.createTwoFactorToken(user.getEmail());
            return new LoginResponse(null, user.getPlan().name(), true, twoFaToken);
        }

        String accessToken = jwtProvider.createAccessToken(user.getEmail(), user.getPlan().name());
        return new LoginResponse(accessToken, user.getPlan().name(), false, null);
    }

    public LoginResponse verify2fa(Verify2faRequest request) {
        if (!jwtProvider.validateToken(request.getTwoFactorToken())) {
            throw new RuntimeException("토큰 유효하지 않음");
        }
        if (!"PENDING_2FA".equals(jwtProvider.getType(request.getTwoFactorToken()))) {
            throw new RuntimeException("2FA 토큰 아님");
        }

        String email = jwtProvider.getEmail(request.getTwoFactorToken());

        if (!twoFactorService.verify(email, request.getCode())) {
            throw new RuntimeException("2FA 코드 틀림");
        }

        twoFactorService.clear(email);

        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        String accessToken = jwtProvider.createAccessToken(user.getEmail(), user.getPlan().name());
        return new LoginResponse(accessToken, user.getPlan().name(), false, null);
    }
    
    public void sendResetPassword(String email, Object emailService) {

        AppUser user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일"));

        String token = UUID.randomUUID().toString();

        PasswordResetToken t = new PasswordResetToken();
        t.setEmail(email);
        t.setToken(token);
        t.setExpiresAt(LocalDateTime.now().plusMinutes(30));

    }

}
