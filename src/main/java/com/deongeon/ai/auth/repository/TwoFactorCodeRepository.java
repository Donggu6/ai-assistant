package com.deongeon.ai.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deongeon.ai.auth.domain.TwoFactorCode;

public interface TwoFactorCodeRepository
extends JpaRepository<TwoFactorCode, Long> {

Optional<TwoFactorCode> findByEmail(String email);

void deleteByEmail(String email);
}
