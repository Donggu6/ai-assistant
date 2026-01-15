package com.deongeon.ai.sourcing.dto;

public class QuoteDetailResponse {

    private String costCny;
    private String weightKg;
    private String channel;

    private String productCost;
    private String shippingCost;
    private String totalCost;
    private String marginRate;

    private String sellPrice;
    private String profit;

    private String message;

    public QuoteDetailResponse(
            String costCny,
            String weightKg,
            String channel,
            String productCost,
            String shippingCost,
            String totalCost,
            String marginRate,
            String sellPrice,
            String profit,
            String message
    ) {
        this.costCny = costCny;
        this.weightKg = weightKg;
        this.channel = channel;
        this.productCost = productCost;
        this.shippingCost = shippingCost;
        this.totalCost = totalCost;
        this.marginRate = marginRate;
        this.sellPrice = sellPrice;
        this.profit = profit;
        this.message = message;
    }

    public String getCostCny() { return costCny; }
    public String getWeightKg() { return weightKg; }
    public String getChannel() { return channel; }
    public String getProductCost() { return productCost; }
    public String getShippingCost() { return shippingCost; }
    public String getTotalCost() { return totalCost; }
    public String getMarginRate() { return marginRate; }
    public String getSellPrice() { return sellPrice; }
    public String getProfit() { return profit; }
    public String getMessage() { return message; }
}
