package com.deongeon.ai.auth.repository;

import com.deongeon.ai.auth.domain.LoginFailLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginFailLogRepository 
        extends JpaRepository<LoginFailLog, Long> {
}
