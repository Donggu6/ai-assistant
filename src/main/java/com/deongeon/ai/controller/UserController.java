package com.deongeon.ai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deongeon.ai.dto.ApiResponse;

@RestController
public class UserController {

	@GetMapping("/api/user/me")
	public ApiResponse<?> me() {
		return ApiResponse.ok();
	}
}
