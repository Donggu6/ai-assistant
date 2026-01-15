package com.deongeon.ai.billing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deongeon.ai.billing.domain.CreditWallet;
import com.deongeon.ai.user.domain.AppUser;

public interface CreditWalletRepository extends JpaRepository<CreditWallet, Long> {
    Optional<CreditWallet> findByUser(AppUser user);
}
