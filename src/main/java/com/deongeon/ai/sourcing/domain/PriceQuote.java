package com.deongeon.ai.sourcing.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceQuote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String channel;
    private int sellPriceKrw;
    private int profitKrw;

    @ManyToOne
    private ProductSource productSource;
}
