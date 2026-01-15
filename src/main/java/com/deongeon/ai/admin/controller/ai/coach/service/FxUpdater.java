package com.deongeon.ai.admin.controller.ai.coach.service;

import java.util.Map;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.deongeon.ai.admin.controller.ai.coach.state.FxState;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class FxUpdater {

  private final FxState fx;

  // ✅ 1) 여기에 "네 실제 키"를 그대로 넣어
  private static final String API_KEY = "9787a396813517e99d3fec9f"; // <- 네 키 전체

  // ✅ 2) baseUrl을 명확히 잡아두기
  private final WebClient client = WebClient.builder()
      .baseUrl("https://v6.exchangerate-api.com/v6/" + API_KEY)
      .build();

  @Scheduled(fixedDelay = 10_000)
  public void refresh() {

    // ✅ 3) 지금 어떤 URL로 나가는지 로그로 박아서 확인
    String uri = "/latest/KRW";
    System.out.println("[FX] Request URL = https://v6.exchangerate-api.com/v6/" + API_KEY + uri);

    try {
      Map res = client.get()
          .uri(uri)
          .retrieve()
          .bodyToMono(Map.class)
          .block();

      Map rates = (Map) res.get("conversion_rates");
      double krwToCny = ((Number) rates.get("CNY")).doubleValue();

      // 기존 로직 유지: 1/CNY는 "CNY -> KRW"로 뒤집는 형태일 가능성 높음
      fx.updateRate(1 / krwToCny);

      System.out.println("[FX] OK: KRW->CNY=" + krwToCny + ", updated=" + (1 / krwToCny));

    } catch (Exception e) {
      System.out.println("[FX] FAIL: " + e.getMessage());
    }
  }
}
