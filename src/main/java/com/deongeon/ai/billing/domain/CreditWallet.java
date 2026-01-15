package com.deongeon.ai.billing.domain;

import com.deongeon.ai.user.domain.AppUser;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class CreditWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int balance;

    @OneToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    public CreditWallet(AppUser user) {
        this.user = user;
        this.balance = 0;
    }

    public void charge(int amount) {
        this.balance += amount;
    }

    public void use(int amount) {
        if (this.balance < amount) {
            throw new RuntimeException("크레딧 부족");
        }
        this.balance -= amount;
    }
}
