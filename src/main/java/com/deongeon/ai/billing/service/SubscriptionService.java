package com.deongeon.ai.billing.service;

import com.deongeon.ai.user.domain.AppUser;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

	public String createSubscriptionSession(AppUser user, String plan) throws Exception {

		String priceId = switch (plan.toUpperCase()) {
		case "BASIC" -> "price_BASIC_ID";
		case "PREMIUM" -> "price_PREMIUM_ID";
		case "BUSINESS" -> "price_BUSINESS_ID";
		default -> throw new IllegalArgumentException("잘못된 플랜");
		};

		SessionCreateParams params = SessionCreateParams.builder().setMode(SessionCreateParams.Mode.SUBSCRIPTION)
				.setSuccessUrl("http://localhost:8080/api/subscription/success?plan=" + plan)
				.setCancelUrl("http://localhost:8080/api/subscription/cancel")
				.addLineItem(SessionCreateParams.LineItem.builder().setPrice(priceId).setQuantity(1L).build()).build();

		return Session.create(params).getUrl();
	}
}
