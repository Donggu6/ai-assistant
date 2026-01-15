package com.deongeon.ai.auth.service;

import org.springframework.stereotype.Service;

import com.deongeon.ai.user.repository.AppUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginFailureService {

    private final AppUserRepository userRepository;

    public void onFailure(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            user.setFailedLoginCount(user.getFailedLoginCount() + 1);
            userRepository.save(user);
        });
    }

    public void onSuccess(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            user.setFailedLoginCount(0);
            userRepository.save(user);
        });
    }
}
