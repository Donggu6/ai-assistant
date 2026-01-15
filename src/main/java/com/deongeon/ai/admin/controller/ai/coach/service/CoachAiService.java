package com.deongeon.ai.admin.controller.ai.coach.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class CoachAiService {

	private final WebClient webClient;

	public CoachAiService() {
		this.webClient = WebClient.builder().baseUrl("https://api.openai.com/v1/chat/completions")
				.defaultHeader("Authorization", "Bearer " + System.getenv("OPENAI_KEY"))
				.defaultHeader("Content-Type", "application/json").build();
	}

	public String ask(String userMessage) {

		String systemPrompt = """
				너는 해외구매대행, 위탁판매, 온라인 셀러 전문 AI 코치다.
				감이 아니라 수익 구조, 리스크, 실행 전략 중심으로 답한다.
				불필요한 설명 없이 바로 실행 전략을 제시해라.
				""";

		Map<String, Object> body = Map.of("model", "gpt-4o-mini", "messages", List
				.of(Map.of("role", "system", "content", systemPrompt), Map.of("role", "user", "content", userMessage)),
				"temperature", 0.6);

		Map response = webClient.post().bodyValue(body).retrieve().bodyToMono(Map.class).block();

		try {
			List choices = (List) response.get("choices");
			Map first = (Map) choices.get(0);
			Map message = (Map) first.get("message");
			return (String) message.get("content");
		} catch (Exception e) {
			return "AI 응답 처리 중 오류 발생";
		}
	}
}
