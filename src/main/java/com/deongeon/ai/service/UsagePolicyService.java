package com.deongeon.ai.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.deongeon.ai.domain.AppUser;
import com.deongeon.ai.repository.AppUserRepository;


@Service
public class UsagePolicyService {

	private final AppUserRepository userRepository;

	private static final int FREE_LIMIT = 5;

    public UsagePolicyService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	public void validateAndIncrease(String email) {

		AppUser user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

		LocalDate today = LocalDate.now();

		// 날짜가 바뀌면 카운트 리셋
		if (user.getLastUsedDate() == null || !user.getLastUsedDate().equals(today)) {
			user.setUsageCount(0);
			user.setLastUsedDate(today);
		}

		// FREE 제한 체크
		if ("FREE".equalsIgnoreCase(user.getRole())) {
			if (user.getUsageCount() >= FREE_LIMIT) {
				throw new RuntimeException("FREE 요금제 하루 사용량 초과!");
			}
		}

		// 사용량 증가
		user.setUsageCount(user.getUsageCount() + 1);

		userRepository.save(user);
	}
}
