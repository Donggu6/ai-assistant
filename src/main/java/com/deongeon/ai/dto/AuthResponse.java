package com.deongeon.ai.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {}
