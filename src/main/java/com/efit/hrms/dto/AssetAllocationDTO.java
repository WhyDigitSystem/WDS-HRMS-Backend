package com.efit.hrms.dto;

import java.time.LocalDate;

import javax.persistence.Column;

import com.efit.hrms.entity.AssetAllocationVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetAllocationDTO {

	private Long id;
	private String assetName;
	private String assetCode;
	private String employeeCode;
	private String employeeName;	
	private LocalDate allocationDate;
	private LocalDate expectedreturndate;
	private String assetcondition;
	private String allocationnotes;
	private boolean active ;

	private String branch;
	private String branchCode;	
	private String finyear;
	private String createdBy;
	private Long orgId;
}
