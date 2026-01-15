package com.deongeon.ai.user.controller;

import com.deongeon.ai.user.domain.AppUser;
import com.deongeon.ai.user.repository.AppUserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final AppUserRepository repository;

    @GetMapping("/me")
    public AppUser me(Authentication auth) {
        String email = auth.getName(); // JWT에서 꺼낸 이메일
        return repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
    }
    
    
}
