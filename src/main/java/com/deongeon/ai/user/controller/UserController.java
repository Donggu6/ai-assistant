package com.deongeon.ai.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.deongeon.ai.user.dto.UsageStatusResponse;
import com.deongeon.ai.user.dto.UserResponse;
import com.deongeon.ai.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/me")
	public ResponseEntity<UserResponse> me() {
		return ResponseEntity.ok(userService.getMe());
	}

	@GetMapping("/usage")
	public ResponseEntity<UsageStatusResponse> usage() {
		return ResponseEntity.ok(userService.getUsageStatus());
	}
}
