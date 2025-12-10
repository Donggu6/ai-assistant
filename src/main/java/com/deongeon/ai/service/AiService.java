package com.deongeon.ai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deongeon.ai.model.AiRequest;
import com.deongeon.ai.model.User;
import com.deongeon.ai.repository.AiRequestRepository;
import com.deongeon.ai.repository.UserRepository;

@Service
public class AiService {

    @Autowired
    private AiRequestRepository aiRequestRepository;

    @Autowired
    private UserRepository userRepository;

    // AI 호출 (현재 더미)
    public String callAI(String prompt) {
        return "AI 응답: " + prompt;
    }

    // 사용량 검증
    public void validateUsage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        int FREE_LIMIT = 10; // 무료 사용량 제한
        if ("FREE".equals(user.getSubscriptionType()) &&
            user.getUsageCount() >= FREE_LIMIT) {
            throw new RuntimeException("무료 사용량이 모두 소진되었습니다. 구독이 필요합니다.");
        }
    }

    // AI 요청 기록 저장
    public void saveRequest(Long userId, String prompt, String response) {
        AiRequest req = new AiRequest();
        req.setUserId(userId);
        req.setRequestData(prompt);
        req.setResponseData(response);
        aiRequestRepository.save(req);
    }
}
