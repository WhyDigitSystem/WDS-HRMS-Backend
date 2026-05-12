package com.efit.hrms.service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
    private JavaMailSender mailSender;
	
	

    /**
     * Send an HTML formatted email
     */
    @Override
    public void sendHtmlEmail(String fromMail,String toEmail, String subject, String htmlContent) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessage.addHeader("Auto-Submitted", "auto-generated");
            mimeMessage.addHeader("Precedence", "bulk");
            mimeMessage.addHeader("X-Auto-Response-Suppress", "All");

            helper.setFrom(fromMail,"WHY DIGIT SYSTEMS (No Reply)");
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

	
 //forgot password
    
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
}
