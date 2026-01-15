package com.deongeon.ai.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String plan;
    private boolean require2FA;
    private String twoFactorToken;
}
