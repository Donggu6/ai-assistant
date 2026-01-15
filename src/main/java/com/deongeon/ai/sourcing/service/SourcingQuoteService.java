package com.deongeon.ai.sourcing.service;

import org.springframework.stereotype.Service;

import com.deongeon.ai.sourcing.domain.PriceQuote;
import com.deongeon.ai.sourcing.domain.ProductSource;
import com.deongeon.ai.sourcing.repository.PriceQuoteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SourcingQuoteService {

    private final PriceQuoteRepository priceQuoteRepository;

    public PriceQuote createQuote(ProductSource source, String channel) {

        double productCost = source.getCostCny() * 190;
        double sellPrice = productCost * 1.5;
        double profit = sellPrice - productCost;

        PriceQuote quote = PriceQuote.builder()
                .productSource(source)
                .channel(channel)
                .sellPriceKrw((int) sellPrice)
                .profitKrw((int) profit)
                .build();

        return priceQuoteRepository.save(quote);
    }
}
