package com.deongeon.ai.controller;

import com.deongeon.ai.dto.RegisterRequest;
import com.deongeon.ai.dto.RegisterResponse;
import com.deongeon.ai.service.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final AppUserService service;

	public UserController(AppUserService service) {
		this.service = service;
	}

	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest req) {
		return ResponseEntity.ok(service.register(req));
	}

	@GetMapping("/public")
	public String publicApi() {
		return "OK - public access";
	}
}