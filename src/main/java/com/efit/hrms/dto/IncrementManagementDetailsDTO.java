package com.efit.hrms.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncrementManagementDetailsDTO {

	private Long id;
	private String heading;
	private BigDecimal amount;
	
}
