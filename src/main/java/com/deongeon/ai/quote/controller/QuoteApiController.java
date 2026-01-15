package com.deongeon.ai.quote.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/quotes")
public class QuoteApiController {

    @PostMapping
    public Map<String, Object> calculate(@RequestBody Map<String, Object> req) {

        String productSourceId = String.valueOf(req.get("productSourceId"));
        String channel = String.valueOf(req.get("channel"));

        Map<String, Object> result = new HashMap<>();

        // 🔹 상품 ID별 더미 데이터
        if ("1".equals(productSourceId)) {
            result.put("name", "무선 청소기");
            result.put("description", "강력한 흡입력, 3단 필터 시스템");
            result.put("image", "https://i.imgur.com/8Km9tLL.jpg");
            result.put("source", "1688");
            result.put("sellPrice", 89000);
            result.put("profit", 21000);
            result.put("totalCost", 68000);
        }
        else if ("2".equals(productSourceId)) {
            result.put("name", "블루투스 스피커");
            result.put("description", "저음 강화, 방수 지원");
            result.put("image", "https://i.imgur.com/0DElr0H.jpg");
            result.put("source", "Taobao");
            result.put("sellPrice", 59000);
            result.put("profit", 15000);
            result.put("totalCost", 44000);
        }
        else {
            result.put("name", "상품 정보 없음");
            result.put("description", "해당 ID의 상품이 없습니다.");
            result.put("image", "https://i.imgur.com/Z6aQZ0E.png");
            result.put("source", "N/A");
            result.put("sellPrice", 0);
            result.put("profit", 0);
            result.put("totalCost", 0);
        }

        return result;
    }
}
