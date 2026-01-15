package com.deongeon.ai.user.controller;

import com.deongeon.ai.user.domain.AppUser;
import com.deongeon.ai.user.repository.AppUserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/2fa")
@RequiredArgsConstructor
public class User2FAController {

    private final AppUserRepository userRepository;

    @PostMapping("/enable")
    public String enable2FA(Authentication auth) {
        AppUser user = userRepository.findByEmail(auth.getName())
                .orElseThrow();

        user.setTwoFactorEnabled(true);
        userRepository.save(user);

        return "2FA ENABLED";
    }

    @PostMapping("/disable")
    public String disable2FA(Authentication auth) {
        AppUser user = userRepository.findByEmail(auth.getName())
                .orElseThrow();

        user.setTwoFactorEnabled(false);
        userRepository.save(user);

        return "2FA DISABLED";
    }
}
