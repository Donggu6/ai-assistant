package com.deongeon.ai.sourcing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deongeon.ai.sourcing.domain.CrawlJob;

public interface CrawlJobRepository extends JpaRepository<CrawlJob, Long> {

}
