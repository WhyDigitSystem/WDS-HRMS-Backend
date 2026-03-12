package com.efit.hrms.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InitiateSeparationDTO {
	
	private Long id;
	private String employeeName;
	private String employeeCode;
	private String department;
	private String position;
	private String reportingManager;
	private LocalDate joiningDate;
	private String separationType;
	private LocalDate resignation;
	private LocalDate lastWorkingDate;
	private int noticeDate;
	private String reasonCategory;
	private String rehireEligible;
	private String detailedReason;
	private String branchCode;
	private String branch;
	private String createdBy;
	private Long orgId;
	
	private String status;

	private LocalDate interviewDate;
	private int ExperienceRating;
	private String exitInterviewFeedback;
	private List<String> reportingPerson;
	private List<String> reportingPersonCode;
	private List<String> reportingPersonEmail;
	
 private List<ClearanceManagementDTO> clearanceManagementDTO;
	
}