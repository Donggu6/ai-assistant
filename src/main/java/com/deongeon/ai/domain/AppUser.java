package com.deongeon.ai.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;

	private String password;

	private String role; // FREE / PREMIUM

	private int usageCount; // 하루 사용량

	private LocalDate lastUsedDate; // 마지막 사용 날짜

	// ---- Getter / Setter ----
	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getUsageCount() {
		return usageCount;
	}

	public void setUsageCount(int usageCount) {
		this.usageCount = usageCount;
	}

	public LocalDate getLastUsedDate() {
		return lastUsedDate;
	}

	public void setLastUsedDate(LocalDate lastUsedDate) {
		this.lastUsedDate = lastUsedDate;
	}

	public AppUser() {
	}

	public AppUser(String email, String password) {
		this.email = email;
		this.password = password;
		this.role = "FREE";
		this.usageCount = 0;
	}

}
