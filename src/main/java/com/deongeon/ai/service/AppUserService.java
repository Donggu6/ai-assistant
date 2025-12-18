package com.deongeon.ai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deongeon.ai.domain.AppUser;
import com.deongeon.ai.repository.AppUserRepository;

@Service
public class AppUserService {

	private final AppUserRepository appUserRepository;

	@Autowired
	public AppUserService(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}

	// 회원 가입 (실전에서는 비밀번호 암호화 필요)
	public AppUser register(String email, String password) {
		AppUser user = new AppUser(email, password);
		user.setRole("FREE");
		user.setUsageCount(0);
		return appUserRepository.save(user);
	}

	public AppUser findByEmail(String email) {
		return appUserRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + email));
	}
}
