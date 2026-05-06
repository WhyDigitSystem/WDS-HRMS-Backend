package com.efit.hrms.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.hrms.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "assetmaster")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetMasterVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assetmastergen")
	@SequenceGenerator(name = "assetmastergen", sequenceName = "assetmasterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "assetmasterid")
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
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "purchasedate")
	private LocalDate purchaseDate;
	@Column(name = "purchasecost")
	private String purchaseCost;
	@Column(name = "warrantyexpiry")
	private LocalDate warrantyExpiry;
	@Column(name = "location")
	private String location;
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
	private String screenCode = "AM";

	@Column(name = "screenname", length = 25)
	private String screenName = "ASSET MANAGEMENT";

	@OneToMany(mappedBy = "assetMaster", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<AssetImageVO> assetImages = new ArrayList<>();

	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
