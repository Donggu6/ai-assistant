package com.deongeon.ai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



@Service
public class AiService {
	@Value("${openai.api.key}")
	private String apiKey;
	
	
	public String callAI(String prompt) {
		// 실제 OpenAi API 호출 구현 가능
		return "AI 응답: " + prompt;
	}
}
