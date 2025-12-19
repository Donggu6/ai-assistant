package com.deongeon.ai.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deongeon.ai.domain.AppUser;
import com.deongeon.ai.dto.LoginRequest;
import com.deongeon.ai.dto.RegisterRequest;
import com.deongeon.ai.repository.AppUserRepository;
import com.deongeon.ai.security.JwtTokenProvider;

@Service
public class AuthService {

	private final AppUserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

	public AuthService(AppUserRepository userRepository, PasswordEncoder passwordEncoder,
			JwtTokenProvider jwtTokenProvider) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public void register(RegisterRequest req) {

		if (userRepository.findByEmail(req.email()).isPresent()) {
			throw new RuntimeException("이미 존재하는 이메일입니다.");
		}

		AppUser user = new AppUser();
		user.setEmail(req.email());
		user.setPassword(passwordEncoder.encode(req.password()));
		user.setRole("FREE");
		user.setUsageCount(0);

		userRepository.save(user);
	}

	public String login(LoginRequest req) {

		AppUser user = userRepository.findByEmail(req.email())
				.orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));

		if (!passwordEncoder.matches(req.password(), user.getPassword())) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}

		return jwtTokenProvider.createToken(user.getEmail());
	}
}
