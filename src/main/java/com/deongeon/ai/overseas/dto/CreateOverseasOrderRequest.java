package com.deongeon.ai.overseas.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateOverseasOrderRequest {
    private String customerName;
    private String productName;
    private double productPriceKrw;
    private String country;
}
