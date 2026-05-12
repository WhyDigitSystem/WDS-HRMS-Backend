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
public class JobPostingsDTO {

	private Long id;
	private String jobTitle;
	private String department;
	private String location;
    private List<String> education;
    private List<String> skills;
    private List<String> keywords;
	private Long orgId;
	private String branch;
	private boolean active;
	private String branchCode;
	private String createdBy;
	private String description;
	private String experience;
}
