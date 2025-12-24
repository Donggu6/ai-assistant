package com.deongeon.ai.consignment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsignmentRequest {
	
	private String brand;
	private String modelName;
	private String size;
	private String conditionState;
	private Integer targetPrice;
}
