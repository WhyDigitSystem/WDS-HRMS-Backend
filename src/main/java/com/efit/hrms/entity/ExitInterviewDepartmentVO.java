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
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exitinterviewdepartment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExitInterviewDepartmentVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exitinterviewdepartmentgen")
	@SequenceGenerator(name = "exitinterviewdepartmentgen", sequenceName = "exitinterviewdepartmentseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "exitinterviewdepartmentid")
	private Long id;

	@Column(name = "designation")
	private String designation;
	@Column(name = "designationcode")
	private String designationCode;
	@Column(name = "branch")
	private String branch;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "updatedby")
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "active")
	private boolean active;	
	@Column(name = "cancel")
	private boolean cancel=false;

	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	
	@OneToMany(mappedBy = "exitInterviewDepartmentVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<QuestionVO> questionVO;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
