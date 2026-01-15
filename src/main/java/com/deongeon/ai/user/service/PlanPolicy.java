package com.deongeon.ai.user.service;

import com.deongeon.ai.user.domain.Plan;

public class PlanPolicy {

    public static boolean canUseAdvancedAnalysis(Plan plan) {
        return plan == Plan.PREMIUM || plan == Plan.BUSINESS;
    }

    public static boolean canUseAutomation(Plan plan) {
        return plan == Plan.PREMIUM || plan == Plan.BUSINESS;
    }

    public static boolean canUseTeamFeature(Plan plan) {
        return plan == Plan.BUSINESS;
    }

    // 🔥 Stripe 결제 후 지급할 초기 크레딧
    public static int initialCredits(Plan plan) {
        return switch (plan) {
            case FREE -> 0;
            case BASIC -> 100;
            case PREMIUM -> 500;
            case BUSINESS -> 2000;
            default -> 0;
        };
    }
}
