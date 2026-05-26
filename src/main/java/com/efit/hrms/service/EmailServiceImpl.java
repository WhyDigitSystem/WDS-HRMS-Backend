package com.efit.hrms.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.efit.hrms.entity.EmployeeVO;
import com.efit.hrms.repo.EmployeeRepo;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	EmployeeRepo employeeRepo;

	/**
	 * Send an HTML formatted email
	 */
	@Override
	public void sendHtmlEmail(String fromMail, String toEmail, String subject, String htmlContent) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			mimeMessage.addHeader("Auto-Submitted", "auto-generated");
			mimeMessage.addHeader("Precedence", "bulk");
			mimeMessage.addHeader("X-Auto-Response-Suppress", "All");

			helper.setFrom(fromMail, "WHY DIGIT SYSTEMS (No Reply)");
			helper.setTo(toEmail);
			helper.setSubject(subject);
			helper.setText(htmlContent, true);

			mailSender.send(mimeMessage);
			System.out.println("✅ HTML Mail sent successfully to " + toEmail);

		} catch (MessagingException e) {
			System.err.println("❌ Messaging error while sending HTML mail: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("❌ Failed to send HTML mail to " + toEmail + ": " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void sendSimpleEmail(String toEmail, String subject, String body) {
		// TODO Auto-generated method stub

	}

	// forgot password

	// 🔹 Load HTML template
	public String loadHtmlTemplate() {
		try {
			InputStream is = new ClassPathResource("templates/forgot_password.html").getInputStream();
			return new String(is.readAllBytes(), StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new RuntimeException("Template load failed", e);
		}
	}

	// 🔹 Send OTP Email
	public void sendOtpEmail(String to, String name, String otp) {

		String html = loadHtmlTemplate();

		html = html.replace("{{name}}", name);
		html = html.replace("{{otp}}", otp);

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom("support@whydigit.in");
			helper.setTo(to);
			helper.setSubject("Password Reset OTP");
			helper.setText(html, true);

			mailSender.send(message);

		} catch (Exception e) {
			throw new RuntimeException("Email sending failed", e);
		}
	}

	// leaverequestmail

	 public void sendLeaveRequestMail(
	            String toEmail,
	            Long orgId,
	            Long leaveId,
	            String employeeCode,
	            String employeeName,
	            LocalDate fromDate,
	            LocalDate toDate,
	            String reason,
	            BigDecimal totalDays,
	            String notifyCode,
	            boolean showButtons) {
		 
	        try {

	            // THYMELEAF VARIABLES
	            Context context = new Context();

	            context.setVariable("employeeName", employeeName);
	            context.setVariable("reason", reason);
//	            context.setVariable("fromDate", fromDate);
	         // DATE FORMATTER
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	            context.setVariable("fromDate", fromDate.format(formatter));
	            context.setVariable("toDate", toDate.format(formatter));
//	            context.setVariable("toDate", toDate);
	            context.setVariable("totalDays", totalDays.toPlainString());	
	            context.setVariable(
	                    "showButtons",
	                    showButtons);
	            // APPROVE URL
	            context.setVariable(
	                    "approveUrl",

	                    "http://localhost:8047/api/leaveprocess/mailLeaveAction"
	                    + "?orgId=" + orgId
	                    + "&id=" + leaveId
	                    + "&employeeCode=" + employeeCode
	                    + "&action=APPROVED"
	                    + "&actionBy=" + notifyCode
	                    + "&notifyCode="
	                    + "&notify="
	                    + "&screenName=MAIL"
	                    + "&email=" + toEmail
	            );

	            // REJECT URL
	            context.setVariable(
	                    "rejectUrl",

	                    "http://localhost:8047/api/leaveprocess/mailLeaveAction"
	                    + "?orgId=" + orgId
	                    + "&id=" + leaveId
	                    + "&employeeCode=" + employeeCode
	                    + "&action=REJECTED"
	                    + "&actionBy=" + notifyCode
	                    + "&notifyCode="
	                    + "&notify="
	                    + "&screenName=MAIL"
	                    + "&email=" + toEmail
	            );

	            context.setVariable(

	            	    "rejectPageUrl",

	            	    "http://localhost:8047/api/leaveprocess/reject-page"

	            	    + "?orgId=" + orgId
	            	    + "&id=" + leaveId
	            	    + "&employeeCode=" + employeeCode
	            	    + "&action=REJECTED"
	            	    + "&actionBy=" + notifyCode
	            	    + "&notifyCode="
	            	    + "&notify="
	            	    + "&screenName=MAIL"
	            	    + "&email=" + toEmail
	            	);
	            
	            
	            // LOAD HTML TEMPLATE
	            String htmlContent =
	                    templateEngine.process(
	                            "leave-request",
	                            context);

//	            String htmlContent =
//	                    "<h1>MAIL WORKING</h1>";
	            
	            // CREATE MAIL
	            MimeMessage mimeMessage =
	                    mailSender.createMimeMessage();

	            MimeMessageHelper helper =
	                    new MimeMessageHelper(mimeMessage, true);

	            helper.setTo(toEmail);

	            helper.setSubject(
	                    "Leave Request - " + reason);

	            // TRUE = HTML MAIL
	            helper.setText(htmlContent, true);

	            // SEND MAIL
	            mailSender.send(mimeMessage);

	        } catch (Exception e) {
	        	
	            e.printStackTrace();
	        }
	    }
	 
	 //response return leave mail 
	 public void sendLeaveStatusMail(

		        String toEmail,
		        String employeeName,
		        String action,
		        String reason,
		        LocalDate fromDate,
		        LocalDate toDate,
		        String approvedBy) {

		 EmployeeVO employee = employeeRepo.findByEmployeeCode(approvedBy);
		 String approved = employee.getEmployeeName();
		    try {

		        Context context = new Context();

		        context.setVariable(
		                "employeeName",
		                employeeName);

		        context.setVariable(
		                "action",
		                action);

		        context.setVariable(
		                "reason",
		                reason);

		        DateTimeFormatter formatter =
		                DateTimeFormatter.ofPattern("dd-MM-yyyy");

		        context.setVariable(
		                "fromDate",
		                fromDate.format(formatter));

		        context.setVariable(
		                "toDate",
		                toDate.format(formatter));
		        
		        context.setVariable("approvedBy",   approved);
		        // TEMPLATE
		        String html =
		                templateEngine.process(
		                        "leave-reply",
		                        context);

		        MimeMessage mimeMessage =
		                mailSender.createMimeMessage();

		        MimeMessageHelper helper =
		                new MimeMessageHelper(
		                        mimeMessage,
		                        true);

		        helper.setTo(toEmail);

		        helper.setSubject(
		                "Leave Request " + action);

		        helper.setText(
		                html,
		                true);

		        mailSender.send(mimeMessage);

		    }

		    catch (Exception e) {
		    	System.out.println("MAIL ERROR");
		        e.printStackTrace();
		    }
		}
	}