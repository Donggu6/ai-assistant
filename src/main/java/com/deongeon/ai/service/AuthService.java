package com.deongeon.ai.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deongeon.ai.config.JwtTokenProvider;
import com.deongeon.ai.dto.AuthRequest;
import com.deongeon.ai.dto.AuthResponse;
import com.deongeon.ai.model.User;
import com.deongeon.ai.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public AuthResponse register(AuthRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 가입된 이메일입니다.");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setSubscriptionType(request.getSubscriptionType());
        user.setUsageCount(0);
        user.setRole("ROLE_USER");

        userRepository.save(user);

        String token = jwtTokenProvider.createToken(user);

        return new AuthResponse(token, user.getEmail(), user.getRole(), user.getSubscriptionType());
    }

    public AuthResponse login(AuthRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("이메일 또는 비밀번호가 잘못되었습니다.");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("이메일 또는 비밀번호가 잘못되었습니다.");
        }

        String token = jwtTokenProvider.createToken(user);

        return new AuthResponse(token, user.getEmail(), user.getRole(), user.getSubscriptionType());
    }

    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }
}
