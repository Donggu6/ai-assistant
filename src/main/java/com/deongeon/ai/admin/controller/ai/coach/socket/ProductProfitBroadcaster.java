package com.deongeon.ai.admin.controller.ai.coach.socket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.deongeon.ai.product.domain.Product;

import com.deongeon.ai.admin.controller.ai.coach.dto.ProfitOutput;
import com.deongeon.ai.admin.controller.ai.coach.service.DynamicProfitEngine;
import com.deongeon.ai.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class ProductProfitBroadcaster {

	private final ProductRepository repository;
	private final DynamicProfitEngine engine;
	private final SimpMessagingTemplate ws;

	@Scheduled(fixedDelay = 10_000)
	public void broadcast() {

		List<Map<String, Object>> result = repository.findAll().stream().map((Product p) -> {
			ProfitOutput profit = engine.calculate(p);

			Map<String, Object> map = new HashMap<>();
			map.put("name", p.getName());
			map.put("sellPrice", p.getSellPriceKrw());
			map.put("profit", profit.profit());
			map.put("margin", profit.margin());

			return map;
		}).sorted((a, b) -> Double.compare((double) b.get("margin"), (double) a.get("margin"))).toList();

		ws.convertAndSend("/topic/products", result);
	}
}
