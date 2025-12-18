package com.deongeon.ai.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.deongeon.ai.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ApiResponse<?> handle(RuntimeException e) {
		return ApiResponse.fail(e.getMessage());
	}
}
