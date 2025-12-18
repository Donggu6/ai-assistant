package com.deongeon.ai.exception;

import com.deongeon.ai.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiResponse<Void>> handle(RuntimeException e) {
		String code = e.getMessage() == null ? "UNKNOWN" : e.getMessage();
		return ResponseEntity.badRequest().body(ApiResponse.fail(code, code));
	}
}
