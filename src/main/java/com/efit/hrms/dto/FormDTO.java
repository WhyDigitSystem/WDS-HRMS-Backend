package com.efit.hrms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormDTO {

	private Long id;

	private String createdBy;
	private String employeeName;
	private String employeeCode;
	private Long orgId;
	private String email;
	private String branch;
	private String branchCode;

}
