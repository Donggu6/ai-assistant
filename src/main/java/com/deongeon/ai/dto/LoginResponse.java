package com.deongeon.ai.dto;

public record LoginResponse(
		String tokenType,
		String accessToken,
		long expiresAt,
		String refreshToken
) {}
