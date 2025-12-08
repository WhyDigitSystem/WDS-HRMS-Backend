package com.efit.hrms.entity;

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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "listofvalues")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListOfValuesVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "listofvaluesgen")
	@SequenceGenerator(name = "listofvaluesgen", sequenceName = "listofvaluesseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "listofvaluesid")
	private Long id;
	@Column(name = "listdescription")
	private String listDescription;	
	

	@Column(name = "screencode", length = 5)
	private String screenCode = "LOV";

	@Column(name = "screenname", length = 25)
	private String screenName = "LIST OF VALUES";

//	@Column(name = "branch", length = 25)
//	private String branch;
//
//	@Column(name = "branchcode", length = 20)
//	private String branchCode;

	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
//	@Column(name = "active")
//	private boolean active;
	
	@OneToMany(mappedBy = "listOfValuesVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<ListOfValuesDetailsVO> listOfValuesDetailsVO;

//	@JsonGetter("active")
//	public String getActive() {
//		return active ? "Active" : "In-Active";
//	}
//
//	@JsonGetter("cancel")
//	public String getCancel() {
//		return cancel ? "T" : "F";
//	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
