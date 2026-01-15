//package com.deongeon.ai.auth.controller;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import com.deongeon.ai.auth.service.PasswordResetService;
//
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.*;
//
//@RestController
//@RequestMapping("/api/auth")
//@RequiredArgsConstructor
//public class PasswordResetController {
//
//    private final PasswordResetService service;
//
//    // 🔹 아이디 찾기
//    @PostMapping("/find-id")
//    public ResponseEntity<?> findId(@RequestBody FindIdReq req) {
//        String msg = service.findIdHint(req.getEmail());
//        return ResponseEntity.ok(msg);
//    }
//
//    // 🔹 비밀번호 재설정 링크 발송
//    @PostMapping("/forgot-password")
//    public ResponseEntity<?> forgotPassword(@RequestBody ForgotReq req, HttpServletRequest request) {
//        String baseUrl = getBaseUrl(request);
//        service.sendResetLink(req.getEmail(), baseUrl);
//        return ResponseEntity.ok("비밀번호 재설정 링크를 이메일로 보냈습니다.");
//    }
//
//    // 🔹 비밀번호 변경
//    @PostMapping("/reset-password")
//    public ResponseEntity<?> resetPassword(@RequestBody ResetReq req) {
//        service.resetPassword(req.getToken(), req.getNewPassword());
//        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
//    }
//
//    private String getBaseUrl(HttpServletRequest req) {
//        String scheme = req.getScheme();
//        String host = req.getServerName();
//        int port = req.getServerPort();
//        boolean defaultPort = (scheme.equals("http") && port == 80) || (scheme.equals("https") && port == 443);
//        return scheme + "://" + host + (defaultPort ? "" : ":" + port);
//    }
//
//    @Getter @Setter
//    static class FindIdReq {
//        private String email;
//    }
//
//    @Getter @Setter
//    static class ForgotReq {
//        private String email;
//    }
//
//    @Getter @Setter
//    static class ResetReq {
//        private String token;
//        private String newPassword;
//    }
//}
