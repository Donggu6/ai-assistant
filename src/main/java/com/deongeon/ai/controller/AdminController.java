package com.deongeon.ai.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.deongeon.ai.domain.AppUser;
import com.deongeon.ai.dto.ApiResponse;
import com.deongeon.ai.repository.AppUserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AppUserRepository userRepository;

	// 전체 유저 조회
	@GetMapping("/users")
	public ApiResponse<List<AppUser>> getUsers() {
		return ApiResponse.ok(userRepository.findAll());
	}

	// 요금제 변경
	@PostMapping("/role")
	public ApiResponse<String> changeRole(@RequestParam String email, @RequestParam String role) {
		AppUser user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

		user.setRole(role.toUpperCase());
		userRepository.save(user);

		return ApiResponse.ok("변경 완료: " + role);
	}
}
