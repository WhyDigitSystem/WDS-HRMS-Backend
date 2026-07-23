package com.efit.hrms.service;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.efit.hrms.entity.EmailConfigurationVO;
import com.efit.hrms.repo.EmailConfigurationRepo;

@Service
public class DynamicEmailServiceImpl implements DynamicEmailService {

	@Autowired
	private EmailConfigurationRepo emailConfigurationRepo;

	@Override
	public void sendHtmlEmail(String fromMail, String toEmail, String subject, String htmlContent) {

		try {

			EmailConfigurationVO config = emailConfigurationRepo.findByType();

			if (config == null) {
				throw new RuntimeException("Email Configuration Not Found.");
			}

			JavaMailSenderImpl sender = new JavaMailSenderImpl();
			sender.setHost(config.getSmtpHost());
			sender.setPort(config.getSmtpPort());
			sender.setUsername(config.getAdminEmail());
			sender.setPassword(config.getPassword());

			Properties props = sender.getJavaMailProperties();
			props.put("mail.smtp.auth", "true");

			if (config.getSmtpPort() == 465) {
				props.put("mail.smtp.ssl.enable", "true");
				props.put("mail.smtp.starttls.enable", "false");
			} else {
				props.put("mail.smtp.ssl.enable", "false");
				props.put("mail.smtp.starttls.enable", "true");
			}

			props.put("mail.smtp.connectiontimeout", "10000");
			props.put("mail.smtp.timeout", "10000");
			props.put("mail.smtp.writetimeout", "10000");

			MimeMessage message = sender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			message.addHeader("Auto-Submitted", "auto-generated");
			message.addHeader("Precedence", "bulk");
			message.addHeader("X-Auto-Response-Suppress", "All");

			helper.setFrom(config.getNoReplayMail(), "WHY DIGIT SYSTEMS (No Reply)");

			helper.setTo(toEmail);

			helper.setSubject(subject);

			helper.setText(htmlContent, true);

			sender.send(message);

			System.out.println("=======================================");
			System.out.println("Mail Sent Successfully");
			System.out.println("SMTP Host : " + config.getSmtpHost());
			System.out.println("SMTP Port : " + config.getSmtpPort());
			System.out.println("From      : " + config.getNoReplayMail());
			System.out.println("To        : " + toEmail);
			System.out.println("=======================================");

		} catch (MessagingException e) {

			System.err.println("Messaging Error : " + e.getMessage());
			e.printStackTrace();

		} catch (Exception e) {

			System.err.println("Mail Sending Failed : " + e.getMessage());
			e.printStackTrace();

		}
	}
}