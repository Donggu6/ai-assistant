package com.deongeon.ai.admin.controller.ai.coach.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.deongeon.ai.admin.controller.ai.coach.domain.CoachState;
import com.deongeon.ai.admin.controller.ai.coach.dto.*;
import com.deongeon.ai.admin.controller.ai.coach.util.Extractors;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoachService {

    private final StateStore stateStore;

    public CoachChatResponse chat(String userId, CoachChatRequest req) {
        CoachState state = stateStore.get(userId);
        String msg = req.getMessage();

        CoachChatResponse res = new CoachChatResponse();

        // 사업 종류
        if (state.getBusinessType() == null) {
            if (Extractors.contains(msg, "구매대행", "위탁", "쇼핑몰")) {
                state.setBusinessType(msg);
                res.setReply("좋아요. 판매 채널은 어디인가요? (스마트스토어 / 쿠팡 / 해외몰)");
            } else {
                res.setReply("어떤 비즈니스를 하실 계획인가요? (구매대행 / 위탁판매 등)");
            }
            return res;
        }

        // 채널
        if (state.getChannel() == null) {
            state.setChannel(msg);
            res.setReply("상품 원가는 얼마인가요?");
            return res;
        }

        // 원가
        if (state.getCost() == null) {
            Integer cost = Extractors.extractPrice(msg);
            if (cost != null) {
                state.setCost(cost);
                res.setReply("판매가는 얼마로 생각하시나요?");
            } else {
                res.setReply("숫자로 원가를 입력해주세요. 예: 15000");
            }
            return res;
        }

        // 판매가
        if (state.getPrice() == null) {
            Integer price = Extractors.extractPrice(msg);
            if (price != null) {
                state.setPrice(price);

                int profit = price - state.getCost();

                res.setReply("대략적인 예상 이익은 " + profit + "원입니다.\n\n다음 단계로 무엇을 할까요?");
                res.setActions(List.of(
                    new CoachAction("📊 실수익 계산", "profit"),
                    new CoachAction("📄 견적서 생성", "quote"),
                    new CoachAction("📈 가격 전략", "pricing")
                ));
                res.setState(toView(state));

            } else {
                res.setReply("숫자로 판매가를 입력해주세요.");
            }
            return res;
        }

        res.setReply("원하시는 작업을 선택해주세요.");
        return res;
    }

    private CoachStateView toView(CoachState s) {
        CoachStateView v = new CoachStateView();
        v.setBusinessType(s.getBusinessType());
        v.setChannel(s.getChannel());
        v.setCost(s.getCost());
        v.setPrice(s.getPrice());
        return v;
    }

    public void reset(String userId) {
        stateStore.reset(userId);
    }
}
