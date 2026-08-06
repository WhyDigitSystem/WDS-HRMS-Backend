package com.efit.hrms.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "investmentdeclarationdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestmentDeclarationDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "investmentdeclarationdetailsgen")
	@SequenceGenerator(name = "investmentdeclarationdetailsgen", sequenceName = "investmentdeclarationdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "investmentdeclarationdetailsid")
	private Long id;

	@Column(name = "section")
	private String section;
	@Column(name = "investmenttype")
	private String investmentType;
	
	@Column(name = "filesize")
	private Long fileSize;

	@Column(name = "contenttype")
	private String contentType;

	@Column(name = "uploadon")
	private LocalDateTime uploadOn;
	
	@Column(name = "filename")
	private String fileName;

	@Column(name = "filepath")
	private String filePath;

	@Column(name = "declared", precision = 10, scale = 2)
	private BigDecimal declared;

	@Column(name = "limitamount", precision = 10, scale = 2)
	private BigDecimal limitAmount;
	
	@Column(name = "proof")
	private String proof;

	@Column(name = "status")
	private String status = "PENDING";

	@Lob
	@Column(name = "uploadfile", columnDefinition = "LONGBLOB")
	private byte[] uploadFile;

	@Column(name = "approvestatus", length = 20)
	private String approveStatus;
	@Column(name = "approveby", length = 20)
	private String approveBy;

	@DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss a")
	@Column(name = "approveon")
	private String approveOn;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "investmentdeclarationid")
	InvestmentDeclarationVO investmentDeclarationVO;

}
