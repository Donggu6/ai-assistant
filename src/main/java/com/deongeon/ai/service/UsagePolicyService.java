package com.deongeon.ai.service;

import com.deongeon.ai.domain.AppUser;
import com.deongeon.ai.repository.AppUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsagePolicyService {

	private final AppUserRepository userRepo;

	// 정책 (나중에 yml로 뺄 수 있음)
	private static final int FREE_MAX = 20; // FREE 하루 20회
	private static final int PREMIUM_MAX = 1000; // PREMIUM 사실상 무제한

	public UsagePolicyService(AppUserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Transactional
	public void validateAndIncrease(String email) {

		AppUser user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

		String plan = user.getPlanType();

		if ("FREE".equalsIgnoreCase(plan)) {
			if (user.getUsageCount() >= FREE_MAX) {
				throw new RuntimeException("FREE_LIMIT_EXCEEDED");
			}
		}

		// PREMIUM은 거의 제한 없음 (원하면 조건 추가)
		user.setUsageCount(user.getUsageCount() + 1);
		userRepo.save(user);
	}
}
