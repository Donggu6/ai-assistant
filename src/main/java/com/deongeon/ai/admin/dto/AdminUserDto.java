package com.deongeon.ai.admin.dto;

import java.time.LocalDateTime;

import com.deongeon.ai.global.util.EmailMasker;
import com.deongeon.ai.user.domain.AppUser;

import lombok.Getter;

@Getter
public class AdminUserDto {
    private final Long id;
    private final String email;
    private final String maskedEmail;
    private final String phone;
    private final String role;
    private final String plan;
    private final String subscriptionStatus;
    private final boolean twoFactorEnabled;
    private final int failedLoginCount;
    private final boolean accountLocked;
    private final LocalDateTime lastFailedLoginAt;
    private final LocalDateTime lockedAt;

    public AdminUserDto(AppUser u) {
        this.id = u.getId();
        this.email = u.getEmail();
        this.maskedEmail = EmailMasker.mask(u.getEmail());
        this.phone = u.getPhone();
        this.role = u.getRole();
        this.plan = u.getPlan() == null ? null : u.getPlan().name();
        this.subscriptionStatus = u.getSubscriptionStatus() == null ? null : u.getSubscriptionStatus().name();
        this.twoFactorEnabled = u.isTwoFactorEnabled();
        this.failedLoginCount = u.getFailedLoginCount();
        this.accountLocked = u.isAccountLocked();
        this.lastFailedLoginAt = u.getLastFailedLoginAt();
        this.lockedAt = u.getLockedAt();
    }
}
