package com.deongeon.ai.auth.oauth;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.deongeon.ai.auth.jwt.JwtProvider;
import com.deongeon.ai.auth.service.TwoFactorService;
import com.deongeon.ai.user.domain.AppUser;
import com.deongeon.ai.user.repository.AppUserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final AppUserRepository userRepo;
    private final TwoFactorService twoFactorService;
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        // 1) 소셜 로그인 이메일 추출
        String email = OAuth2EmailExtractor.extract(authentication);

        // 2) 유저 조회 또는 생성
        AppUser user = userRepo.findByEmail(email)
                .orElseGet(() -> {
                    AppUser u = new AppUser();
                    u.setEmail(email);
                    u.setPassword("{OAUTH2}");
                    u.setPlan(com.deongeon.ai.user.domain.Plan.FREE);
                    u.setSubscriptionStatus(
                        com.deongeon.ai.user.domain.SubscriptionStatus.ACTIVE
                    );
                    u.setTwoFactorEnabled(false); // 기본값
                    return userRepo.save(u);
                });

        // 3) 2FA 사용자인 경우 → 이메일 코드 발송 + 2FA 페이지 이동
        if (user.isTwoFactorEnabled()) {
            twoFactorService.createChallenge(user.getEmail());

            String redirect = "/2fa.html?email=" +
                    URLEncoder.encode(user.getEmail(), StandardCharsets.UTF_8);

            response.sendRedirect(redirect);
            return;
        }

        // 4) 2FA 미사용 → 바로 JWT 발급
        String token = jwtProvider.createAccessToken(
                user.getEmail(),
                user.getPlan().name()
        );

        String redirect = "/dashboard.html?token=" +
                URLEncoder.encode(token, StandardCharsets.UTF_8);

        response.sendRedirect(redirect);
    }
}
