package com.deongeon.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deongeon.ai.dto.ApiResponse;
import com.deongeon.ai.service.AiService;
import com.deongeon.ai.service.UsagePolicyService;

@RestController
@RequestMapping("/api/ai")
public class AiController {

	private final AiService aiService;
	private final UsagePolicyService usagePolicyService;

	@Autowired
	public AiController(AiService aiService, UsagePolicyService usagePolicyService) {
		this.aiService = aiService;
		this.usagePolicyService = usagePolicyService;
	}

	/**
	 * 실전용 흐름: 1) 이메일 기반으로 FREE/PREMIUM 사용량 검증 2) 사용량 증가 3) AI 호출 4) 결과 반환
	 */
	@PostMapping("/chat")
	public ApiResponse<String> chat(@RequestParam String email, @RequestParam String prompt) {
		// 1,2. 사용량 정책 체크 + 증가
		usagePolicyService.validateAndIncrease(email);

		// 3. AI 호출 (실제 구현은 AiService 안에서)
		String answer = aiService.callAI(prompt);

		// 4. 결과 반환
		return ApiResponse.ok(answer);
	}
}
