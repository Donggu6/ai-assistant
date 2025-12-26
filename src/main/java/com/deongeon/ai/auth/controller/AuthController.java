package com.deongeon.ai.auth.controller;

import com.deongeon.ai.auth.dto.AuthResponse;
import com.deongeon.ai.auth.dto.LoginRequest;
import com.deongeon.ai.auth.service.AuthService;
import com.deongeon.ai.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){

        var user = authService.login(request);

        String token = jwtTokenProvider.createToken(user.getEmail());

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
