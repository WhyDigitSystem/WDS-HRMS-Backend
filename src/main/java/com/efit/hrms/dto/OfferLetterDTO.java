package com.efit.hrms.dto;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferLetterDTO {

	private Long id;
	private String candidatesName;
	private String email;
	private String position;
	private String department;
	private String location;
	private String remarks;
	private boolean active ;
	private String createdBy;
	private Long orgId;
	private String branchCode;
	private String branch;


}
