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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "performancegoalsdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class PerformanceGoalsDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "performancegoalsdetailsgen")
	@SequenceGenerator(name = "performancegoalsdetailsgen", sequenceName = "performancegoalsdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "performancegoalsdetailsid")
	private Long id;

	@Column(name = "perspective", length = 50)
	private String perspective;

	@Column(name = "objectivedesc")
	private String objectivedesc;

	@Column(name = "perassigned")
	private String perassigned;

	@Column(name = "measurement")
	private String measurement;

	@Column(name = "qtrtarget")
	private String qtrtarget;

	@Column(name = "performance")
	private String performance;

	@Column(name = "comments")
	private String comments;

	@Column(name = "performanceself")
	private String performanceself;

	@Column(name = "selfrating")
	private Long selfrating;

	@Column(name = "appraiserrating")
	private Long appraiserrating;

	@Column(name = "apprjustification")
	private String apprjustification;

	@ManyToOne
	@JoinColumn(name = "performancegoalsid")
	@JsonBackReference
	private PerformanceGoalsVO performanceGoalsVO;

}
