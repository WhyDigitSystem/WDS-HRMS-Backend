package com.efit.hrms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOfferDTO {

	private Long id;
	private String candidateName;
	private Long candidateId ;
	private String position;
	private String joiningDate;
	private String reportingPerson;
	private String reportingcode;
	private String reportingEmail;
	private String workLocation;
	private Long  probationPeriod;
	private String noticePeriod;
	private String workhours;
	private String templateType;
	private String additionalBenefits;
	private String specialTermsCondition;
	
	private long orgId;
	private String branchCode;
	private String branch;
	private String department;
	private String finYear;
	private String createdBy;
	
	public List<CompensationDetailsDTO> compensationDetailsDTO;
	
}
