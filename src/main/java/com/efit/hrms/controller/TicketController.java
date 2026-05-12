package com.efit.hrms.controller;


import java.net.URLDecoder;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efit.hrms.common.CommonConstant;
import com.efit.hrms.common.UserConstants;
import com.efit.hrms.dto.CommentsDTO;
import com.efit.hrms.dto.ResponseDTO;
import com.efit.hrms.dto.TicketDTO;
import com.efit.hrms.entity.CommentsVO;
import com.efit.hrms.entity.TicketVO;
import com.efit.hrms.repo.TicketRepo;
import com.efit.hrms.service.TicketService;


@CrossOrigin
@RestController
@RequestMapping("/api/ticketcontroller")
public class TicketController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(TicketController.class);
	
	@Autowired
	TicketService ticketService;
	
	@Autowired
	TicketRepo ticketRepo;
	
	@PutMapping("/createUpdateTicket")
	public ResponseEntity<ResponseDTO> CreateUpdateTicket(@Valid @RequestBody TicketDTO ticketDTO) {
		String methodName = "createUpdateTicket()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
	        Map<String, Object> ticketVO = ticketService.createUpdateTicket(ticketDTO);
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, ticketVO.get("message"));
	        responseObjectsMap.put("ticketVO", ticketVO.get("ticketVO")); // Corrected key
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }
	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/uploadTicketScreenShotInBloob")
	public ResponseEntity<ResponseDTO> uploadTicketScreenShotInBloob(@RequestParam("file") MultipartFile file,
			@RequestParam Long id) {
		String methodName = "uploadTicketScreenShotInBloob()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		TicketVO ticketVO = null;
		try {
			ticketVO = ticketService.uploadTicketScreenShotInBloob(file, id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Upload PartImage", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Ticket ScreenShot Successfully Upload");
			responseObjectsMap.put("ticketVO", ticketVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Ticket ScreenShot Upload Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getTicketById")
	public ResponseEntity<ResponseDTO> getTicketById(@RequestParam(required = false) Long id) {
		String methodName = "getTicketById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Optional<TicketVO> ticketVO = null;
		try {
			ticketVO = ticketService.getTicketById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();   
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Ticket information get successfully By Id");
			responseObjectsMap.put("ticketVO", ticketVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Ticket information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getTicketByUserName")
	public ResponseEntity<ResponseDTO> getTicketByUserName(@RequestParam(required = false) String userName,@RequestParam(required = false) Long orgId) {
		String methodName = "getTicketByUserName()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<TicketVO> ticketVO = new ArrayList<TicketVO>();
		try {
			ticketVO = ticketService.getTicketByUserName(userName,orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();   
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Ticket information get successfully By userName");
			responseObjectsMap.put("ticketVO", ticketVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Ticket information receive failed By UserName", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getTicketByOrgId")
	public ResponseEntity<ResponseDTO> getTicketByOrgId(@RequestParam(required = false) Long orgId) {
		String methodName = "getTicketByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<TicketVO> ticketVO = new ArrayList<TicketVO>();
		try {
			ticketVO = ticketService.getTicketByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();   
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Ticket information get successfully By orgId");
			responseObjectsMap.put("ticketVO", ticketVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Ticket information receive failed By orgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	//Comments
	
	@PutMapping("/updateCreateComments")
	public ResponseEntity<ResponseDTO> updateCreateComments(@Valid @RequestBody CommentsDTO commentsDTO) {
		String methodName = "updateCreateComments()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
	        Map<String, Object> commentsVO = ticketService.updateCreateComments(commentsDTO);
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, commentsVO.get("message"));
	        responseObjectsMap.put("commentsVO", commentsVO.get("commentsVO")); // Corrected key
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }
	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getCommentsByTicketId")
	public ResponseEntity<ResponseDTO> getCommentsByTicketId(@RequestParam(required = false) Long ticketId,@RequestParam(required = false) Long orgId ) {
		String methodName = "getCommentsByTicketId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CommentsVO> commentsVO = new ArrayList<CommentsVO>();
		try {
			commentsVO = ticketService.getCommentsByTicketId(ticketId,orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();   
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Comments information get successfully By userName");
			responseObjectsMap.put("commentsVO", commentsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Comments information receive failed By UserName", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PutMapping("/updateTicketStatus")
	public ResponseEntity<ResponseDTO> updateTicketStatus(
	        @RequestParam(required = false) Long orgId,
	        @RequestParam(required = false) Long ticketId,
	        @RequestParam(required = false) String status,
	        @RequestParam(required = false) String userName) {
	    
	    String methodName = "updateTicketStatus()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        // Assuming this updates the ticket status internally
	        TicketVO ticketVO = ticketService.updateTicketStatus(orgId, ticketId,status,userName);

	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Ticket status updated successfully");
	        responseObjectsMap.put("ticketVO", ticketVO);
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap, "Ticket status update failed", errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/deleteCommentsById")
	public ResponseEntity<ResponseDTO> deleteCommentsById(@RequestParam(required = true) Long id) {
		String methodName = "deleteCommentsById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			ticketService.deleteCommentsById(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Comments deleted successfully By Id");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Comments deletion failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	//Notification
	
	@GetMapping("/getTicketNotification")
	public ResponseEntity<ResponseDTO> getTicketNotification(@RequestParam(required = false) Long orgId) {
		String methodName = "getTicketNotification()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String,Object>> ticketVOs = new ArrayList<Map<String,Object>>();
		try {
			ticketVOs = ticketService.getTicketNotification(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();   
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Ticket Notification get successfully");
			responseObjectsMap.put("ticketVOs", ticketVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Ticket  Notification receive failed By UserName", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PutMapping("/updateNotification")
	public ResponseEntity<ResponseDTO> updateNotification(
	        @RequestParam(required = false) Long orgId,
	        @RequestParam(required = false) Long ticketId,
	        @RequestParam(required = false) String status) {
	    
	    String methodName = "updateNotification()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        // Assuming this updates the ticket status internally
	        TicketVO ticketVO = ticketService.updateNotification(orgId, ticketId,status);

	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Notification status updated successfully");
	        responseObjectsMap.put("ticketVO", ticketVO);
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap, "Notification update failed", errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getNotificationFromUser")
	public ResponseEntity<ResponseDTO> getNotificationFromUser(@RequestParam(required = false) String userName) {
		String methodName = "getNotificationFromUser()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String,Object>> ticketVOs = new ArrayList<Map<String,Object>>();
		try {
			ticketVOs = ticketService.getNotificationFromUser(userName);
		} catch (Exception e) {
			errorMsg = e.getMessage();   
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "User Notification get successfully");
			responseObjectsMap.put("ticketVOs", ticketVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"User  Notification receive failed By UserName", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PutMapping("/clearUserNotification")
	public ResponseEntity<ResponseDTO> clearUserNotification(
	        @RequestParam(required = false) Long orgId,
	        @RequestParam(required = false) String userName,
	        @RequestParam(required = false) Long ticketId,
	        @RequestParam(required = false) String status) {
	    
	    String methodName = "clearUserNotification()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        // Assuming this updates the ticket status internally
	        TicketVO ticketVO = ticketService.clearUserNotification(orgId,userName,ticketId,status);

	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "User Notification Clear successfully");
	        responseObjectsMap.put("ticketVO", ticketVO);
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap, "User Notification Clear failed", errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}
	@PostMapping("/createComments")
	public ResponseEntity<ResponseDTO> createComments(@RequestBody CommentsDTO commentDTO) {
		String methodName = "createComments()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> commentVO = ticketService.createComments(commentDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, commentVO.get("message"));
			responseObjectsMap.put("commentVO", commentVO.get("commentVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllCommentsAnotherServer")
	public ResponseEntity<ResponseDTO> getAllCommentsAnotherServer(@RequestParam Long ticketId) {
		String methodName = "getAllCommentsAnotherServer()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CommentsVO> commentsVO = new ArrayList<CommentsVO>();
		try {
			commentsVO = ticketService.getAllCommentsAnotherServer(ticketId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "comments information get successfully By TicketId");
			responseObjectsMap.put("commentsVO", commentsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "comments information receive failed By orgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllCommentsMyServer")
	public ResponseEntity<ResponseDTO> getAllCommentsMyServer(@RequestParam Long ticketId) {
		String methodName = "getAllCommentsMyServer()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CommentsVO> commentsVO = new ArrayList<CommentsVO>();
		try {
			commentsVO = ticketService.getAllCommentsMyServer(ticketId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "comments information get successfully By TicketId");
			responseObjectsMap.put("commentsVO", commentsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "comments information receive failed By orgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateComments")
	public CommentsVO updateComment(@RequestBody CommentsDTO dto) {
		return ticketService.updateComments(dto);
	}

	@DeleteMapping("/deleteComments")
	public ResponseEntity<ResponseDTO> deleteComments(@RequestParam(required = false) Long id,
			@RequestParam(required = false) Long sourceId) {

		String methodName = "deleteComments()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			ticketService.deleteComments(id, sourceId);

		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Comment deleted successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Comment delete failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}
	
	
	@PutMapping("/updateTicketFromRemote")
	public ResponseEntity<ResponseDTO> updateTicketFromRemote(@RequestParam Long orgId, @RequestParam Long id,
			@RequestParam String status, @RequestParam String empCode, @RequestParam String email,
			@RequestParam String ticketStatus) {

		Map<String, Object> map = new HashMap<>();
		ResponseDTO response;

		try {
			TicketVO ticket = ticketRepo.findByOrgIdAndIdEmail(orgId, id, email);

			if (ticket == null) {
				throw new RuntimeException("Ticket not found");
			}
			String decodedStatus = URLDecoder.decode(ticketStatus, StandardCharsets.UTF_8);
			ticket.setStatus(status);
			ticket.setTicketStatus(decodedStatus);
			ticket.setUpdatedBy(empCode);
//			ticket.setCompletedBy(empCode);
			ticket.setUpdatedDate(LocalDate.now());
			ticketRepo.save(ticket);

			map.put("message", "✅ Remote ticket updated");
			map.put("ticket", ticket);

			response = createServiceResponse(map);

		} catch (Exception e) {
			response = createServiceResponseError(map, "❌ Remote update failed", e.getMessage());
		}

		return ResponseEntity.ok(response);
	}
	
}



