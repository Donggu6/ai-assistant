package com.deongeon.ai.dto;

public class ApiResponse<T> {

	private final boolean success;
	private final T data;
	private final String message;

	private ApiResponse(boolean success, T data, String message) {
		this.success = success;
		this.data = data;
		this.message = message;
	}

	public static <T> ApiResponse<T> ok(T data) {
		return new ApiResponse<>(true, data, null);
	}

	public static ApiResponse<?> ok() {
		return new ApiResponse<>(true, null, null);
	}

	public static ApiResponse<?> fail(String message) {
		return new ApiResponse<>(false, null, message);
	}

	public boolean isSuccess() {
		return success;
	}

	public T getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}
}