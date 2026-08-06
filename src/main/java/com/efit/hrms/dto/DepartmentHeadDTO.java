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
public class DepartmentHeadDTO {
	
	private Long id;
	private String department;
	private String departmentCode;
	private String branchCode;
	private String branch;
	private String createdBy;
	private Long orgId;
	
	List<ReportingHeadDTO> reportingHeadDTO;
	List<ClearanceDetailsDTO> clearanceDetailsDTO;


}
