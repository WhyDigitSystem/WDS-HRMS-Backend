package com.efit.hrms.dto;

import java.time.LocalDate;

import javax.persistence.Column;

import com.efit.hrms.entity.TravelRequestsVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelRequestsDTO {

	private Long id;
	private String employeeName;
	private String employeeCode;
	private String department;
	private String travelTitle;
	private String from;
	private String to;
	private LocalDate departureDate;
	private LocalDate returnDate;
	private String transportMode;
	private String accommodation;
	private String estimatedCost;
	private String businessPurpose;
	private String branchCode;
	private String branch;
	private String createdBy;
	private Long orgId;
}
