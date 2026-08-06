package com.efit.hrms.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.hrms.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clearancedetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClearanceDetailsVO {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clearancedetailsgen")
	@SequenceGenerator(name = "clearancedetailsgen", sequenceName = "clearancedetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "clearancedetailsid")
	private Long id;
	
	@Column(name = "clearancename")
	private String clearanceName;
	
	@Column(name = "screencode", length = 5)
	private String screenCode = "CD";

	@Column(name = "screenname", length = 25)
	private String screenName = "CLEARANCE DETAILS";
	


    @ManyToOne
    @JoinColumn(name = "departmentheadid", nullable = false)
    @JsonBackReference
    private DepartmentHeadVO departmentHeadVO;
	
	
}

