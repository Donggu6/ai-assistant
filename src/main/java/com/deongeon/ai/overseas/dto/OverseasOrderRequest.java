package com.deongeon.ai.overseas.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OverseasOrderRequest {

    private String productName;
    private String productUrl;
    private double productPrice;
    private String country;
    private String userEmail;
    private double costCny;
    private double weightKg;

}
