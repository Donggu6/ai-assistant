package com.deongeon.ai.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deongeon.ai.product.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(String category);

    List<Product> findByBrandContainingIgnoreCase(String brand);

    List<Product> findTop10ByOrderByViewCountDesc();
}
