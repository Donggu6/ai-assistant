package com.deongeon.ai.overseas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuoteResult {
    private double sellPriceKrw;
    private double totalCost;
    private double netProfit;
    private double margin;
}
