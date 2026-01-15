package com.deongeon.ai.user.controller;

import com.deongeon.ai.global.util.EmailMasker;
import com.deongeon.ai.user.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/find-id")
public class FindIdController {

    private final AppUserRepository userRepository;

    @GetMapping
    public String find(@RequestParam String phone) {
        return userRepository.findByPhone(phone)
                .map(u -> EmailMasker.mask(u.getEmail()))
                .orElse("일치하는 계정이 없습니다.");
    }
}
