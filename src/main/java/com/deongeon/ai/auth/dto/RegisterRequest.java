package com.deongeon.ai.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
	private String email;
	private String password;
}
