package com.efit.hrms.entity;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "createoffer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOfferVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "createoffergen")
	@SequenceGenerator(name = "createoffergen", sequenceName = "createofferseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "createofferid")
	private Long id;

	@Column(name = "candidatename")
	private String candidateName;
	@Column(name = "candidateid")
	private Long candidateId ;
	@Column(name = "position")
	private String position;
	@Column(name = "joiningdate")
	private String joiningDate;
	
	@Column(name = "reportingperson")
	private String reportingPerson;
	@Column(name = "reportingcode")
	private String reportingcode;
	@Column(name = "reportingemail")
	private String reportingEmail;
	@Column(name = "worklocation")
	private String workLocation;
	
	@Column(name = "probationperiod")
	private Long  probationPeriod;
	@Column(name = "noticeperiod")
	private String noticePeriod;
	@Column(name = "workhours")
	private String workhours;
	@Column(name = "templatetype")
	private String templateType;
	@Column(name = "additionalbenefits")
	private String additionalBenefits;
	@Column(name = "specialtermscondition")
	private String specialTermsCondition;

	
	@Column(name = "approvestatus")
	private String approveStatus;
	@Column(name = "approveby")
	private String approveBy;
	@Column(name = "approveon")
	private String approveOn;
	
	@Column(name = "orgid")
	private long orgId;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "branch")
	private String branch;
	@Column(name = "department")
	private String department;
	@Column(name = "finyear")
	private String finYear;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "screencode", length = 30)
	private String screenCode = "CO";
	@Column(name = "screenname", length = 30)
	private String screenName = "CREATE OFFER";
	@Column(name = "active")
	private boolean active = true;

	
	@OneToMany(mappedBy = "createOfferVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<CompensationDetailsVO> compensationDetailsVO;
	

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";

	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
