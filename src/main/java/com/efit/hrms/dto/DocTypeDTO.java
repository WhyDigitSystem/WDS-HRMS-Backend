package com.efit.hrms.dto;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocTypeDTO {
	
	private Long id;
	private String screenCode;
	private String screenName;
	private String docCode;
	private String createdBy;
	private Long orgId;
	private int docCodePos;
	private int branchCodePos;
//	private int finYearPos;
	private int seqPos;
	private int seqDigit;
    private String codePattern; // e.g. ${companyCode}-${department}-${seq}

}