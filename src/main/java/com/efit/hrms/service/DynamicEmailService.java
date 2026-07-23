package com.efit.hrms.service;

import org.springframework.stereotype.Service;

@Service
public interface DynamicEmailService {

	void sendHtmlEmail(String fromEail, String toEmail, String subject, String htmlContent);

}
