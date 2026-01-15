package com.deongeon.ai.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime; // ⭐ 이게 없어서 에러 난 거야

@Entity
@Table(name = "app_user")
@Getter
@Setter
@NoArgsConstructor
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private boolean twoFactorEnabled = false;

	@Column(nullable = false)
	private int failedLoginCount = 0;

	@Column(nullable = false)
	private boolean accountLocked = false;

	private LocalDateTime lastFailedLoginAt;
	private LocalDateTime lockedAt;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Plan plan;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SubscriptionStatus subscriptionStatus;

	@Column(nullable = false)
	private String role = "USER";
}
