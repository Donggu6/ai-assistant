package com.deongeon.ai.service;

import org.springframework.stereotype.Service;

@Service
public class AiService {

	// 🔥 지금은 임시 기능 (실전에서 여기서 진짜 AI 호출하면 됨)
	public String callAI(String prompt) {
		return "AI Response for: " + prompt;
	}
}
