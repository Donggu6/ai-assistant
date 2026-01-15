package com.deongeon.ai.auth.dto;

import lombok.Getter;

@Getter
public class RegisterRequest {
    private String email;
    private String password;
    private boolean twoFactorEnabled;
}
