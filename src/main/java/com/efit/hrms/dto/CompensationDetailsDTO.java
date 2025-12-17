package com.efit.hrms.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompensationDetailsDTO {

	private String componentType;
	private BigDecimal amount;
}
