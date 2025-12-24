package com.deongeon.ai.global.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.deongeon.ai.global.common.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ApiResponse<?> handle(RuntimeException e){
		return ApiResponse.fail(e.getMessage());
	}
}
