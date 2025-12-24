package com.deongeon.ai.auth.dto.response;

import com.deongeon.ai.auth.domain.AppUser;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterResponse {

	private Long id;
	private String email;

	public static RegisterResponse from(AppUser user){
		return new RegisterResponse(
				user.getId(),
				user.getEmail()
		);
	}
}
