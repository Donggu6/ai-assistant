package com.deongeon.ai.service;

import org.springframework.stereotype.Service;

import com.deongeon.ai.dto.LoginRequest;

@Service
public class AuthService {

	public String login(LoginRequest request) {
		// 여기에 실제 인증 로직 넣으면 됨
		return "dummy-token";
	}
}
