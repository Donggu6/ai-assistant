package com.deongeon.ai.admin.controller.ai.coach.controller;

import org.springframework.web.bind.annotation.*;

import com.deongeon.ai.admin.controller.ai.coach.dto.*;
import com.deongeon.ai.admin.controller.ai.coach.service.CoachService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/coach")
@RequiredArgsConstructor
public class CoachController {

    private final CoachService coachService;

    @PostMapping("/chat")
    public CoachChatResponse chat(
            @RequestHeader("X-USER-ID") String userId,
            @RequestBody CoachChatRequest req) {

        return coachService.chat(userId, req);
    }

    @PostMapping("/reset")
    public void reset(@RequestHeader("X-USER-ID") String userId) {
        coachService.reset(userId);
    }
}
