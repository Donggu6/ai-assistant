package com.deongeon.ai.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deongeon.ai.dto.AiRequestDto;
import com.deongeon.ai.model.AiRequest;
import com.deongeon.ai.model.User;
import com.deongeon.ai.repository.AiRequestRepository;
import com.deongeon.ai.repository.UserRepository;

@Service
public class AiService {

	private final UserRepository userRepository;
	private final AiRequestRepository aiRequestRepository;

	@Autowired
	public AiService(UserRepository userRepository, AiRequestRepository aiRequestRepository) {
		this.userRepository = userRepository;
		this.aiRequestRepository = aiRequestRepository;
	}

	/**
	 * AI 호출 처리 (나중에 OpenAI API 연동할 부분)
	 */
	public String callAI(String prompt) {
		// TODO: 실제 OpenAI API 연동 부분
		// 현재는 임시 답변
		return "AI Response: " + prompt;
	}

	/**
	 * FREE 요금제는 하루 10회 제한
	 */
	public void validateLimit(User user) {
		if ("FREE".equals(user.getSubscriptionType())) {
			if (user.getUsageCount() >= 10) {
				throw new RuntimeException("FREE 요금제는 하루 10회까지 사용 가능합니다.");
			}
		}
	}

	/**
	 * 사용량 증가
	 */
	public void increaseUsage(User user) {
		user.setUsageCount(user.getUsageCount() + 1);
		userRepository.save(user);
	}

	/**
	 * 요청 기록 저장
	 */
	public void saveLog(Long userId, String request, String response) {
		AiRequest log = new AiRequest();
		log.setUserId(userId);
		log.setRequestData(request);
		log.setResponseData(response);
		log.setTimestamp(LocalDateTime.now());

		aiRequestRepository.save(log);
	}
}
