package com.efit.hrms.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.hrms.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "assetreturn")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetReturnVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assetreturngen")
	@SequenceGenerator(name = "assetreturngen", sequenceName = "assetreturnseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "assetreturnid")
	private Long id;
	@Column(name = "assetname")
	private String assetName;
	@Column(name = "assetcode")
	private String assetCode;
	@Column(name = "category")
	private String category;
	@Column(name = "brand")
	private String brand;
	@Column(name = "model")
	private String model;
	@Column(name = "serialnumber")
	private String serialNumber;
	@Column(name = "employeecode")
	private String employeeCode;
	@Column(name = "employeename")
	private String employeeName;

	@Column(name = "allocationdate")
	private LocalDate allocationDate;
	@Column(name = "expectedreturndate")
	private LocalDate expectedreturndate;
	@Column(name = "assetcondition")
	private String assetcondition;
	@Column(name = "allocationnotes")
	private String allocationnotes;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "purchasedate")
	private LocalDate purchaseDate;
	@Column(name = "purchasecost")
	private int purchaseCost;
	@Column(name = "warrantyexpiry")
	private LocalDate warrantyExpiry;
	@Column(name = "location")
	private String location;
	@Column(name = "locationcode")
	private String locationCode;
	@Column(name = "notes")
	private String notes;

	@Column(name = "branch")
	private String branch;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "finyear")
	private String finyear;

	@Column(name = "active")
	private boolean active;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "cancel")
	private boolean cancel;

	@Column(name = "screencode", length = 5)
	private String screenCode = "AR";

	@Column(name = "screenname", length = 25)
	private String screenName = "ASSET RETURN";

	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
