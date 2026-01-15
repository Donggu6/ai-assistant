package com.deongeon.ai.billing.controller;

import com.deongeon.ai.user.domain.Plan;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @PostMapping("/checkout")
    public String checkout(
            @RequestParam Plan plan,
            Authentication auth
    ) throws Exception {

        String email = auth.getName();

        if (plan == Plan.FREE) {
            throw new IllegalArgumentException("FREE 플랜은 결제할 수 없습니다.");
        }

        String priceId = switch (plan) {
            case BASIC -> "price_PRO_ID";
            case PREMIUM -> "price_PREMIUM_ID";
            case BUSINESS -> "price_BUSINESS_ID";
            default -> throw new IllegalStateException("Unexpected value: " + plan);
        };

        SessionCreateParams params = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
            .setSuccessUrl("http://localhost:8080/payment-success.html")
            .setCancelUrl("http://localhost:8080/payment-cancel.html")
            .putMetadata("plan", plan.name())
            .putMetadata("email", email)
            .addLineItem(
                SessionCreateParams.LineItem.builder()
                    .setPrice(priceId)
                    .setQuantity(1L)
                    .build()
            )
            .build();

        Session session = Session.create(params);

        return session.getUrl();
    }
}
