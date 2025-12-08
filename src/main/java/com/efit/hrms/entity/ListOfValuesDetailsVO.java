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
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "listofvaluesdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListOfValuesDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "listofvaluesdetailsgen")
	@SequenceGenerator(name = "listofvaluesdetailsgen", sequenceName = "listofvaluesdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "listofvaluesdetailsid")
	private Long id;

	@Column(name = "listvalues")
	private String listValues;

	@Column(name = "active")
	private boolean active = true;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "listofvaluesid")
	ListOfValuesVO listOfValuesVO;
}
