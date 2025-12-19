package com.deongeon.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.deongeon.ai.dto.ApiResponse;
import com.deongeon.ai.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

	private final PaymentService paymentService;

	@Autowired
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@PostMapping("/upgrade")
	public ApiResponse<String> upgrade(@RequestParam String email) {
		paymentService.upgradeToPremium(email);
		return ApiResponse.ok("PREMIUM 업그레이드 완료!");
	}
}
