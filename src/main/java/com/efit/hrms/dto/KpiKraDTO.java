package com.efit.hrms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KpiKraDTO {

	private Long id;

	private String appraisalId;
	private Long orgId;
	private String createdBy;
	private boolean active;
//	private String finYear;

	private String branchCode;

	private String branch;
	private String designation;

	private List<KpiKraDetailsDTO> kpiKraDetailsDTO;

	private List<KpiDTO> kpiDTO;

}
