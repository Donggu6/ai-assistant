package com.deongeon.ai.user.dto;

import com.deongeon.ai.auth.domain.Plan;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UsageStatusResponse {

    private Plan plan;
    private int usageCount;
    private Integer dailyLimit;
    private boolean exceeded;
}
