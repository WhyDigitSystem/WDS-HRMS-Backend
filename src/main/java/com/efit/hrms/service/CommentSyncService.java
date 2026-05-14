package com.efit.hrms.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.efit.hrms.entity.CommentsVO;


@Service
public class CommentSyncService {

	@Autowired
	private RestTemplate restTemplate;

	@Async("taskExecutor")
	public void sendToServerB(CommentsVO commentsVO) {

		try {
			System.out.println("🚀 Sending A → B for ID: " + commentsVO.getId());

			Map<String, Object> body = new HashMap<>();

			body.put("comment", commentsVO.getComments());
			body.put("sourceId", commentsVO.getId());
			body.put("sourceOrgId", commentsVO.getOrgId());
			body.put("sourceTicketId", commentsVO.getTicketId());
			body.put("sourceUserName", commentsVO.getUserName());
			body.put("application", "HRMS");

			body.put("ticketId", commentsVO.getTicketId());

//			String url = "http://localhost:8061/api/ticket/createComments";

			String url = "http://139.5.190.244:8061/api/ticket/createComments";


			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

			System.out.println("➡️ A → B Payload: " + body);

			ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

			System.out.println("✅ A → B Response: " + response.getBody());

		} catch (Exception e) {
			System.out.println("❌ ERROR A → B");
			e.printStackTrace();
		}
	}

	@Async("taskExecutor")
	public void updateToServerB(CommentsVO vo) {

		try {
			Map<String, Object> body = new HashMap<>();

			body.put("comment", vo.getComments());
			body.put("commentName", vo.getUserName());
			body.put("ticketId", vo.getTicketId());

			// 🔥 THIS IS THE LINK
			body.put("sourceId", vo.getId());

			body.put("sourceUserName", vo.getSourceUserName());
			body.put("sourceOrgId", vo.getOrgId());

			String url = "http://139.5.190.244:8061/api/ticket/updateComments";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

			System.out.println("📤 A → B Payload: " + body);

			restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

			System.out.println("✅ A → B updated");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Async("taskExecutor")
	public void deleteInServerB(Long id) {

	    try {

	        // 🔥 Only sourceId is required for sync
	        String url = "http://139.5.190.244:8061/api/ticket/deleteComments?sourceId=" + id;

	        System.out.println("📤 A → B DELETE URL: " + url);

	        restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);

	        System.out.println("✅ A → B delete synced");

	    } catch (Exception e) {
	        System.err.println("❌ Error while calling Server B");
	        e.printStackTrace();
	    }
	}
}

