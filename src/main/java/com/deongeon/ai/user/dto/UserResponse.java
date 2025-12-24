package com.deongeon.ai.user.dto;

import java.time.LocalDateTime;

import com.deongeon.ai.auth.domain.AppUser;
import com.deongeon.ai.auth.domain.Plan;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

	private Long id;
	private String email;
	private Plan plan;
	private int usageCount;
	private LocalDateTime lastUsedDate;
	private LocalDateTime registeredDate;
	private LocalDateTime premiumExpireDate;

	public static UserResponse from(AppUser user) {
		return UserResponse.builder().id(user.getId()).email(user.getEmail()).plan(user.getPlan())
				.usageCount(user.getUsageCount()).lastUsedDate(user.getLastUsedDate())
				.registeredDate(user.getRegisteredDate()).premiumExpireDate(user.getPremiumExpireDate()).build();
	}
}
