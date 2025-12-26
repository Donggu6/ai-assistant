package com.deongeon.ai.auth.service;

import com.deongeon.ai.auth.domain.AppUser;
import com.deongeon.ai.auth.dto.LoginRequest;
import com.deongeon.ai.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUser login(LoginRequest req){

        var user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if(!passwordEncoder.matches(req.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Password");
        }

        return user;
    }
}
