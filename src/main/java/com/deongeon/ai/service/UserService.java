package com.deongeon.ai.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deongeon.ai.model.User;
import com.deongeon.ai.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // 회원가입
    public User register(String email, String password) {
        Optional<User> existing = userRepository.findByEmail(email);
        if (existing.isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setSubscriptionType("FREE");
        user.setUsageCount(0);

        return userRepository.save(user);
    }

    // 로그인
    public User login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("존재하지 않는 이메일입니다.");
        }

        User user = userOpt.get();

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }

        return user;
    }

    // 사용량 증가
    public void increaseUsage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        user.setUsageCount(user.getUsageCount() + 1);
        userRepository.save(user);
    }

    // 구독 변경
    public void upgradeToPremium(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        user.setSubscriptionType("PREMIUM");
        userRepository.save(user);
    }

    // 사용자 조회
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }
}
