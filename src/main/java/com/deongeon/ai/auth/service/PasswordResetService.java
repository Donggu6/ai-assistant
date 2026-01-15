//package com.deongeon.ai.auth.service;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.deongeon.ai.auth.domain.PasswordResetToken;
//import com.deongeon.ai.auth.repository.PasswordResetTokenRepository;
//import com.deongeon.ai.global.mail.EmailService;
//import com.deongeon.ai.user.domain.AppUser;
//import com.deongeon.ai.user.repository.AppUserRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class PasswordResetService {
//
//    private final AppUserRepository userRepo;
//    private final PasswordResetTokenRepository tokenRepo;
//    private final EmailService emailService;
//    private final PasswordEncoder passwordEncoder;
//
//    private static final int EXPIRE_MINUTES = 30;
//
//    // 🔹 비밀번호 재설정 링크 발송
//    @Transactional
//    public void sendResetLink(String email, String baseUrl) {
//
//        AppUser user = userRepo.findByEmail(email)
//            .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));
//
//        // OAuth 계정이면 차단 (원하면 제거 가능)
//        if ("{OAUTH2}".equals(user.getPassword())) {
//            throw new RuntimeException("소셜 로그인 계정입니다. 소셜 로그인을 이용하세요.");
//        }
//
//        tokenRepo.deleteByEmail(email);
//
//        String token = UUID.randomUUID().toString();
//
//        PasswordResetToken t = new PasswordResetToken();
//        t.setEmail(email);
//        t.setToken(token);
//        t.setExpiresAt(LocalDateTime.now().plusMinutes(EXPIRE_MINUTES));
//        tokenRepo.save(t);
//
//        String link = baseUrl + "/forgot-password.html?mode=reset&token=" + token;
//
//        // 디버깅용
//        System.out.println("[RESET LINK] " + link);
//
//        emailService.sendPasswordResetLink(email, link);
//    }
//
//    // 🔹 비밀번호 변경
//    @Transactional
//    public void resetPassword(String token, String newPassword) {
//
//        PasswordResetToken t = tokenRepo.findByToken(token)
//            .orElseThrow(() -> new RuntimeException("유효하지 않은 토큰입니다."));
//
//        if (t.isExpired()) {
//            tokenRepo.delete(t);
//            throw new RuntimeException("토큰이 만료되었습니다.");
//        }
//
//        AppUser user = userRepo.findByEmail(t.getEmail())
//            .orElseThrow(() -> new RuntimeException("사용자 없음"));
//
//        user.setPassword(passwordEncoder.encode(newPassword));
//        userRepo.save(user);
//
//        tokenRepo.delete(t);
//    }
//
//    // 🔹 아이디(이메일) 찾기 – 존재 여부 안내
//    public String findIdHint(String email) {
//        return userRepo.findByEmail(email).isPresent()
//            ? "해당 이메일로 가입된 계정이 있습니다."
//            : "해당 이메일로 가입된 계정이 없습니다.";
//    }
//}
