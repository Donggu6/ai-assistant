package com.deongeon.ai.product.controller;

import com.deongeon.ai.product.domain.Product;
import com.deongeon.ai.product.dto.CreateProductFromSourceRequest;
import com.deongeon.ai.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public Product create(@RequestBody Product p) {
        return productService.create(p);
    }

    @GetMapping
    public List<Product> list() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product detail(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product p) {
        return productService.update(id, p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    // 🔥 소싱 결과(Source)로부터 상품 생성
    @PostMapping("/from-source")
    public Product createFromSource(@RequestBody CreateProductFromSourceRequest req) {
        return productService.createFromSource(
                req.getSourceId(),
                req.getSellPrice(),
                req.getCategory(),
                req.getBrand()
        );
    }
}
