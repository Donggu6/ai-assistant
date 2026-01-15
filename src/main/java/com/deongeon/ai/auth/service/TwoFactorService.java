package com.deongeon.ai.auth.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.deongeon.ai.auth.domain.TwoFactorCode;
import com.deongeon.ai.auth.repository.TwoFactorRepository;
import com.deongeon.ai.global.mail.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TwoFactorService {

    private final TwoFactorRepository repo;
    private final EmailService emailService;

    // 2FA 코드 생성 + 이메일 발송
    public void createChallenge(String email) {
        String code = String.format("%06d", new Random().nextInt(1_000_000));

        TwoFactorCode entity = new TwoFactorCode();
        entity.setEmail(email);
        entity.setCode(code);
        entity.setSentAt(LocalDateTime.now().plusMinutes(5));
        entity.setExpiresAt(LocalDateTime.now());

        repo.save(entity);

        System.out.println("[2FA CODE] " + email + " = " + code);
        emailService.send2FACode(email, code);
    }

    // 2FA 코드 검증
    public boolean verify(String email, String code) {
        return repo.findByEmailAndCode(email, code)
                .filter(c -> !c.isExpired())
                .isPresent();
    }

    // 사용된 코드 삭제
    public void clear(String email) {
        repo.findTopByEmailOrderByIdDesc(email)
            .ifPresent(repo::delete);
    }
}
