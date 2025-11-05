package com.efit.hrms.dto;

import javax.persistence.Column;

import com.efit.hrms.entity.JobPostingsVO;

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
	private Long orgId;
	private String branch;
	private String branchCode;
	private String createdBy;
}
