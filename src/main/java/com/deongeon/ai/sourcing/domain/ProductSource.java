package com.deongeon.ai.sourcing.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_source", schema = "sourcing")
public class ProductSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double costKrw;

    @Column(name = "source_url", nullable = false)
    private String sourceUrl;

    @Column(name = "source_site")
    private String sourceSite;

    @Column(name = "cost_cny")
    private double costCny;

    @Column(name = "weight_kg")
    private double weightKg;

    @Column(name = "category_code")
    private String categoryCode;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crawl_job_id")
    private CrawlJob crawlJob;

    public static ProductSource fromUrl(String url) {
        ProductSource ps = new ProductSource();
        ps.sourceUrl = url;
        ps.createdAt = LocalDateTime.now();
        return ps;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public double getCostKrw() { return costKrw; }
    public String getSourceUrl() { return sourceUrl; }
    public String getSourceSite() { return sourceSite; }
    public double getCostCny() { return costCny; }
    public double getWeightKg() { return weightKg; }
    public String getCategoryCode() { return categoryCode; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public CrawlJob getCrawlJob() { return crawlJob; }

    public void setName(String name) { this.name = name; }
    public void setCostKrw(double costKrw) { this.costKrw = costKrw; }
    public void setSourceUrl(String sourceUrl) { this.sourceUrl = sourceUrl; }
    public void setSourceSite(String sourceSite) { this.sourceSite = sourceSite; }
    public void setCostCny(double costCny) { this.costCny = costCny; }
    public void setWeightKg(double weightKg) { this.weightKg = weightKg; }
    public void setCategoryCode(String categoryCode) { this.categoryCode = categoryCode; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setCrawlJob(CrawlJob crawlJob) { this.crawlJob = crawlJob; }
}
