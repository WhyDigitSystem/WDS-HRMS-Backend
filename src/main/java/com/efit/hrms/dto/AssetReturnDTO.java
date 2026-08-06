package com.efit.hrms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetReturnDTO {
	private Long id;
	private String assetName;
	private String assetCode;
	private String category;
	private String brand;
	private String model;
	private String serialNumber;
	private String employeeCode;
	private String employeeName;
	private LocalDate purchaseDate;
	private int purchaseCost;
	private LocalDate warrantyExpiry;
	private String location;
	private String notes;
	private String branch;
	private String branchCode;
	private String finyear;
	private LocalDate allocationDate;
	private LocalDate expectedreturndate;
	private String assetcondition;
	private String allocationnotes;
	private String locationCode;
	private String createdBy;
	private Long orgId;

}
