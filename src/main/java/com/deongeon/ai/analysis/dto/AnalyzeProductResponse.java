package com.deongeon.ai.analysis.dto;

public record AnalyzeProductResponse(
        Long productId,
        String sourceUrl,
        String productName,

        double costKrw,
        double shippingKrw,
        double feeKrw,
        double expectedSellPriceKrw,
        double profitKrw,

        int profitScore,      // 0~100
        int riskScore,        // 0~100 (높을수록 위험)
        String recommendation // "등록 추천" / "보류" 등
) {}
