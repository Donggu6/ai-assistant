package com.deongeon.ai.billing.service;

import com.deongeon.ai.billing.domain.CreditWallet;
import com.deongeon.ai.billing.repository.CreditWalletRepository;
import com.deongeon.ai.user.domain.AppUser;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CreditService {

    private final CreditWalletRepository walletRepo;

    public CreditWallet getOrCreate(AppUser user) {
        return walletRepo.findByUser(user)
                .orElseGet(() -> walletRepo.save(new CreditWallet(user)));
    }

    public void charge(AppUser user, int amount) {
        CreditWallet wallet = getOrCreate(user);
        wallet.charge(amount);
    }

    public void use(AppUser user, int amount) {
        CreditWallet wallet = getOrCreate(user);
        wallet.use(amount);
    }

    public int getBalance(AppUser user) {
        return getOrCreate(user).getBalance();
    }
}
