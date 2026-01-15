package com.deongeon.ai.auth.service;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TwoFactorStore {

    // 5분 유효
    private static final long TTL_MILLIS = 5 * 60 * 1000L;

    public static class Challenge {
        private final String code;
        private final long expiresAt;

        public Challenge(String code, long expiresAt) {
            this.code = code;
            this.expiresAt = expiresAt;
        }

        public String getCode() {
            return code;
        }

        public boolean isExpired() {
            return Instant.now().toEpochMilli() > expiresAt;
        }
    }

    private final Map<String, Challenge> map = new ConcurrentHashMap<>();

    public void put(String email, Challenge ch) {
        map.put(email, ch);
    }

    public Challenge get(String email) {
        return map.get(email);
    }

    public void remove(String email) {
        map.remove(email);
    }

    public long expiresAtFromNow() {
        return Instant.now().toEpochMilli() + TTL_MILLIS;
    }
}
