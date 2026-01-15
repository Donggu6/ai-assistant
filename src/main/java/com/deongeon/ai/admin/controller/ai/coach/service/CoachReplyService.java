package com.deongeon.ai.admin.controller.ai.coach.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoachReplyService {

    // 1단계: 규칙 기반 “현실형” 응답 (2단계에서 OpenAI로 교체)
    public List<String> replyChunks(String userMsg) {
        String msg = userMsg == null ? "" : userMsg.trim();

        String reply;
        if (msg.isBlank()) {
            reply = "질문을 한 줄로만 적어줘. 예: \"이 상품 마진 괜찮아?\"";
        } else if (msg.contains("마진") || msg.contains("수익") || msg.contains("이익")) {
            reply = """
            좋아. 감이 아니라 구조로 보자.

            1) 판매가(예상) / 원가 / 배송비 / 플랫폼 수수료(%) / 결제수수료(%)를 알려줘.
            2) 지금 목표 마진(%)이 몇 %인지도.
            
            숫자 5개만 오면 바로 실수익 계산해서 “가능/보류/위험”으로 판단해줄게.
            """;
        } else if (msg.contains("견적") || msg.contains("견적서")) {
            reply = """
            견적은 “가격 + 근거 + 조건”이 핵심이야.
            상품명/원가/배송비/판매채널/목표마진을 주면
            견적 문구 + 금액 + 조건까지 한 번에 만들어줄게.
            """;
        } else {
            reply = """
            오케이. 우선 목표를 분해하자.

            ✅ 지금 상황(무엇을 팔고/어디서 팔고/현재 문제 1개)을 말해줘.
            내가 “지금 당장 할 행동 3개”로 정리해서 안내할게.
            """;
        }

        // “실시간처럼 보이게” chunk로 쪼개기
        return splitToChunks(reply, 18);
    }

    private List<String> splitToChunks(String text, int chunkSize) {
        List<String> chunks = new ArrayList<>();
        int i = 0;
        while (i < text.length()) {
            int end = Math.min(i + chunkSize, text.length());
            chunks.add(text.substring(i, end));
            i = end;
        }
        return chunks;
    }
}
