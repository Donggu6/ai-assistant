package com.deongeon.ai.auth.controller;

import com.deongeon.ai.auth.dto.*;
import com.deongeon.ai.auth.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return "OK";
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/2fa/verify")
    public LoginResponse verify2fa(@RequestBody Verify2faRequest request) {
        return authService.verify2fa(request);
    }
}
