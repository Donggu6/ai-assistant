package com.deongeon.ai.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deongeon.ai.auth.domain.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
}
