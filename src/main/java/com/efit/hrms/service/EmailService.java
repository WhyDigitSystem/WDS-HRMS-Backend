package com.efit.hrms.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

	public void sendSimpleEmail(String toEmail, String subject, String body);

	

	/**
	 * Send an HTML formatted email
	 */
	void sendHtmlEmail(String fromEail, String toEmail, String subject, String htmlContent);



	public void sendOtpEmail(String email, String employeeName, String otp);



	public void sendLeaveRequestMail(String notifyEmail, Long orgId, Long id, String employeeCode, String employeeName,
			LocalDate fromDate, LocalDate toDate, String notes, BigDecimal totalDays, String notifyCode, boolean b);



	public void sendLeaveStatusMail(String email, String employeeName, String action, String reason, LocalDate fromDate,
			LocalDate toDate, String approvedBy);



	public void sendCompOffRequestMail(String notify2Email, Long orgId, Long id, String employeeCode,
			String employeeName, String leaveType, LocalDate compOffDate, BigDecimal totalDays, String notes,
			String notify2Code, boolean b);



	public void sendCompOffStatusMail(String employeeCode,    
	        String employeeName,
	        String action,
	        String reason,
	        LocalDate compOffDate,
	        String leaveType,
	        String approvedBy);


	void sendCheckInOutStatusMail(String employeeCode, String empName, String action, String reason,
			LocalDate checkInDate, String entryIn, String entryOut, String approvedBy);

	void sendCheckInOutRequestMail(String toEmail, Long orgId, String employeeCode, String empName,
			LocalDate checkInDate, String entryIn, String entryOut, String requestReason, String notifyCode,
			boolean showButtons);



	public void sendWfhStatusMail(String employeeCode, String employeeName, String action, String reason,
			LocalDate wfhDate, String reason2, String actionBy);



	public void sendWfhRequestMail(String reportingManagerEmail, Long orgId, Long id, String employeeCode,
			String employeeName, LocalDate wfhDate, String reason, String workAccomplished, String departmentHead,
			String reportingManagerCode, boolean b);



}
