package com.efit.hrms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KpiKraDetailsDTO {

	private String kraId;
	private String kraDescription;
	private String ro;
	private String kpiId;
	private String kpiDescription;

}
