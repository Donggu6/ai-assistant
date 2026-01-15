package com.deongeon.ai.analysis.controller;

import com.deongeon.ai.analysis.dto.AnalysisResult;
import com.deongeon.ai.analysis.service.AnalysisService;
import com.deongeon.ai.user.domain.AppUser;
import com.deongeon.ai.user.repository.AppUserRepository;
import com.deongeon.ai.user.service.PlanPolicy;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    private final AnalysisService analysisService;
    private final AppUserRepository userRepo;

    public AnalysisController(AnalysisService analysisService, AppUserRepository userRepo) {
        this.analysisService = analysisService;
        this.userRepo = userRepo;
    }

    @PostMapping("/basic")
    public AnalysisResult analyzeBasic(@RequestBody String url) {
        return analysisService.analyzeBasic(url);
    }

    @PostMapping("/advanced")
    public AnalysisResult advancedAnalysis(@RequestBody String url, Authentication auth) {
        AppUser user = userRepo.findByEmail(auth.getName()).orElseThrow();

        if (!PlanPolicy.canUseAdvancedAnalysis(user.getPlan())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "BASIC 이상 플랜 필요");
        }

        return analysisService.analyzeWithCredit(url, user);
    }

    @GetMapping("/automation")
    public String runAutomation(Authentication auth) {
        AppUser user = userRepo.findByEmail(auth.getName()).orElseThrow();

        if (!PlanPolicy.canUseAutomation(user.getPlan())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "PREMIUM 이상 필요");
        }

        return "자동화 실행 완료";
    }

    @GetMapping("/team")
    public String teamFeature(Authentication auth) {
        AppUser user = userRepo.findByEmail(auth.getName()).orElseThrow();

        if (!PlanPolicy.canUseTeamFeature(user.getPlan())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "BUSINESS 전용");
        }

        return "팀 관리 기능";
    }
}
