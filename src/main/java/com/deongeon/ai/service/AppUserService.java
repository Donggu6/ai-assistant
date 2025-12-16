package com.deongeon.ai.service;

import org.springframework.stereotype.Service;
import com.deongeon.ai.domain.AppUser;
import com.deongeon.ai.repository.AppUserRepository;

@Service
public class AppUserService {

	private final AppUserRepository repository;

	public AppUserService(AppUserRepository repository) {
		this.repository = repository;
	}

	public AppUser getUser(Long id) {
		return repository.findById(id).orElseThrow(() -> new RuntimeException("User not found: " + id));
	}
}
