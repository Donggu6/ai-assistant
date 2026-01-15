package com.deongeon.ai.auth.dto;

import lombok.Getter;

@Getter
public class Verify2faRequest {
    private String twoFactorToken;
    private String code;
}
