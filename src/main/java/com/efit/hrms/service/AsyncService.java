package com.efit.hrms.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.efit.hrms.entity.TicketVO;

@Service
public class AsyncService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private EmailService emailService;

	@Value("${app.mail.adminEmail}")
	private String adminEmail;

	@Value("${app.mail.noreplay}")
	private String noReplayEmail;

	private String externalUrl = "http://139.5.190.244:8061/api/ticket/createticket";

	// ✅ EXTERNAL API ASYNC
	@Async
	public void externalApiCallAsync(TicketVO ticketVO) {

		try {
			Map<String, Object> body = new HashMap<>();

			body.put("client", "LOCAL_APP");
			body.put("createdBy", ticketVO.getCreatedBy());
			body.put("description", ticketVO.getDescription());
			body.put("email", ticketVO.getEmail());
			body.put("modifiedBy", ticketVO.getUpdatedBy());
			body.put("priority", "HIGH");
			body.put("title", ticketVO.getSubject());

			// 🔥 IMPORTANT
			body.put("sourceId", ticketVO.getSourceId());
			body.put("customer", ticketVO.getCompanyName());
			body.put("sourceOrgId", ticketVO.getOrgId());
			body.put("sourceBranch", ticketVO.getBranch());
			body.put("sourceBranchCode", ticketVO.getBranchCode());
			body.put("projectName", ticketVO.getCompanyName());
			body.put("application", "HRMS");

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

			restTemplate.postForEntity(externalUrl, request, String.class);

			System.out.println("✅ External API Success");

		} catch (Exception e) {
			System.err.println("❌ External API Error: " + e.getMessage());
		}
	}

	// ✅ EMAIL ASYNC
	@Async
	public void sendEmailAsync(TicketVO ticketVO) {

		try {
			String createdOn = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a").format(new Date());

			ClassPathResource resource = new ClassPathResource("templates/email_template.html");

			String content = new String(resource.getInputStream().readAllBytes());

			String htmlContent = content.replace("${ticketId}", ticketVO.getId().toString())
					.replace("${subject}", ticketVO.getSubject()).replace("${status}", ticketVO.getStatus())
					.replace("${description}", ticketVO.getDescription())
					.replace("${raisedBy}", ticketVO.getCreatedBy()).replace("${raisedEmail}", ticketVO.getEmail())
					.replace("${raisedOn}", createdOn);

			// send to admin
			emailService.sendHtmlEmail(noReplayEmail, adminEmail, ticketVO.getSubject(), htmlContent);

			// send to user
			emailService.sendHtmlEmail(noReplayEmail, ticketVO.getEmail(), ticketVO.getSubject(), htmlContent);

			System.out.println("✅ Email Sent");

		} catch (Exception e) {
			System.err.println("❌ Email Error: " + e.getMessage());
		}
	}

	@Async
	public void uploadImageAsync(byte[] fileBytes, String fileName, Long sourceId) {

		try {
			String url = "http://139.5.190.244:8061/api/ticket/uploadTicketBySourceId";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);

			MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

			body.add("file", new ByteArrayResource(fileBytes) {
				@Override
				public String getFilename() {
					return fileName;
				}
			});

			body.add("sourceId", sourceId.toString());

			HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

			restTemplate.postForEntity(url, request, String.class);

			System.out.println("✅ Image API async success");

		} catch (Exception e) {
			System.err.println("❌ Image API async error: " + e.getMessage());
		}
	}

}
