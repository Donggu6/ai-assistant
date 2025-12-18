package com.deongeon.ai.service;

import com.deongeon.ai.domain.AppUser;
import com.deongeon.ai.domain.Role;
import com.deongeon.ai.dto.RegisterRequest;
import com.deongeon.ai.dto.RegisterResponse;
import com.deongeon.ai.repository.AppUserRepository;
import com.deongeon.ai.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

	private final AppUserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

	public AppUserService(AppUserRepository repository, PasswordEncoder passwordEncoder,
			JwtTokenProvider jwtTokenProvider) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public RegisterResponse register(RegisterRequest req) {
		AppUser user = new AppUser();
		user.setEmail(req.email());
		user.setPassword(passwordEncoder.encode(req.password()));
		user.setRole(Role.USER);

		AppUser saved = repository.save(user);

		return new RegisterResponse(saved.getId(), saved.getEmail(), saved.getRole().name());
	}
}