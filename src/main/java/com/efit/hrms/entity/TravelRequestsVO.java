package com.efit.hrms.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.hrms.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "travelrequests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelRequestsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "travelrequestsgen")
	@SequenceGenerator(name = "travelrequestsgen", sequenceName = "travelrequestsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "travelrequestsid")
	private Long id;
	
	@Column(name = "employeename")
	private String employeename;

	@Column(name = "employeecode")
	private String employeeCode;
	@Column(name = "traveltitle")
	private String travelTitle;
	@Column(name = "fromlocation")
	private String from;
	@Column(name = "tolocation")
	private String to;
	@Column(name = "departuredate")
	private LocalDate departureDate;
	@Column(name = "returndate")
	private LocalDate returnDate;
	@Column(name = "transportmode")
	private String transportMode;

	@Column(name = "accommodation")
	private String accommodation;
	
	@Column(name = "estimatedcost")
	private String estimatedCost;
	@Column(name = "businesspurpose")
	private String businessPurpose;
	
	@Column(name = "reportingperson")
	private String reportingPerson;
	@Column(name = "reportingpersoncode")
	private String reportingPersonCode;
	@Column(name = "reportingpersonemail")
	private String reportingPersonEmail;
	
	
	@Column(name = "approvestatus")
	private String approveStatus;
	@Column(name = "approveby")
	private String approveBy;
	@Column(name = "approveon")
	private String approveOn;
	
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
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	
	@Column(name = "screencode", length = 5)
	private String screenCode = "TR";

	@Column(name = "screenname", length = 25)
	private String screenName = "TRAVEL REQUEST";
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
	
	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

}
