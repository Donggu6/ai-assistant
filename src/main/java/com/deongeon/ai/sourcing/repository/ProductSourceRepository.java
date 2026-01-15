package com.deongeon.ai.sourcing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.deongeon.ai.sourcing.domain.ProductSource;

import java.util.List;
import java.util.Optional;

public interface ProductSourceRepository extends JpaRepository<ProductSource, Long> {
    Optional<ProductSource> findBySourceUrl(String url);
    
    List<ProductSource> findByCrawlJobId(Long crawJobId);
}
