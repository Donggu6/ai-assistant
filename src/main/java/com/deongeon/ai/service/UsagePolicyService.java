package com.deongeon.ai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deongeon.ai.domain.AppUser;
import com.deongeon.ai.repository.AppUserRepository;

@Service
public class UsagePolicyService {

	public static final int FREE_MAX = 10;

	private final AppUserRepository appUserRepository;

	@Autowired
	public UsagePolicyService(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}

	// 이메일로 유저 찾아서 검증 + 사용량 증가까지 한 번에
	public void validateAndIncrease(String email) {
		AppUser user = appUserRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + email));

		if ("FREE".equalsIgnoreCase(user.getRole()) && user.getUsageCount() >= FREE_MAX) {
			throw new RuntimeException("무료 사용 한도를 초과했습니다.");
		}

		user.setUsageCount(user.getUsageCount() + 1);
		appUserRepository.save(user);
	}

	// 필요하면 AppUser 객체로 쓰는 버전도 사용 가능
	public void validate(AppUser user) {
		if ("FREE".equalsIgnoreCase(user.getRole()) && user.getUsageCount() >= FREE_MAX) {
			throw new RuntimeException("무료 사용 한도를 초과했습니다.");
		}
	}

	public void increase(AppUser user) {
		user.setUsageCount(user.getUsageCount() + 1);
		appUserRepository.save(user);
	}
}
