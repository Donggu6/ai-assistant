package com.deongeon.ai.admin.controller;

import com.deongeon.ai.user.domain.AppUser;
import com.deongeon.ai.user.domain.Plan;
import com.deongeon.ai.user.repository.AppUserRepository;
import com.deongeon.ai.user.security.RequirePlan;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AppUserRepository userRepo;

    // 👥 전체 유저 조회 (BUSINESS)
    @RequirePlan(Plan.BUSINESS)
    @GetMapping("/users")
    public List<AppUser> getUsers() {
        return userRepo.findAll();
    }

    // 🔁 유저 플랜 변경 (BUSINESS)
    @RequirePlan(Plan.BUSINESS)
    @PatchMapping("/users/{id}/plan")
    public AppUser updatePlan(
            @PathVariable Long id,
            @RequestParam Plan plan
    ) {
        AppUser user = userRepo.findById(id).orElseThrow();
        user.setPlan(plan);
        return userRepo.save(user);
    }
}
