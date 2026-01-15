package com.deongeon.ai.analysis.logic;

public class RiskScoring {
    public static int score(Double risk) {
        if (risk == null) return 0;
        if (risk > 70) return 80;
        if (risk > 40) return 50;
        return 20;
    }
}
