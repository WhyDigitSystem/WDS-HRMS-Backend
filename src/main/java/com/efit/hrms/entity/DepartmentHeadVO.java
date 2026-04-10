package com.efit.hrms.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.hrms.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "departmenthead")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentHeadVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departmentheadgen")
	@SequenceGenerator(name = "departmentheadgen", sequenceName = "departmentheadseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "departmentheadid")
	private Long id;
	
	@Column(name = "department")
	private String department;
	@Column(name = "departmentcode")
	private String departmentCode;
	
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "branch")
	private String branch;
//	@Column(name = "finyear")
//	private String finYear;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "active")
	private boolean active=true;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	
	@Column(name = "screencode", length = 5)
	private String screenCode = "DH";

	@Column(name = "screenname", length = 25)
	private String screenName = "DEPARTMENT HAED";
	
	
	
	@OneToMany(mappedBy = "departmentHeadVO", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<ReportingHeadVO> reportingHeadVO = new ArrayList<>();

	@OneToMany(mappedBy = "departmentHeadVO", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<ClearanceDetailsVO> clearanceDetailsVO = new ArrayList<>();
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
	
	
	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
}

