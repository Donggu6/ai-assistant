package com.deongeon.ai.analysis.logic;

public class ProfitCalculator {

    public static double marginPercent(double cost, double sell) {
        if (cost <= 0) return 0;
        return ((sell - cost) / cost) * 100.0;
    }
}
