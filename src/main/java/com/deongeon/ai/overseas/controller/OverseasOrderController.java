package com.deongeon.ai.overseas.controller;

import com.deongeon.ai.overseas.domain.OverseasOrder;
import com.deongeon.ai.overseas.dto.CreateOverseasOrderRequest;
import com.deongeon.ai.overseas.service.OverseasOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/overseas/orders")
@RequiredArgsConstructor
public class OverseasOrderController {

    private final OverseasOrderService service;

    @PostMapping
    public OverseasOrder create(@RequestBody CreateOverseasOrderRequest req) {
        OverseasOrder o = new OverseasOrder();
        o.setCustomerName(req.getCustomerName());
        o.setProductName(req.getProductName());
        o.setProductPriceKrw(req.getProductPriceKrw());
        o.setCountry(req.getCountry());
        return service.create(o);
    }

    @GetMapping
    public List<OverseasOrder> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public OverseasOrder get(@PathVariable Long id) {
        return service.get(id);
    }
}
