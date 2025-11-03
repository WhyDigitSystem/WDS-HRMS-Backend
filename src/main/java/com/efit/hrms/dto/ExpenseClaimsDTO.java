package com.efit.hrms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseClaimsDTO {

	private Long id;
	private String employeename;
	private String expenseTitle;
	private String category;
	private BigDecimal amount;
	private String currency;
	private LocalDate expenseDate;
	private String receiptAttached;
	private String description;
	private String approveStatus;
	private String approveBy;
	private String approveOn;
	private String branchCode;
	private String branch;
	private String createdBy;
	private Long orgId;


}
