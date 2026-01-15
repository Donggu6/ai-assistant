package com.deongeon.ai.overseas.service;

import org.springframework.stereotype.Service;

import com.deongeon.ai.overseas.dto.OverseasOrderRequest;
import com.deongeon.ai.overseas.dto.QuoteResult;

@Service
public class OverseasQuoteService {

    public QuoteResult calculate(OverseasOrderRequest req) {

        double exchange = 190;           
        double intlShipPerKg = 8000;     
        double domesticShip = 3000;     
        double platformFeeRate = 0.10;  
        double dutyRate = 0.08;         
        double vatRate = 0.10;          

        double costKrw = req.getCostCny() * exchange;
        double intlShip = req.getWeightKg() * intlShipPerKg;

        double productCost = costKrw + intlShip + domesticShip;
        double duty = productCost * dutyRate;
        double vat = productCost * vatRate;

        double totalCost = productCost + duty + vat;
        double sellPrice = totalCost / (1 - platformFeeRate);
        double profit = sellPrice - totalCost;
        double margin = (profit / sellPrice) * 100;

        return new QuoteResult(
                Math.round(sellPrice),
                Math.round(totalCost),
                Math.round(profit),
                Math.round(margin * 10) / 10.0
        );
    }
}
