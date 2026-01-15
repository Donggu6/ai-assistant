package com.deongeon.ai.sourcing.controller;

import com.deongeon.ai.sourcing.domain.CrawlJob;
import com.deongeon.ai.sourcing.domain.ProductSource;
import com.deongeon.ai.sourcing.dto.AddSourceRequest;
import com.deongeon.ai.sourcing.dto.CreateJobRequest;
import com.deongeon.ai.sourcing.service.SourcingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sourcing")
@RequiredArgsConstructor
public class SourcingController {

    private final SourcingService sourcingService;

    @PostMapping("/jobs")
    public CrawlJob createJob(@RequestBody CreateJobRequest req) {
        return sourcingService.createJob(req.getKeyword());
    }

    @GetMapping("/jobs/{id}")
    public CrawlJob getJob(@PathVariable Long id) {
        return sourcingService.getJob(id);
    }

    @GetMapping("/jobs/{jobId}/sources")
    public List<ProductSource> getSources(@PathVariable Long jobId) {
        return sourcingService.getSources(jobId);
    }

    @PostMapping("/jobs/{jobId}/sources")
    public ProductSource addSource(
            @PathVariable Long jobId,
            @RequestBody AddSourceRequest req) {

        return sourcingService.addSourceToJob(
                jobId,
                req.getUrl(),
                req.getName(),
                req.getCostKrw(),
                req.getCostCny(),
                req.getWeightKg()
        );
    }

    @GetMapping("/sources/{sourceId}")
    public ProductSource getSource(@PathVariable Long sourceId) {
        return sourcingService.getSource(sourceId);
    }
}
