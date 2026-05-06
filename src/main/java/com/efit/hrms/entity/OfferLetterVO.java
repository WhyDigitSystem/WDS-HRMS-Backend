package com.efit.hrms.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.hrms.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "offerletter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferLetterVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offerlettergen")
	@SequenceGenerator(name = "offerlettergen", sequenceName = "offerletterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "offerletterid")
	private Long id;

	@Column(name = "candidatesname")
	private String candidatesName;

	@Column(name = "email")
	private String email;
	@Column(name = "position")
	private String position;

	@Column(name = "department")
	private String department;
	@Column(name = "location")
	private String location;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "active")
	private boolean active ;
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
	private String screenCode = "OF";

	@Column(name = "screenname", length = 25)
	private String screenName = "OFFER LETER";

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}

