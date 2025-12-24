package com.deongeon.ai.user.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deongeon.ai.auth.domain.AppUser;
import com.deongeon.ai.auth.domain.Plan;
import com.deongeon.ai.user.dto.UsageStatusResponse;
import com.deongeon.ai.user.dto.UserResponse;
import com.deongeon.ai.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    /**
     * 🔥 현재 로그인한 사용자 반환
     * - JwtAuthenticationFilter 가 SecurityContext 에 email 넣어줌
     * - 여기서 email 읽고 DB 에서 AppUser 조회
     */
    public AppUser getCurrentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 없을 경우 (비로그인 / 잘못된 토큰)
        if (auth == null || auth.getName() == null) {
            throw new IllegalStateException("No authenticated user found");
        }

        String email = auth.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found: " + email));
    }

    public UserResponse getMe() {
        AppUser user = getCurrentUser();
        return UserResponse.from(user);
    }

    public UsageStatusResponse getUsageStatus() {
        AppUser user = getCurrentUser();

        Integer dailyLimit = (user.getPlan() == Plan.FREE) ? 50 : null;

        boolean exceeded = user.getPlan() == Plan.FREE &&
                           user.getUsageCount() >= 50;

        return UsageStatusResponse.builder()
                .plan(user.getPlan())
                .usageCount(user.getUsageCount())
                .dailyLimit(dailyLimit)
                .exceeded(exceeded)
                .build();
    }

    @Transactional
    public void increaseUsage() {
        AppUser user = getCurrentUser();
        user.setUsageCount(user.getUsageCount() + 1);
    }
}
