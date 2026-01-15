package com.deongeon.ai.sourcing.service;

import com.deongeon.ai.sourcing.domain.CrawlJob;
import com.deongeon.ai.sourcing.domain.ProductSource;
import com.deongeon.ai.sourcing.repository.CrawlJobRepository;
import com.deongeon.ai.sourcing.repository.ProductSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SourcingService {

    private final CrawlJobRepository crawlJobRepository;
    private final ProductSourceRepository productSourceRepository;

    public CrawlJob createJob(String keyword) {
        CrawlJob job = new CrawlJob();
        job.setKeyword(keyword);
        job.setCreatedAt(LocalDateTime.now());
        return crawlJobRepository.save(job);
    }

    public CrawlJob getJob(Long id) {
        return crawlJobRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Job not found"));
    }

    public List<ProductSource> getSources(Long jobId) {
        return productSourceRepository.findByCrawlJobId(jobId);
    }

    public ProductSource addSourceToJob(
            Long jobId,
            String url,
            String name,
            Double costKrw,
            Double costCny,
            Double weightKg
    ) {
        CrawlJob job = getJob(jobId);

        ProductSource source = productSourceRepository.findBySourceUrl(url)
                .orElse(ProductSource.fromUrl(url));

        source.setCrawlJob(job);

        if (name != null) source.setName(name);
        if (costKrw != null) source.setCostKrw(costKrw);
        if (costCny != null) source.setCostCny(costCny);
        if (weightKg != null) source.setWeightKg(weightKg);

        if (source.getCreatedAt() == null)
            source.setCreatedAt(LocalDateTime.now());

        return productSourceRepository.save(source);
    }

    public ProductSource getSource(Long sourceId) {
        return productSourceRepository.findById(sourceId)
                .orElseThrow(() -> new IllegalArgumentException("Source not found"));
    }
}
