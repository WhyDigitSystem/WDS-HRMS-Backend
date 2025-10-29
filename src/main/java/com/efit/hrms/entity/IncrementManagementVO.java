package com.efit.hrms.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "incrementmanagement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncrementManagementVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incrementmanagementgen")
	@SequenceGenerator(name = "incrementmanagementgen", sequenceName = "incrementmanagementseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "incrementmanagementid")
	private Long id;
	@Column(name = "employeename")
	private String employeeName;
	@Column(name = "employeecode")
	private String employeeCode;
	@Column(name = "department")
	private String department;
	@Column(name = "designation")
	private String designation;
	@Column(name = "location")
	private String location;
	@Column(name = "joiningdate")
	private LocalDate joiningDate;
	@Column(name = "reportingto")
	private String reportingTo;
	@Column(name = "incrementcycle")
	private String incrementCycle;
	@Column(name = "effectivefrom")
	private LocalDate effectiveFrom;
	@Column(name = "adjustmenttype")
	private String adjustmentType;
	@Column(name = "adjustmentvalue")
	private String adjustmentValue;
	@Column(name = "newdesignation")
	private String newDesignation;
	@Column(name = "newgrade")
	private String newGrade;
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "nextapproval")
	private String nextApproval;
	@Column(name = "totalctc")
	private BigDecimal totalctc;
	@Column(name = "totalctcpercentage")
	private String totalCtcPercentage;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "reportingpersoncode")
	private String reportingPersonCode;
	@Column(name = "reportingpersonemail")
	private String reportingPersonEmail;
	@Column(name = "branch")
	private String branch;
	@Column(name = "branchcode")
	private String branchCode;
	
	@Column(name = "approvestatus")
	private String approveStatus;
	@Column(name = "approveby")
	private String approveBy;
	@Column(name = "approveon")
	private String approveOn;
	
	@Column(name = "active")
	private boolean active=true;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "screencode", length = 5)
	private String screenCode = "IM";

	@Column(name = "screenname", length = 25)
	private String screenName = "INCREMENT MANAGEMENT";

	
	
	
	  @OneToMany(mappedBy = "incrementManagementVO",cascade = CascadeType.ALL)
	   	@JsonManagedReference
	   	private List<IncrementManagementDetailsVO> incrementManagementDetailsVO;
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
	
	
	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
