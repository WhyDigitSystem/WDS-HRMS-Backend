package com.efit.hrms.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestmentDeclarationDetailsDTO {
	private String section;
	private String investmentType;
	private BigDecimal declared;
	private BigDecimal limitAmount;
	private String proof;

}
