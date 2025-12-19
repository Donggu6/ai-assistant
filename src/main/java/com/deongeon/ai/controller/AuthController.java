package com.deongeon.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.deongeon.ai.dto.ApiResponse;
import com.deongeon.ai.dto.LoginRequest;
import com.deongeon.ai.dto.RegisterRequest;
import com.deongeon.ai.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	@Autowired
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	public ApiResponse<String> register(@RequestBody RegisterRequest req) {
		authService.register(req);
		return ApiResponse.ok("회원가입 완료");
	}

	@PostMapping("/login")
	public ApiResponse<String> login(@RequestBody LoginRequest req) {
		String token = authService.login(req);
		return ApiResponse.ok(token);
	}
}
