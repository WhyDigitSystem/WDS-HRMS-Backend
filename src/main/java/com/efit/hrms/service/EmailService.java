package com.efit.hrms.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

	public void sendSimpleEmail(String toEmail, String subject, String body);

	

	/**
	 * Send an HTML formatted email
	 */
	void sendHtmlEmail(String fromEail, String toEmail, String subject, String htmlContent);
}
