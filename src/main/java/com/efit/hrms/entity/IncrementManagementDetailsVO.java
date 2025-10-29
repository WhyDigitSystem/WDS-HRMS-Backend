package com.efit.hrms.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "incrementmanagementdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncrementManagementDetailsVO {

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incrementmanagementdetailsgen")
	@SequenceGenerator(name = "incrementmanagementdetailsgen", sequenceName = "incrementmanagementdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "incrementmanagementdetailsid")
	private Long id;
	@Column(name = "heading", length = 100)
	private String heading;
	@Column(name = "amount", length = 100)
	private BigDecimal amount;
	
	
	@ManyToOne
	@JoinColumn(name = "incrementmanagementid")
	@JsonBackReference
	private IncrementManagementVO incrementManagementVO;
	
}
