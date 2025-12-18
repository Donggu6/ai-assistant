package com.deongeon.ai.controller;

import com.deongeon.ai.domain.AppUser;
import com.deongeon.ai.security.JwtTokenProvider;
import com.deongeon.ai.service.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AppUserService userService;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtProvider;

	public AuthController(AppUserService userService, PasswordEncoder passwordEncoder, JwtTokenProvider jwtProvider) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.jwtProvider = jwtProvider;
	}

	// ✅ 회원가입
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody Map<String, String> req) {
		AppUser user = userService.createUser(req.get("email"), req.get("password"));
		return ResponseEntity.ok("REGISTERED");
	}

	// ✅ 로그인
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> req) {
		AppUser user = userService.getByEmail(req.get("email"));

		if (!passwordEncoder.matches(req.get("password"), user.getPassword())) {
			return ResponseEntity.status(401).body("INVALID_PASSWORD");
		}

		String token = jwtProvider.createToken(user.getId(), user.getRole());

		return ResponseEntity.ok(Map.of("token", token));
	}
}
