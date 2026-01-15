package com.deongeon.ai.sourcing.dto;

public class QuoteRequest {

    private Long productSourceId;
    private String channel;

    public Long getProductSourceId() {
        return productSourceId;
    }

    public void setProductSourceId(Long productSourceId) {
        this.productSourceId = productSourceId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
