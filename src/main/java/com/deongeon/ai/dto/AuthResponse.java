package com.deongeon.ai.dto;

public class AuthResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private String email;
    private String role;
    private String subscriptionType;

    public AuthResponse(String accessToken, String email, String role, String subscriptionType) {
        this.accessToken = accessToken;
        this.email = email;
        this.role = role;
        this.subscriptionType = subscriptionType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }
}
