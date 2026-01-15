package com.deongeon.ai.auth.oauth;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class OAuth2EmailExtractor {

    private OAuth2EmailExtractor() {}

    @SuppressWarnings("unchecked")
    public static String extract(Authentication authentication) {

        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attrs = user.getAttributes();

        // Google
        if (attrs.get("email") != null) {
            return String.valueOf(attrs.get("email"));
        }

        // Naver
        Object response = attrs.get("response");
        if (response instanceof Map<?, ?> map && map.get("email") != null) {
            return String.valueOf(map.get("email"));
        }

        throw new IllegalStateException("OAuth2 이메일을 찾을 수 없습니다.");
    }
}
