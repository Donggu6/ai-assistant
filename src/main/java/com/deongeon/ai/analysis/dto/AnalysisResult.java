package com.deongeon.ai.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnalysisResult {
    private String message;
    private int score;
}
