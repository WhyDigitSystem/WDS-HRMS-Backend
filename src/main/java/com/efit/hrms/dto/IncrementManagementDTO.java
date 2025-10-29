package com.efit.hrms.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncrementManagementDTO {

	private Long id;
	private String employeeName;
	private String employeeCode;
	private String department;
	private String designation;
	private String location;
	private LocalDate joiningDate;
	private String reportingTo;
	private String incrementCycle;
	private LocalDate effectiveFrom;
	private String adjustmentType;
	private String adjustmentValue;
	private String newDesignation;
	private String newGrade;
	private String remarks;
	private String nextApproval;
	private String reportingPersonCode;
	private String reportingPersonEmail;
	private String branch;
	private String branchCode;

//	private String totalctc;
//	private String totalCtcPercentage;
	private Long orgId;
	
//	private String approveStatus;
//	private String approveBy;
//	private String approveOn;
	
	private String createdBy;
	
	private List<IncrementManagementDetailsDTO> incrementManagementDetailsDTO;
	
	
}
