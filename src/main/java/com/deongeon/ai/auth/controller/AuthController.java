package com.deongeon.ai.auth.controller;

import org.springframework.web.bind.annotation.*;

import com.deongeon.ai.auth.dto.LoginRequest;
import com.deongeon.ai.auth.dto.RegisterRequest;
import com.deongeon.ai.auth.dto.response.LoginResponse;
import com.deongeon.ai.auth.dto.response.RegisterResponse;
import com.deongeon.ai.auth.domain.AppUser;
import com.deongeon.ai.auth.service.AuthService;
import com.deongeon.ai.global.common.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public ApiResponse<?> register(@RequestBody RegisterRequest req) {
		AppUser user = authService.register(req);
		return ApiResponse.ok(RegisterResponse.from(user));
	}

	@PostMapping("/login")
	public ApiResponse<?> login(@RequestBody LoginRequest req) {
		LoginResponse response = authService.login(req);
		return ApiResponse.ok(response);
	}
}
