package com.deongeon.ai.analysis.logic;

public class ScoreCalculator {
    public static int score(double margin, int risk) {
        int v = (int) (margin * 1.2 - risk);
        if (v < 0) return 0;
        if (v > 100) return 100;
        return v;
    }
}
