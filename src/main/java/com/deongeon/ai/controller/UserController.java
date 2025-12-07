package com.deongeon.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.deongeon.ai.model.User;
import com.deongeon.ai.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 회원가입
    @PostMapping("/register")
    public User register(
            @RequestParam String email,
            @RequestParam String password
    ) {
        return userService.register(email, password);
    }

    // 로그인
    @PostMapping("/login")
    public User login(
            @RequestParam String email,
            @RequestParam String password
    ) {
        return userService.login(email, password);
    }

    // 구독 업그레이드
    @PostMapping("/upgrade")
    public User upgradeToPremium(@RequestParam Long userId) {
        userService.upgradeToPremium(userId);
        return userService.getUser(userId);
    }

    // 사용자 조회
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}
