package com.efit.hrms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExitInterviewDepartmentDTO {

	private Long id;
	private String designation;
	private String designationCode;
	private String branch;
	private String branchCode;
	private Long orgId;
	private String createdBy;
	private boolean active;	
	
    private List<QuestionDTO> questionDTO; 


}
