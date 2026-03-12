package com.efit.hrms.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "initiateseparation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InitiateSeparationVO {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "initiateseparationgen")
	@SequenceGenerator(name = "initiateseparationgen", sequenceName = "initiateseparationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "initiateseparationid")
	private Long id;
	
	@Column(name = "employeename")
	private String employeeName;
	@Column(name = "employeecode")
	private String employeeCode;
	@Column(name = "department")
	private String department;
	@Column(name = "position")
	private String position;
//	@Column(name = "reportingmanger")
//	private String reportingManager;
	@Column(name = "joiningdate")
	private LocalDate joiningDate;
	@Column(name = "separationtype")
	private String separationType;
	@Column(name = "resignation")
	private LocalDate resignation;
	@Column(name = "lastworkingdate")
	private LocalDate lastWorkingDate;
	
	@Column(name = "noticedate")
	private int noticeDate;
	@Column(name = "reportingmanager")
	private String reportingManager;
	@Column(name = "reasoncategory")
	private String reasonCategory;
	@Column(name = "rehireeligible")
	private String rehireEligible;
	@Column(name = "detailedreason")
	private String detailedReason;
	@Column(name = "interviewdate")
	private LocalDate interviewDate;
	@Column(name = "Experiencerating")
	private int ExperienceRating;
	@Column(name = "exitinterviewfeedback")
	private String exitInterviewFeedback;
	@Column(name = "status")
	private String status;
	
	@Column(name = "reportingperson")
	private String reportingPerson;
	@Column(name = "reportingpersoncode")
	private String reportingPersonCode;
	@Column(name = "reportingpersonemail")
	private String reportingPersonEmail;
//	
//	@Column(name = "approvestatus")
//	private String approveStatus;
//	@Column(name = "approveby")
//	private String approveBy;
//	@Column(name = "approveon")
//	private String approveOn;
	
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
	private String screenCode = "IS";

	@Column(name = "screenname", length = 25)
	private String screenName = "INITIATE SEPARATION";
	
	
	
	@OneToMany(mappedBy = "initiateSeparationVO", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<ClearanceManagementVO> clearanceManagementVO = new ArrayList<>();
	
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
	
	
	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
}
