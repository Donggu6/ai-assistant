package com.deongeon.ai;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.deongeon.ai.model.User;
import com.deongeon.ai.repository.UserRepository;

@Component
public class TestRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // 테스트용 유저 생성
        User user = new User();
        user.setEmail("test1@example.com");
        user.setPassword("pass123");
        user.setSubscriptionType("FREE");
        user.setUsageCount(0);

        // DB에 저장
        userRepository.save(user);

        // DB에서 전체 조회
        System.out.println("=== Users in DB ===");
        for (User u : userRepository.findAll()) {
            System.out.println("ID: " + u.getId() + ", Email: " + u.getEmail() +
                               ", Subscription: " + u.getSubscriptionType() +
                               ", Usage: " + u.getUsageCount());
        }
    }
}
