package com.deongeon.ai.consignment.dto;

import com.deongeon.ai.consignment.domain.ConsignmentStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConsignmentResponse {
	
	private Long id;
	private String brand;
	private String modelName;
	private String size;
	private String conditionState;
	private Integer targetPrice;
	private ConsignmentStatus status;
}
