package com.efit.hrms.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.hrms.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "candidates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidatesVO {

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidatesgen")
	@SequenceGenerator(name = "candidatesgen", sequenceName = "candidatesseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "candidatesid")
	private Long id;

	@Column(name = "candidatesname")
	private String candidatesName;

	@Column(name = "email")
	private String email;
	@Column(name = "positionapplied")
	private String positionApplied;

	@Column(name = "resumescore")
	private int resumeScore;
	@Column(name = "interviewdate")
	private LocalDate interviewDate;
	@Column(name = "interviewtime")
	private String interviewTime;
	@Column(name = "interviewer")
	private String interviewer;
	@Column(name = "rating")
	private int rating;
	@Column(name = "feedback")
	private String feedBack;

	@Column(name = "remarks")
	private String remarks;

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
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "branch")
	private String branch;


	@Column(name = "screencode", length = 5)
	private String screenCode = "CD";

	@Column(name = "screenname", length = 25)
	private String screenName = "CANDIDATES";

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
