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



}
