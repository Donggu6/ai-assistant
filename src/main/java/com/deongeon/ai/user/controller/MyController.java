package com.deongeon.ai.user.controller;

import com.deongeon.ai.user.domain.AppUser;
import com.deongeon.ai.user.repository.AppUserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/me")
@RequiredArgsConstructor
public class MyController {

    private final AppUserRepository userRepo;

    @GetMapping
    public Map<String, Object> me(Authentication auth) {
        AppUser user = userRepo.findByEmail(auth.getName()).orElseThrow();
        return Map.of(
                "email", user.getEmail(),
                "plan", user.getPlan().name()
        );
    }
}
