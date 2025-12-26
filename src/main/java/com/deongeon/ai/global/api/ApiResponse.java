package com.deongeon.ai.global.api;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> ApiResponse<T> ok(String msg, T data){
        return new ApiResponse<>(true, msg, data);
    }

    public static ApiResponse<?> fail(String msg){
        return new ApiResponse<>(false, msg, null);
    }
}
