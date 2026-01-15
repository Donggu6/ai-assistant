package com.deongeon.ai.admin.controller.ai.coach.dto;

import java.util.List;
import lombok.Data;

@Data
public class CoachChatResponse {
    private String reply;
    private List<CoachAction> actions;
    private CoachStateView state;
}
