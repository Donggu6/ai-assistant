package com.deongeon.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deongeon.ai.service.AiService;

@RestController
@RequestMapping("/api/ai")
public class AiController {
	
	@Autowired
	private AiService aiService;
	
	@PostMapping("/request")
	public ResponseEntity<String> requestAI(@RequestParam String prompt){
		String result = aiService.callAI(prompt);
		return ResponseEntity.ok(result);
//							.ok
	}
}
