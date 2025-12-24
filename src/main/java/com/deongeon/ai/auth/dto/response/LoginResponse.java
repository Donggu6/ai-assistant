package com.deongeon.ai.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

	private String accessToken;   // JWT
	private String refreshToken;  // 필요하면 사용
	private Long userId;
	private String email;
}
