package com.efit.hrms.entity;

import java.math.BigDecimal;
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
@Table(name = "expenseclaims")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseClaimsVO {

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expenseclaimsgen")
	@SequenceGenerator(name = "expenseclaimsgen", sequenceName = "expenseclaimsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "expenseclaimsid")
	private Long id;
	
	@Column(name = "employeename")
	private String employeename;
	@Column(name = "expensetitle")
	private String expenseTitle;
	@Column(name = "category")
	private String category;
	@Column(name = "amount")
	private BigDecimal amount;
	@Column(name = "currency")
	private String currency;
	@Column(name = "expenseDate")
	private LocalDate expenseDate;
	@Column(name = "receiptattached")
	private String receiptAttached;

	@Column(name = "description")
	private String description;
	
	@Column(name = "approvestatus")
	private String approveStatus;
	@Column(name = "approveby")
	private String approveBy;
	@Column(name = "approveon")
	private String approveOn;
	
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "branch")
	private String branch;
//	@Column(name = "finyear")
//	private String finYear;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	
	@Column(name = "screencode", length = 5)
	private String screenCode = "EC";

	@Column(name = "screenname", length = 25)
	private String screenName = "EXPENSE CLAIMS";
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
	
	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

}
