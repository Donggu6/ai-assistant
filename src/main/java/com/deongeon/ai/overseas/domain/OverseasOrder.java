package com.deongeon.ai.overseas.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class OverseasOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String productName;

    private double productPriceKrw;
    private String country;        // 예: KR, CN, JP

    private LocalDateTime createdAt;
}
