package com.efit.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reportinghead")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportingHeadVO {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reportingheadgen")
	@SequenceGenerator(name = "reportingheadgen", sequenceName = "reportingheadseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "reportingheadid")
	private Long id;
	
	@Column(name = "employee")
	private String employee;
	@Column(name = "employeecode")
	private String employeeCode;
	@Column(name = "employeeemail")
	private String employeeEmail;
	
	@Column(name = "screencode", length = 5)
	private String screenCode = "RH";

	@Column(name = "screenname", length = 25)
	private String screenName = "REPORTING HEAD";
	


    @ManyToOne
    @JoinColumn(name = "departmentheadid", nullable = false)
    @JsonBackReference
    private DepartmentHeadVO departmentHeadVO;
	
	
}
