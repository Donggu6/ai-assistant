package com.deongeon.ai.analysis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnalysisPageController {

    @GetMapping("/analysis")
    public String analysisPage() {
        return "analysis";
    }
}