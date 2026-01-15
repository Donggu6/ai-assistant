package com.deongeon.ai.sourcing.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SourcingResponse {
	private Long id;
	private String keyword;
	private String status;
	private int resultCount;
	private String message;
	private LocalDateTime startedAt;
	private LocalDateTime finishedAt;
}
