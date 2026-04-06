package com.efit.hrms.service;

import java.time.LocalDate;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class SeparationMailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.from}")
    private String fromEmail;

    public void sendSeparationMail(String employeeEmail,String employeeName) {

        try {

            Context context = new Context();
            context.setVariable("employeeName", employeeName);

            String htmlContent = templateEngine.process("separation_template", context);

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message,true);

            helper.setFrom(fromEmail);
            helper.setTo(employeeEmail);
            helper.setSubject("Separation Process Initiated");

            helper.setText(htmlContent,true);

            ClassPathResource logo =
                    new ClassPathResource("static/logo.png");

            helper.addInline("companyLogo", logo);

            mailSender.send(message);

        }
        catch(Exception e){
            System.out.println("EMAIL ERROR");
            e.printStackTrace();
        }
        }
    
    /* 2️⃣ Employee Completes Clearance → Mail sent to HR
    */
   public void sendClearanceCompletedMail(String hrEmail, String employeeName) {

       try {

           Context context = new Context();
           context.setVariable("employeeName", employeeName);

           String htmlContent =
                   templateEngine.process("clearance_completed_template", context);

           MimeMessage message = mailSender.createMimeMessage();

           MimeMessageHelper helper =
                   new MimeMessageHelper(message, true);

           helper.setFrom(fromEmail);
           helper.setTo(hrEmail);
           helper.setSubject("Employee Clearance Completed");

           helper.setText(htmlContent, true);

           ClassPathResource logo =
                   new ClassPathResource("static/logo.png");

           helper.addInline("companyLogo", logo);

           mailSender.send(message);

       } catch (Exception e) {

           System.out.println("ERROR SENDING HR MAIL");
           e.printStackTrace();

       }
   }

   public void sendInterviewCompletedMail(String[] emails,
           String employeeName,
           LocalDate interviewDate, Long separationId) {

			try {
			
			Context context = new Context();
			context.setVariable("employeeName", employeeName);
			context.setVariable("interviewDate", interviewDate);
			
			// approval link
			context.setVariable("approveUrl",
			"http://localhost:8047/api/employeseparation/approve/" + separationId);
			
			context.setVariable("rejectUrl",
			        "http://localhost:8047/api/employeseparation/reject/" + separationId);
			
			String htmlContent =
			templateEngine.process("interview_completed_template", context);
			
			MimeMessage message = mailSender.createMimeMessage();
			
			MimeMessageHelper helper =
			new MimeMessageHelper(message, true);
			
			helper.setFrom(fromEmail);
			helper.setTo(emails);
			helper.setSubject("Exit Interview Completed");
			
			helper.setText(htmlContent, true);
			
			ClassPathResource logo =
			new ClassPathResource("static/logo.png");
			
			helper.addInline("companyLogo", logo);
			
			mailSender.send(message);
			
			} catch (Exception e) {
			e.printStackTrace();
			}
			}
   
   
    }



