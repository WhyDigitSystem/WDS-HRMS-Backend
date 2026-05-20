package com.efit.hrms.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.hrms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "form")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "formgen")
	@SequenceGenerator(name = "formgen", sequenceName = "formseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "formid")
	private Long id;

	@Column(name = "uploadon")
	private LocalDateTime uploadOn;

	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedy")
	private String updatedBy;
	@Column(name = "employeename")
	private String employeeName;

	@Column(name = "employeecode")
	private String employeeCode;

	@Column(name = "orgid")
	private Long orgId;

	@Column(name = "finyear")
	private Long finYear;

	@Column(name = "branch")
	private String branch;

	@Column(name = "branchcode")
	private String branchCode;

	@Column(name = "filename")
	private String fileName;

	@Column(name = "filepath")
	private String filePath;

	@Column(name = "filesize")
	private Long fileSize;

	@Column(name = "contenttype")
	private String contentType;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
