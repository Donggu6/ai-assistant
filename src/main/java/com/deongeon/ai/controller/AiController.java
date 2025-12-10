package com.deongeon.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.deongeon.ai.service.AiService;
import com.deongeon.ai.service.UserService;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    @Autowired
    private AiService aiService;

    @Autowired
    private UserService userService;

    @PostMapping("/send")
    public String sendRequest(@RequestParam Long userId,
                              @RequestParam String prompt) {
        try {
            // 1. 사용자 존재 여부 검증
            userService.getUser(userId);

            // 2. FREE 유저면 사용량 제한 체크
            aiService.validateUsage(userId);

            // 3. AI 호출
            String result = aiService.callAI(prompt);

            // 4. 사용량 증가 및 기록 저장
            userService.increaseUsage(userId);
            aiService.saveRequest(userId, prompt, result);

            // 5. 결과 반환
            return result;

        } catch (RuntimeException e) {
            return "Error: " + e.getMessage();
        }
    }
}
