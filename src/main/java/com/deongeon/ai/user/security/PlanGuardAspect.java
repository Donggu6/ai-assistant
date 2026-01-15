package com.deongeon.ai.user.security;

import com.deongeon.ai.user.domain.Plan;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PlanGuardAspect {

    @Before("@annotation(requirePlan)")
    public void checkPlan(JoinPoint jp, RequirePlan requirePlan) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getAuthorities() == null) {
            throw new RuntimeException("인증 필요");
        }

        Plan required = requirePlan.value();

        // 권한은 JwtAuthenticationFilter에서 PLAN_xxx로 넣음
        boolean ok = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("PLAN_" + required.name()));

        // NONE 처리 포함 (switch 완전 커버)
        switch (required) {
            case NONE -> throw new RuntimeException("플랜 없음");
            case FREE, BASIC, PREMIUM, BUSINESS -> {
                if (!ok) throw new RuntimeException("플랜 권한 부족: " + required);
            }
        }
    }
}
