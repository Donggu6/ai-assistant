package com.deongeon.ai.admin.controller.ai.coach.state;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class FxState {

  private volatile double cnyToKrw = 190.0;
  private volatile double shippingPerKg = 8000;
  private volatile double platformFeeRate = 0.08;
  private volatile double paymentFeeRate = 0.03;

  public void updateRate(double rate) {
    this.cnyToKrw = rate;
  }
}
