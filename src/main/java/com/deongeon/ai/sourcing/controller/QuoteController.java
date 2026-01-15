package com.deongeon.ai.sourcing.controller;

import com.deongeon.ai.sourcing.domain.*;
import com.deongeon.ai.sourcing.dto.*;
import com.deongeon.ai.sourcing.repository.ProductSourceRepository;
import com.deongeon.ai.sourcing.service.SourcingQuoteService;
import com.deongeon.ai.user.domain.AppUser;
import com.deongeon.ai.user.domain.Plan;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sourcing/quotes")
@RequiredArgsConstructor
public class QuoteController {

    private final ProductSourceRepository productSourceRepository;
    private final SourcingQuoteService quoteService;

    @PostMapping
    public Object createQuote(@RequestBody QuoteRequest request) {

        AppUser user = getCurrentUser();

        ProductSource source = productSourceRepository.findById(request.getProductSourceId())
                .orElseThrow(() -> new IllegalArgumentException("상품 소스 없음"));

        PriceQuote quote = quoteService.createQuote(source, request.getChannel());

        double productCost = source.getCostCny() * 190;
        double shippingCost = source.getWeightKg() * 8000;
        double totalCost = productCost + shippingCost;

        if (user.getPlan() == Plan.FREE) {
            return new QuoteResponse(
                quote.getId(),
                quote.getSellPriceKrw(),
                quote.getProfitKrw(),
                Math.round(totalCost)
            );
        }

        double marginRate = switch (request.getChannel().toLowerCase()) {
            case "smartstore" -> 0.35;
            case "coupang" -> 0.40;
            case "abroad" -> 0.45;
            default -> 0.30;
        };

        return new QuoteDetailResponse(
            source.getCostCny() + "위안",
            source.getWeightKg() + "kg",
            request.getChannel(),

            Math.round(productCost) + "원",
            Math.round(shippingCost) + "원",
            Math.round(totalCost) + "원",
            (int)(marginRate * 100) + "%",

            quote.getSellPriceKrw() + "원",
            quote.getProfitKrw() + "원",

            "이 상품을 " + quote.getSellPriceKrw() + "원에 팔면 " +
            quote.getProfitKrw() + "원 남습니다."
        );
    }

    private AppUser getCurrentUser() {
        AppUser user = new AppUser();
        user.setPlan(Plan.FREE);
        return user;
    }
}
