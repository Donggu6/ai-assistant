package com.deongeon.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.deongeon.ai.domain.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}
