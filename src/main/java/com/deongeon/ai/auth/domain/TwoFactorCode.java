package com.deongeon.ai.auth.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "two_factor_code")
@Getter
@Setter
@NoArgsConstructor
public class TwoFactorCode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false, length = 6)
	private String code;

	@Column(name = "sent_at", nullable = false)
	private LocalDateTime sentAt;

	@Column(name = "expires_at", nullable = false)
	private LocalDateTime expiresAt;

	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expiresAt);
	}
}
