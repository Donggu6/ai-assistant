package com.deongeon.ai.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deongeon.ai.domain.AppUser;
import com.deongeon.ai.domain.Role;
import com.deongeon.ai.repository.AppUserRepository;



@Service
public class AppUserService {

	private final AppUserRepository repo;
	private final PasswordEncoder passwordEncoder;
	
	public AppUserService(AppUserRepository repo, PasswordEncoder passwordEncoder) {
		this.repo = repo;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional
	public AppUser createUser(String email, String rawPassword) {
		if (email == null || email.isBlank()) throw new IllegalArgumentException("email is required");
		if (rawPassword == null || rawPassword.isBlank()) throw new IllegalArgumentException("password is required");
		
		String normalizedEmail = email.trim().toLowerCase();
		
		if (repo.existsByEmail(normalizedEmail)) {
			throw new IllegalStateException("email already exists");
		}	
		
		
		AppUser user = new AppUser();
		user.setEmail(normalizedEmail);
		user.setPassword(passwordEncoder.encode(rawPassword));  // BCrypt 저장
		user.setRole(Role.USER);
		
		return repo.save(user);
	}
	
	@Transactional(readOnly = true)
	public AppUser getByEmail(String email) {
		String normalizedEmail = (email == null) ? "" : email.trim().toLowerCase();
		return repo.findByEmail(normalizedEmail)
				.orElseThrow(() -> new IllegalStateException("user not found"));
	}
		
}
