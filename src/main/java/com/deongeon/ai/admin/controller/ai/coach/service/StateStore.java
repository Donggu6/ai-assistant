package com.deongeon.ai.admin.controller.ai.coach.service;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import com.deongeon.ai.admin.controller.ai.coach.domain.CoachState;

@Component
public class StateStore {

    private final ConcurrentHashMap<String, CoachState> store = new ConcurrentHashMap<>();

    public CoachState get(String userId) {
        return store.computeIfAbsent(userId, k -> new CoachState());
    }

    public void reset(String userId) {
        store.remove(userId);
    }
}
