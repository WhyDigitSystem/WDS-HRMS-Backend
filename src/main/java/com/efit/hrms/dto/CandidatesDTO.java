package com.efit.hrms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidatesDTO {

	
	private Long id;
	private String candidatesName;
	private String email;
	private String positionApplied;
	private int resumeScore;
	private LocalDate interviewDate;
	private String interviewTime;
	private String interviewer;
	private int rating;
	private String feedBack;
	private boolean active ;


	private String createdBy;
	private Long orgId;
	private String branchCode;
	private String branch;
}
