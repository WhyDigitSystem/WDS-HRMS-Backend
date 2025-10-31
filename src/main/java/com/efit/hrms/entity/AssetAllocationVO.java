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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "assetallocation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetAllocationVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assetallocationgen")
	@SequenceGenerator(name = "assetallocationgen", sequenceName = "assetallocationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "assetallocationid")
	private Long id;
	@Column(name = "assetname")
	private String assetName;
	@Column(name = "assetcode")
	private String assetCode;
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

	@Column(name = "branch")
	private String branch;
	@Column(name = "branchcode")
	private String branchCode;	
	@Column(name = "finyear")
	private String finyear;
	
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "cancel")
	private boolean cancel;
	
	@Column(name = "screencode", length = 5)
	private String screenCode = "AL";

	@Column(name = "screenname", length = 25)
	private String screenName = "ASSET ALLOCATION";


	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
