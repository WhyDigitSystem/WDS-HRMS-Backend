package com.efit.hrms.entity;


import java.time.LocalDateTime;
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
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "performancegoals")
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceGoalsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "performancegoalsgen")
	@SequenceGenerator(name = "performancegoalsgen", sequenceName = "performancegoalsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "performancegoalsid")
	private Long id;

	@Column(name = "createdby", length = 50)
	private String createdBy;

	@Column(name = "modifiedby", length = 50)
	private String modifiedBy;

	@Column(name = "empcode", length = 10)
	private String empCode;

	@Column(name = "empname", length = 100)
	private String empName;

	@Column(name = "approve1", length = 100)
	private String approve1;

	@Column(name = "approve1name", length = 100)
	private String approve1name;

	@Column(name = "approve1on", length = 100)
	private LocalDateTime approve1on;

	@Column(name = "appraisalyear")
	private String appraisalYear;
	
	@Column(name = "pmonth")
	private String pmonth;
	
	@Column(name = "reportingto")
	private String reportingto;
	
	@Column(name = "reportingname")
	private String reportingname;
	
	@Column(name = "department")
	private String department;
//	
	@Column(name = "designation")
	private String designation;
	
	@Column(name = "orgid")
	private Long orgId;
	
	@Column(name = "finyear", length = 5)
	private String finYear;
//
//	@Column(name = "screencode", length = 5)
//	private String screenCode = "QA";
//
//	@Column(name = "screenname", length = 25)
//	private String screenName = "QUOTATION";
//
	@Column(name = "branch", length = 25)
	private String branch;

	@Column(name = "branchcode", length = 20)
	private String branchCode;
//	
//	@Column(name = "cancel")
//	private boolean cancel = false;
//	@Column(name = "cancelremarks")
//	private String cancelRemarks;
//
//	@Column(name = "active")
//	private boolean active = true;
	

	@OneToMany(mappedBy = "performanceGoalsVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PerformanceGoalsDetailsVO> performanceGoalsDtlVO;

	@Embedded
	private CreatedUpdatedDate createdUpdatedDate = new CreatedUpdatedDate();

}

