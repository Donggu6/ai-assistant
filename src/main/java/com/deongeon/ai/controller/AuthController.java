package com.deongeon.ai.controller;

import com.deongeon.ai.dto.ApiResponse;
import com.deongeon.ai.dto.LoginRequest;
import com.deongeon.ai.dto.LoginResponse;
import com.deongeon.ai.dto.LogoutRequest;
import com.deongeon.ai.dto.RefreshRequest;
import com.deongeon.ai.dto.RegisterRequest;
import com.deongeon.ai.dto.RegisterResponse;
import com.deongeon.ai.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody RegisterRequest req) {
		return ResponseEntity.ok(ApiResponse.ok(authService.register(req)));
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest req) {
		return ResponseEntity.ok(ApiResponse.ok(authService.login(req)));
	}

	@PostMapping("/refresh")
	public ResponseEntity<ApiResponse<LoginResponse>> refresh(@RequestBody RefreshRequest req) {
		return ResponseEntity.ok(ApiResponse.ok(authService.refresh(req.refreshToken())));
	}

	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<String>> logout(@RequestBody LogoutRequest req) {
		authService.logout(req.refreshToken());
		return ResponseEntity.ok(ApiResponse.ok("LOGOUT_OK"));
	}

}
