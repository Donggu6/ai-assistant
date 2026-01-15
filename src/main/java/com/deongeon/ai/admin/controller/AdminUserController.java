package com.deongeon.ai.admin.controller;

import com.deongeon.ai.user.domain.AppUser;
import com.deongeon.ai.user.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {

    private final AppUserRepository userRepository;

    // 전체 유저 조회
    @GetMapping
    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    // 계정 잠금 해제
    @PostMapping("/{id}/unlock")
    public void unlock(@PathVariable Long id) {
        AppUser user = userRepository.findById(id).orElseThrow();
        user.setAccountLocked(false);
        user.setFailedLoginCount(0);
        userRepository.save(user);
    }

    // 권한 변경
    @PostMapping("/{id}/role")
    public void changeRole(@PathVariable Long id, @RequestParam String role) {
        AppUser user = userRepository.findById(id).orElseThrow();
        user.setRole(role);
        userRepository.save(user);
    }
}
