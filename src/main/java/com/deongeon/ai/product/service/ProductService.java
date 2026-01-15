package com.deongeon.ai.product.service;

import com.deongeon.ai.product.domain.Product;
import com.deongeon.ai.product.repository.ProductRepository;
import com.deongeon.ai.sourcing.domain.ProductSource;
import com.deongeon.ai.sourcing.service.SourcingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final SourcingService sourcingService;

    public Product create(Product p) {
        return productRepository.save(p);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
    }

    public Product update(Long id, Product update) {
        Product p = findById(id);
        p.setName(update.getName());
        p.setBrand(update.getBrand());
        p.setCategory(update.getCategory());
        p.setPrice(update.getPrice());
        p.setImageUrl(update.getImageUrl());
        return p;
    }

    public void delete(Long id) {
        productRepository.delete(findById(id));
    }

    @Transactional(readOnly = true)
    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Transactional(readOnly = true)
    public List<Product> searchByBrand(String brand) {
        return productRepository.findByBrandContainingIgnoreCase(brand);
    }

    @Transactional(readOnly = true)
    public List<Product> popular() {
        return productRepository.findTop10ByOrderByViewCountDesc();
    }

    // 🔥 소싱(Source) → 판매 상품(Product) 전환
    public Product createFromSource(Long sourceId, Double sellPrice, String category, String brand) {
        ProductSource source = sourcingService.getSource(sourceId);

        double cost = source.getCostKrw();
        double finalSellPrice = (sellPrice != null) ? sellPrice : autoSellPrice(cost);

        Product p = new Product();
        p.setName(source.getName());
        p.setCategory(category != null ? category : "DEFAULT");
        p.setBrand(brand != null ? brand : "NO_BRAND");
        p.setPrice(finalSellPrice);
        p.setViewCount(0);

        return productRepository.save(p);
    }

    private double autoSellPrice(double costKrw) {
        // 임시: 마진/수수료 포함 전략 (원하면 여기 고도화)
        if (costKrw <= 0) return 0;
        return costKrw * 1.6;
    }
}
