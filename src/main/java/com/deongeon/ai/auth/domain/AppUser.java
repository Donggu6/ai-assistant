package com.deongeon.ai.auth.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 기존 필드
	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	// ===== 🔥 사업용 필드 =====
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Plan plan = Plan.FREE;

	@Column(nullable = false)
	private int usageCount = 0;

	private LocalDateTime lastUsedDate;

	private LocalDateTime registeredDate = LocalDateTime.now();

	private LocalDateTime premiumStartDate;
	private LocalDateTime premiumExpireDate;
}
