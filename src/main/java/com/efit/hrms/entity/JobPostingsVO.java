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
@Table(name = "jobpostings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPostingsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jobpostingsgen")
	@SequenceGenerator(name = "jobpostingsgen", sequenceName = "jobpostingsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "jobpostingsid")
	private Long id;
	@Column(name = "jobtitle")
	private String jobTitle;
	@Column(name = "department")
	private String department;
	@Column(name = "location")
	private String location;
	
	@Column(name = "orgid")
	private Long orgId;
	
	@Column(name = "branch")
	private String branch;
	@Column(name = "branchcode")
	private String branchCode;
	
	@Column(name = "active")
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel=false;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "screencode", length = 5)
	private String screenCode = "JP";

	@Column(name = "screenname", length = 25)
	private String screenName = "JOB POSTINGS";
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
	
	
	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
