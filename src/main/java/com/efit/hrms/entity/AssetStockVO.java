package com.efit.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.hrms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "assetstock")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetStockVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assetstockgen")
	@SequenceGenerator(name = "assetstockgen", sequenceName = "assetstockseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "assetstockid")
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
	@Column(name = "sourcescreen")
	private String sourceScreen;
	@Column(name = "sourcescreencode")
	private String sourceScreenCode;
	@Column(name = "serialnumber")
	private String serialNumber;
	@Column(name = "location")
	private String location;
	@Column(name = "locationcode")
	private String locationCode;
	@Column(name = "assetstatus")
	private int assetStatus;

	@Column(name = "branch")
	private String branch;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "finyear")
	private String finyear;
	@Column(name = "sourceid")
	private Long sourceId;

	@Column(name = "active")
	private boolean active = true;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "screencode", length = 5)
	private String screenCode = "AS";

	@Column(name = "screenname", length = 25)
	private String screenName = "ASSET STOCK";

	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
