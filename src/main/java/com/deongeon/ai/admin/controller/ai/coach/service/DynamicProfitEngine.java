package com.deongeon.ai.admin.controller.ai.coach.service;

import org.springframework.stereotype.Service;

import com.deongeon.ai.admin.controller.ai.coach.dto.ProfitOutput;
import com.deongeon.ai.admin.controller.ai.coach.state.FxState;
import com.deongeon.ai.product.domain.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DynamicProfitEngine {

	private final FxState fx;

	public ProfitOutput calculate(Product p) {

		double productCost = p.getCostCny() * fx.getCnyToKrw();
		double shipping = p.getWeightKg() * fx.getShippingPerKg();
		double platformFee = p.getSellPriceKrw() * fx.getPlatformFeeRate();
		double paymentFee = p.getSellPriceKrw() * fx.getPaymentFeeRate();

		double total = productCost + shipping + platformFee + paymentFee;
		double profit = p.getSellPriceKrw() - total;

		return new ProfitOutput(total, profit, profit / p.getSellPriceKrw() * 100);
	}
}
