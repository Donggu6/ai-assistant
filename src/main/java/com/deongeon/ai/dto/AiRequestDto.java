package com.deongeon.ai.dto;

import jakarta.validation.constraints.NotBlank;

public class AiRequestDto {

    @NotBlank
    private String prompt;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
