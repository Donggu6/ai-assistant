package com.deongeon.ai.overseas.service;

import com.deongeon.ai.overseas.domain.OverseasOrder;
import com.deongeon.ai.overseas.repository.OverseasOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OverseasOrderService {

    private final OverseasOrderRepository repository;

    public OverseasOrder create(OverseasOrder order) {
        order.setCreatedAt(LocalDateTime.now());
        return repository.save(order);
    }

    @Transactional(readOnly = true)
    public List<OverseasOrder> list() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public OverseasOrder get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }
}
