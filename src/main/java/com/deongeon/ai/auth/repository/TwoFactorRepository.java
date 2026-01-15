package com.deongeon.ai.auth.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.deongeon.ai.auth.domain.TwoFactorCode;

public interface TwoFactorRepository extends JpaRepository<TwoFactorCode, Long> {

    Optional<TwoFactorCode> findTopByEmailOrderByIdDesc(String email);

    Optional<TwoFactorCode> findByEmailAndCode(String email, String code);
}
