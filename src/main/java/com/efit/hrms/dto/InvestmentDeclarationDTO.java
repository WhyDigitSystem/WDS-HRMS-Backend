package com.efit.hrms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestmentDeclarationDTO {
	private Long id;
	private Long orgId;
	private String branch;
	private String branchCode;
	private String createdBy;
	private String employeeName;
	private String employeeCode;

	List<InvestmentDeclarationDetailsDTO> investmentDeclarationDetailsDTO;

}
