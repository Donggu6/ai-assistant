package com.deongeon.ai.controller;

import com.deongeon.ai.dto.ApiResponse;
import com.deongeon.ai.service.UsagePolicyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

	private final UsagePolicyService usagePolicyService;

	public AiController(UsagePolicyService usagePolicyService) {
		this.usagePolicyService = usagePolicyService;
	}

	@PostMapping("/send")
	public ResponseEntity<ApiResponse<String>> send(@RequestBody String prompt, Authentication authentication) {

		String email = (String) authentication.getPrincipal();

		// ✅ 요금제 + 사용량 체크
		usagePolicyService.validateAndIncrease(email);

		// TODO: 실제 AI 호출 로직
		return ResponseEntity.ok(ApiResponse.ok("AI_RESPONSE"));
	}
}
