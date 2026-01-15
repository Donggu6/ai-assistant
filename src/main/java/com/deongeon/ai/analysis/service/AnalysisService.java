package com.deongeon.ai.analysis.service;

import com.deongeon.ai.analysis.dto.AnalysisResult;
import com.deongeon.ai.user.domain.AppUser;
import org.springframework.stereotype.Service;

@Service
public class AnalysisService {

    public AnalysisResult analyzeBasic(String url) {
        return new AnalysisResult("BASIC 분석 완료: " + url, 50);
    }

    public AnalysisResult analyzeWithCredit(String url, AppUser user) {
        return new AnalysisResult("ADVANCED 분석 완료: " + url + " / user=" + user.getEmail(), 85);
    }
}
