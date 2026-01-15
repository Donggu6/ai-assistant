package com.deongeon.ai.sourcing.dto;

public class QuoteResponse {

    private Long quoteId;
    private double sellPrice;
    private double profit;

    // 🔥 추가: 총원가만 보여줌 (배송비 개별 표시는 제거)
    private double totalCost;

    public QuoteResponse(
            Long quoteId,
            double sellPrice,
            double profit,
            double totalCost
    ) {
        this.quoteId = quoteId;
        this.sellPrice = sellPrice;
        this.profit = profit;
        this.totalCost = totalCost;
    }

    public Long getQuoteId() { return quoteId; }
    public double getSellPrice() { return sellPrice; }
    public double getProfit() { return profit; }
    public double getTotalCost() { return totalCost; }
}
