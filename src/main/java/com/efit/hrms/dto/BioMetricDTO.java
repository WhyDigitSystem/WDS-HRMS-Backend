package com.efit.hrms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BioMetricDTO {
	private String branch;
	private String branchCode;
	private String empcode;
	private String empName;
	private long orgId;
	private boolean status;
	private String notify;
	private String notifyCode;
	private String notifyEmail;
	private String email;
	private Double latitude;
	private Double longitude;
	private String workFromHome;
	private String locationAddress;
	private LocalDate logDate;
	private String logTime;
	private Long deviceLogId;
}
