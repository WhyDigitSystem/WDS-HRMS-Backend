package com.efit.hrms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efit.hrms.common.CommonConstant;
import com.efit.hrms.common.UserConstants;
import com.efit.hrms.dto.CandidatesDTO;
import com.efit.hrms.dto.JobPostingsDTO;
import com.efit.hrms.dto.ResponseDTO;
import com.efit.hrms.entity.JobPostingsVO;
import com.efit.hrms.service.RecruitmentManagementService;

@CrossOrigin
@RestController
@RequestMapping("/api/recruitmentmanagement")
public class RecruitmentManagementController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(CheckInOutController.class);

	
	@Autowired
	RecruitmentManagementService recruitmentManagementService;
	
	@PutMapping("/createUpdateJobPostings")
	public ResponseEntity<ResponseDTO> createUpdateJobPostings(@RequestBody JobPostingsDTO jobPostingsDTO) {
		String methodName = "createUpdateJobPostings()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		Map<String, Object> responseObjectsMap = new HashMap<String, Object>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> jobPostingsVO = recruitmentManagementService.createUpdateJobPostings(jobPostingsDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, jobPostingsVO.get("message"));
			responseObjectsMap.put("jobPostingsVO", jobPostingsVO.get("jobPostingsVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("getJobPostingsByOrgId")
	public ResponseEntity<ResponseDTO> getJobPostingsByOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getJobPostingsByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<JobPostingsVO> jobPostingsVO = null;
		try {
			jobPostingsVO = recruitmentManagementService.getJobPostingsByOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "JobPostings found by ORGID");
			responseObjectsMap.put("jobPostingsVO", jobPostingsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "JobPostings not found for orgID: " + orgId;
			responseDTO = createServiceResponseError(responseObjectsMap, "JobPostings not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("getJobPostingsById")
	public ResponseEntity<ResponseDTO> getJobPostingsById(@RequestParam Long id) {
		String methodName = "getJobPostingsById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		JobPostingsVO jobPostingsVO = null;
		try {
			jobPostingsVO = recruitmentManagementService.getJobPostingsById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "JobPostings found by ID");
			responseObjectsMap.put("jobPostingsVO", jobPostingsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "JobPostings not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "JobPostings not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	//Candidates
	
	@PutMapping("/createUpdateCandidates")
	public ResponseEntity<ResponseDTO> createUpdateCandidates(@RequestBody CandidatesDTO candidatesDTO) {
		String methodName = "createUpdateCandidates()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		Map<String, Object> responseObjectsMap = new HashMap<String, Object>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> candidatesVO = recruitmentManagementService.createUpdateCandidates(candidatesDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, candidatesVO.get("message"));
			responseObjectsMap.put("candidatesVO", candidatesVO.get("candidatesVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
}
