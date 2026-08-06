package com.efit.hrms.entity;

import java.math.BigDecimal;
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
@Table(name = "investmentdeclaration")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestmentDeclarationVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "investmentdeclarationgen")
	@SequenceGenerator(name = "investmentdeclarationgen", sequenceName = "investmentdeclarationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "investmentdeclarationid")
	private Long id;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "branch", length = 30)
	private String branch;
	@Column(name = "branchcode", length = 10)
	private String branchCode;
	@Column(name = "finyear", length = 10)
	private String finYear;
	@Column(name = "createdby", length = 30)
	private String createdBy;
	@Column(name = "modifiedby", length = 30)
	private String modifiedBy;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemarks;
	
	@Column(name = "totalamount", precision = 10, scale = 2)
	private BigDecimal totalAmount;
	
	@Column(name = "employeename")
	private String employeeName;
	@Column(name = "employeecode")
	private String employeeCode;
	
	@Column(name = "screencode", length = 30)
	private String screenCode = "ID";
	@Column(name = "screenname", length = 30)
	private String screenName = "INVESTMENT DECLARATION";

	
	
	@OneToMany(mappedBy = "investmentDeclarationVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<InvestmentDeclarationDetailsVO> investmentDeclarationDetailsVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}