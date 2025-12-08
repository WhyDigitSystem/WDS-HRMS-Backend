package com.efit.hrms.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.hrms.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clearancemanagement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClearanceManagementVO {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clearancemanagementgen")
	@SequenceGenerator(name = "clearancemanagementgen", sequenceName = "clearancemanagementseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "clearancemanagementid")
	private Long id;
	
	@Column(name = "clearanceitem")
	private String clearanceItem;
	
	
	@Column(name = "screencode", length = 5)
	private String screenCode = "CM";

	@Column(name = "screenname", length = 25)
	private String screenName = "CLEARANCE MANAGEMENT";
	


    @ManyToOne
    @JoinColumn(name = "initiateseparationid", nullable = false)
    @JsonBackReference
    private InitiateSeparationVO initiateSeparationVO;
	
	

}