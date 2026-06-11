package com.efit.hrms.service;


import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${app.base-url}")
	private String baseUrl;

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

	                    baseUrl + "/api/leaveprocess/mailLeaveAction"
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

	                    baseUrl + "/api/leaveprocess/mailLeaveAction"
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

	            	    baseUrl + "/api/leaveprocess/reject-page"

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
	 
	 public void sendCompOffRequestMail(
		        String toEmail,
		        Long orgId,
		        Long compOffId,
		        String employeeCode,
		        String employeeName,
		        String leaveType,
		        LocalDate compOffDate,
		        BigDecimal totalDays,
		        String notes,
		        String notifyCode,
		        boolean showButtons) {

		    try {
		        Context context = new Context();
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		        context.setVariable("employeeName", employeeName);
		        context.setVariable("leaveType",    leaveType);
		        context.setVariable("compOffDate",  compOffDate.format(formatter));
		        context.setVariable("totalDays",    totalDays.toPlainString());
		        context.setVariable("notes",        notes);
		        context.setVariable("showButtons",  showButtons);

		        context.setVariable("approveUrl",
		        		baseUrl + "/api/leaveprocess/mailCompOffAction"
		                + "?orgId=" + orgId
		                + "&id=" + compOffId
		                + "&employeeCode=" + employeeCode
		                + "&action=APPROVED"
		                + "&actionBy=" + notifyCode
		                + "&notifyCode=&notify=&screenName=MAIL&email=" + toEmail);

		        context.setVariable("rejectPageUrl",
		        		baseUrl + "/api/leaveprocess/compoff-reject-page"
		                + "?orgId=" + orgId
		                + "&id=" + compOffId
		                + "&employeeCode=" + employeeCode
		                + "&action=REJECTED"
		                + "&actionBy=" + notifyCode
		                + "&notifyCode=&notify=&screenName=MAIL&email=" + toEmail);

		        String html = templateEngine.process("compoff-request", context);

		        MimeMessage mimeMessage = mailSender.createMimeMessage();
		        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		        helper.setTo(toEmail);
		        helper.setSubject("Compensatory Off Request - " + employeeName);
		        helper.setText(html, true);
		        mailSender.send(mimeMessage);

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
	 
	 public void sendCompOffStatusMail(
		        String employeeCode,    // ← pass employeeCode instead of email
		        String employeeName,
		        String action,
		        String reason,
		        LocalDate compOffDate,
		        String leaveType,
		        String approvedBy) {

		    try {
		        // ✅ repo call inside service — correct place
		        EmployeeVO employee = employeeRepo.findByEmployeeCode(employeeCode);
		        if (employee == null || employee.getEmail() == null) {
//		            log.error("Employee not found or email missing: {}", employeeCode);
		            return;
		        }
		        String toEmail = employee.getEmail();

		        EmployeeVO approver = employeeRepo.findByEmployeeCode(approvedBy);
		        String approvedByName = approver != null ? approver.getEmployeeName() : approvedBy;

		        Context context = new Context();
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		        context.setVariable("employeeName", employeeName);
		        context.setVariable("action",       action);
		        context.setVariable("reason",       reason);
		        context.setVariable("compOffDate",  compOffDate.format(formatter));
		        context.setVariable("leaveType",    leaveType);
		        context.setVariable("approvedBy",   approvedByName);

		        String html = templateEngine.process("compoff-reply", context);

		        MimeMessage mimeMessage = mailSender.createMimeMessage();
		        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		        helper.setTo(toEmail);

		        String subject = "APPROVED".equalsIgnoreCase(action)
		                ? "Your Comp-Off Request Has Been Approved"
		                : "Your Comp-Off Request Has Been Rejected";
		        helper.setSubject(subject);
		        helper.setText(html, true);
		        mailSender.send(mimeMessage);

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
	 
	 @Override
	 public void sendCheckInOutRequestMail(
		        String toEmail,
		        Long orgId,
		        String employeeCode,
		        String empName,
//		        String branch,
		        LocalDate checkInDate,
		        String entryIn,
		        String entryOut,
		        String requestReason,
		        String notifyCode,
		        String department, String designation, String codeAndName, boolean showButtons) {

		    try {
		        Context context = new Context();
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		        context.setVariable("empName",        empName);
//		        context.setVariable("branch",         branch);
		        context.setVariable("checkInDate",    checkInDate.format(formatter));
		        context.setVariable("entryIn",        entryIn != null ? entryIn : "—");
		        context.setVariable("entryOut",       entryOut != null ? entryOut : "—");
		        context.setVariable("requestReason",  requestReason);
		        context.setVariable("showButtons",    showButtons);

		        // ... existing code ...
		        context.setVariable("department",  department);
		        context.setVariable("designation", designation);
		        context.setVariable("codeAndName", codeAndName);
		        
		        context.setVariable("approveUrl",
		        		baseUrl + "/api/basicmaster/mailCheckInOutAction"
		                + "?orgId=" + orgId
		                + "&employeeCode=" + employeeCode
		                + "&action=APPROVED"
		                + "&actionBy=" + notifyCode
		                + "&checkInDate=" + checkInDate
		                + "&notifyCode=&notify=&screenName=MAIL&email=" + toEmail);

		        context.setVariable("rejectPageUrl",
		        		baseUrl + "/api/basicmaster/checkinout-reject-page"
		                + "?orgId=" + orgId
		                + "&employeeCode=" + employeeCode
		                + "&action=REJECTED"
		                + "&actionBy=" + notifyCode
		                + "&checkInDate=" + checkInDate
		                + "&notifyCode=&notify=&screenName=MAIL&email=" + toEmail);

		        String html = templateEngine.process("checkinout-request", context);

		        MimeMessage mimeMessage = mailSender.createMimeMessage();
		        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		        helper.setTo(toEmail);
		        helper.setSubject("Check-In/Out Adjustment Request - " + empName);
		        helper.setText(html, true);
		        mailSender.send(mimeMessage);

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}

		@Override
		public void sendCheckInOutStatusMail(
		        String employeeCode,
		        String empName,
		        String action,
		        String reason,
		        LocalDate checkInDate,
		        String entryIn,
		        String entryOut,
		        String approvedBy) {

		    try {
		        EmployeeVO employee = employeeRepo.findByEmployeeCode(employeeCode);
		        if (employee == null || employee.getEmail() == null) return;
		        String toEmail = employee.getEmail();


		        // ← pull from the already-fetched employee object
		        String department  = employee.getDepartment()  != null ? employee.getDepartment()  : "";
		        String designation = employee.getDesignation() != null ? employee.getDesignation() : "";

		        EmployeeVO approver = employeeRepo.findByEmployeeCode(approvedBy);
		        String approvedByName = approver != null ? approver.getEmployeeName() : approvedBy;

		        Context context = new Context();
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		        context.setVariable("empName",      empName);
		        context.setVariable("action",       action);
		        context.setVariable("reason",       reason);
		        context.setVariable("checkInDate",  checkInDate.format(formatter));
		        context.setVariable("entryIn",      entryIn  != null ? entryIn  : "—");
		        context.setVariable("entryOut",     entryOut != null ? entryOut : "—");
		        context.setVariable("approvedBy",   approvedByName);
		        context.setVariable("department",   department);
		        context.setVariable("designation",  designation);
		        context.setVariable("codeAndName",  employeeCode + " - " + empName);
		        
		        String html = templateEngine.process("checkinout-reply", context);

		        MimeMessage mimeMessage = mailSender.createMimeMessage();
		        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		        helper.setTo(toEmail);

		        String subject = "APPROVED".equalsIgnoreCase(action)
		                ? "Your Adjustment Request Has Been Approved"
		                : "Your Adjustment Request Has Been Rejected";
		        helper.setSubject(subject);
		        helper.setText(html, true);
		        mailSender.send(mimeMessage);

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}

	//WORKFROMHOME
		
		public void sendWfhRequestMail(
		        String toEmail,
		        Long orgId,
		        Long wfhId,
		        String employeeCode,
		        String employeeName,
		        LocalDate wfhDate,
		        String reason,
		        String workAccomplished,
		        String departmentHead,
		        String notifyCode,
		        boolean showButtons) {

		    try {
		        Context context = new Context();
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		        context.setVariable("employeeName",      employeeName);
		        context.setVariable("wfhDate",           wfhDate.format(formatter));
		        context.setVariable("reason",            reason);
		        context.setVariable("workAccomplished",  workAccomplished);
		        context.setVariable("departmentHead",    departmentHead);
		        context.setVariable("showButtons",       showButtons);

		        String encodedEmail = URLEncoder.encode(toEmail, StandardCharsets.UTF_8);

		        context.setVariable("approveUrl",
		                baseUrl + "/api/leaveprocess/mailWfhAction"
		                + "?orgId=" + orgId
		                + "&id=" + wfhId
		                + "&employeeCode=" + employeeCode
		                + "&action=APPROVED"
		                + "&actionBy=" + notifyCode
		                + "&notifyCode=&notify=&screenName=MAIL"
		                + "&email=" + encodedEmail);

		        context.setVariable("rejectPageUrl",
		                baseUrl + "/api/leaveprocess/wfh-reject-page"
		                + "?orgId=" + orgId
		                + "&id=" + wfhId
		                + "&employeeCode=" + employeeCode
		                + "&action=REJECTED"
		                + "&actionBy=" + notifyCode
		                + "&notifyCode=&notify=&screenName=MAIL"
		                + "&email=" + encodedEmail);

		        String html = templateEngine.process("wfh-request", context);

		        MimeMessage mimeMessage = mailSender.createMimeMessage();
		        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		        helper.setTo(toEmail);
		        helper.setSubject("Work From Home Request - " + employeeName);
		        helper.setText(html, true);
		        mailSender.send(mimeMessage);

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}

		public void sendWfhStatusMail(
		        String employeeCode,
		        String employeeName,
		        String action,
		        String rejectReason,
		        LocalDate wfhDate,
		        String reason,
		        String approvedBy) {

		    try {
		        EmployeeVO employee = employeeRepo.findByEmployeeCode(employeeCode);
		        if (employee == null || employee.getEmail() == null) return;
		        String toEmail = employee.getEmail();

		        EmployeeVO approver = employeeRepo.findByEmployeeCode(approvedBy);
		        String approvedByName = approver != null ? approver.getEmployeeName() : approvedBy;

		        Context context = new Context();
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		        context.setVariable("employeeName", employeeName);
		        context.setVariable("action",       action);
		        context.setVariable("wfhDate",      wfhDate.format(formatter));
		        context.setVariable("reason",       reason);
		        context.setVariable("rejectReason", rejectReason);
		        context.setVariable("approvedBy",   approvedByName);

		        String html = templateEngine.process("wfh-reply", context);

		        MimeMessage mimeMessage = mailSender.createMimeMessage();
		        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		        helper.setTo(toEmail);

		        String subject = "APPROVED".equalsIgnoreCase(action)
		                ? "Your WFH Request Has Been Approved"
		                : "Your WFH Request Has Been Rejected";
		        helper.setSubject(subject);
		        helper.setText(html, true);
		        mailSender.send(mimeMessage);

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		
		//permission mail
		
		@Override
		public void sendPermissionRequestMail(
		        String toEmail,
		        Long orgId,
		        Long permissionId,
		        String employeeCode,
		        String employeeName,
		        String date,
		        String fromTime,
		        String toTime,
		        String totalHours,
		        String notes,
		        String notifyCode,
		        boolean showButtons) {

		    try {
		        Context context = new Context();
		        String encodedEmail = URLEncoder.encode(toEmail, StandardCharsets.UTF_8);

		        context.setVariable("employeeName", employeeName);
		        context.setVariable("date",         date);
		        context.setVariable("fromTime",     fromTime);
		        context.setVariable("toTime",       toTime);
		        context.setVariable("totalHours",   totalHours);
		        context.setVariable("notes",        notes);
		        context.setVariable("showButtons",  showButtons);

		        context.setVariable("approveUrl",
		                baseUrl + "/api/employeemaster/mailPermissionAction"
		                + "?orgId=" + orgId
		                + "&id=" + permissionId
		                + "&employeeCode=" + employeeCode
		                + "&action=APPROVED"
		                + "&actionBy=" + notifyCode
		                + "&notifyCode=&notify=&screenName=MAIL"
		                + "&email=" + encodedEmail);

		        context.setVariable("rejectPageUrl",
		                baseUrl + "/api/employeemaster/permission-reject-page"
		                + "?orgId=" + orgId
		                + "&id=" + permissionId
		                + "&employeeCode=" + employeeCode
		                + "&action=REJECTED"
		                + "&actionBy=" + notifyCode
		                + "&notifyCode=&notify=&screenName=MAIL"
		                + "&email=" + encodedEmail);

		        String html = templateEngine.process("permission-request", context);

		        MimeMessage mimeMessage = mailSender.createMimeMessage();
		        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		        helper.setTo(toEmail);
		        helper.setSubject("Permission Request - " + employeeName);
		        helper.setText(html, true);
		        mailSender.send(mimeMessage);

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}

		@Override
		public void sendPermissionStatusMail(
		        String employeeCode,
		        String employeeName,
		        String action,
		        String reason,
		        String date,
		        String fromTime,
		        String toTime,
		        String approvedBy) {

		    try {
		        EmployeeVO employee = employeeRepo.findByEmployeeCode(employeeCode);
		        if (employee == null || employee.getEmail() == null) return;
		        String toEmail = employee.getEmail();

		        EmployeeVO approver = employeeRepo.findByEmployeeCode(approvedBy);
		        String approvedByName = approver != null ? approver.getEmployeeName() : approvedBy;

		        Context context = new Context();
		        context.setVariable("employeeName", employeeName);
		        context.setVariable("action",       action);
		        context.setVariable("date",         date);
		        context.setVariable("fromTime",     fromTime);
		        context.setVariable("toTime",       toTime);
		        context.setVariable("reason",       reason);
		        context.setVariable("approvedBy",   approvedByName);

		        String html = templateEngine.process("permission-reply", context);

		        MimeMessage mimeMessage = mailSender.createMimeMessage();
		        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		        helper.setTo(toEmail);

		        String subject = "APPROVED".equalsIgnoreCase(action)
		                ? "Your Permission Request Has Been Approved"
		                : "Your Permission Request Has Been Rejected";
		        helper.setSubject(subject);
		        helper.setText(html, true);
		        mailSender.send(mimeMessage);

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}

		
		//travel requests
		@Override
		public void sendTravelRequestMail(
		        String toEmail,
		        Long orgId,
		        Long travelId,
		        String employeeCode,
		        String employeeName,
		        String travelTitle,
		        String from,
		        String to,
		        String departureDate,
		        String returnDate,
		        String transportMode,
		        String accommodation,
		        String estimatedCost,
		        String businessPurpose,
		        String notifyCode,
		        boolean showButtons) {

		    try {
		        Context context = new Context();
		        String encodedEmail = URLEncoder.encode(toEmail, StandardCharsets.UTF_8);

		        context.setVariable("employeeName",    employeeName);
		        context.setVariable("travelTitle",     travelTitle);
		        context.setVariable("from",            from);
		        context.setVariable("to",              to);
		        context.setVariable("departureDate",   departureDate);
		        context.setVariable("returnDate",      returnDate);
		        context.setVariable("transportMode",   transportMode);
		        context.setVariable("accommodation",   accommodation);
		        context.setVariable("estimatedCost",   estimatedCost);
		        context.setVariable("businessPurpose", businessPurpose);
		        context.setVariable("showButtons",     showButtons);

		        context.setVariable("approveUrl",
		                baseUrl + "/api/assetmanagement/mailTravelAction"
		                + "?orgId=" + orgId
		                + "&id=" + travelId
		                + "&employeeCode=" + employeeCode
		                + "&action=APPROVED"
		                + "&actionBy=" + notifyCode
		                + "&approvedAmount=0"
		                + "&notifyCode=&notify=&screenName=MAIL"
		                + "&email=" + encodedEmail);

		        context.setVariable("rejectPageUrl",
		                baseUrl + "/api/assetmanagement/travel-reject-page"
		                + "?orgId=" + orgId
		                + "&id=" + travelId
		                + "&employeeCode=" + employeeCode
		                + "&action=REJECTED"
		                + "&actionBy=" + notifyCode
		                + "&notifyCode=&notify=&screenName=MAIL"
		                + "&email=" + encodedEmail);

		        String html = templateEngine.process("travel-request", context);

		        MimeMessage mimeMessage = mailSender.createMimeMessage();
		        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		        helper.setTo(toEmail);
		        helper.setSubject("Travel Request - " + employeeName);
		        helper.setText(html, true);
		        mailSender.send(mimeMessage);

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}

		@Override
		public void sendTravelStatusMail(
		        String employeeCode,
		        String employeeName,
		        String action,
		        String reason,
		        String travelTitle,
		        String from,
		        String to,
		        String departureDate,
		        String returnDate,
		        String approvedAmount,
		        String approvedBy) {

		    try {
		        EmployeeVO employee = employeeRepo.findByEmployeeCode(employeeCode);
		        if (employee == null || employee.getEmail() == null) return;
		        String toEmail = employee.getEmail();

		        EmployeeVO approver = employeeRepo.findByEmployeeCode(approvedBy);
		        String approvedByName = approver != null ? approver.getEmployeeName() : approvedBy;

		        Context context = new Context();
		        context.setVariable("employeeName",  employeeName);
		        context.setVariable("action",        action);
		        context.setVariable("travelTitle",   travelTitle);
		        context.setVariable("from",          from);
		        context.setVariable("to",            to);
		        context.setVariable("departureDate", departureDate);
		        context.setVariable("returnDate",    returnDate);
		        context.setVariable("approvedAmount",approvedAmount);
		        context.setVariable("reason",        reason);
		        context.setVariable("approvedBy",    approvedByName);

		        String html = templateEngine.process("travel-reply", context);

		        MimeMessage mimeMessage = mailSender.createMimeMessage();
		        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		        helper.setTo(toEmail);

		        String subject = "APPROVED".equalsIgnoreCase(action)
		                ? "Your Travel Request Has Been Approved"
		                : "Your Travel Request Has Been Rejected";
		        helper.setSubject(subject);
		        helper.setText(html, true);
		        mailSender.send(mimeMessage);

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		
	}