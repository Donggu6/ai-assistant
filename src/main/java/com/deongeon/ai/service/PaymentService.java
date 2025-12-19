package com.deongeon.ai.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deongeon.ai.domain.AppUser;
import com.deongeon.ai.repository.AppUserRepository;

@Service
public class PaymentService {

	private final AppUserRepository userRepository;

	@Autowired
	public PaymentService(AppUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void upgradeToPremium(String email) {

		AppUser user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

		user.setRole("PREMIUM");
		user.setUsageCount(0);
		user.setLastUsedDate(LocalDate.now());

		userRepository.save(user);
	}
}
