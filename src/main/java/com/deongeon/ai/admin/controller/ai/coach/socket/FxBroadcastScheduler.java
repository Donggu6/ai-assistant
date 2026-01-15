package com.deongeon.ai.admin.controller.ai.coach.socket;

import java.util.Map;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.deongeon.ai.admin.controller.ai.coach.state.FxState;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FxBroadcastScheduler {

  private final SimpMessagingTemplate ws;
  private final FxState fx;

  @Scheduled(fixedDelay = 10_000)
  public void push() {

    ws.convertAndSend("/topic/fx", Map.of(
      "cnyToKrw", fx.getCnyToKrw(),
      "shipping", fx.getShippingPerKg()
    ));
  }
}
