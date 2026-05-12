package com.efit.hrms.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetMasterDTO {

	private Long id;
	private String assetName;
//	private String assetCode;
	private String category;
	private String brand;	
	private String model;
	private String serialNumber;
	private LocalDate purchaseDate;
	private String purchaseCost;
	private LocalDate warrantyExpiry;
	private String location;
	private String notes;  	
	private boolean active ;

	
	private String branch;
	private String branchCode;	
	private String finyear;
	
	private String createdBy;
	private Long orgId;
	
	private MultipartFile[] files;
	
}
