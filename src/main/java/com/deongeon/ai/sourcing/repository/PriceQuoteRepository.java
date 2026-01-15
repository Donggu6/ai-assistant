package com.deongeon.ai.sourcing.repository;

import com.deongeon.ai.sourcing.domain.PriceQuote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceQuoteRepository
        extends JpaRepository<PriceQuote, Long> {
}
