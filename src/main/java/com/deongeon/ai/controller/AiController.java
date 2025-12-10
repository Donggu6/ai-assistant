package com.deongeon.ai.controller;

import com.deongeon.ai.dto.AiRequestDto;
import com.deongeon.ai.model.User;
import com.deongeon.ai.repository.UserRepository;
import com.deongeon.ai.service.AiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

	private final AiService aiService;
	private final UserRepository userRepository;

	@Autowired
	public AiController(AiService aiService, UserRepository userRepository) {
		this.aiService = aiService;
		this.userRepository = userRepository;
	}

	@PostMapping("/send")
	public ResponseEntity<?> sendPrompt(@RequestBody AiRequestDto dto, Authentication auth) {
		// JWT 인증된 사용자 이메일 가져오기
		String email = auth.getName();

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

		// 제한 체크 (FREE 요금제)
		aiService.validateLimit(user);

		// 실제 AI 호출
		String response = aiService.callAI(dto.getPrompt());

		// 사용량 증가
		aiService.increaseUsage(user);

		// 로그 저장
		aiService.saveLog(user.getId(), dto.getPrompt(), response);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/test")
	public String test() {
		return "AI API OK";
	}
}
