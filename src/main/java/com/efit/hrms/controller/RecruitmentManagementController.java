package com.efit.hrms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.efit.hrms.dto.CreateOfferDTO;
import com.efit.hrms.dto.JobPostingsDTO;
import com.efit.hrms.dto.OfferLetterDTO;
import com.efit.hrms.dto.ResponseDTO;
import com.efit.hrms.entity.CandidatesVO;
import com.efit.hrms.entity.CreateOfferVO;
import com.efit.hrms.entity.JobPostingsVO;
import com.efit.hrms.entity.OfferLetterVO;
import com.efit.hrms.exception.ApplicationException;
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
	
	
	@GetMapping("getCandidatesByOrgId")
	public ResponseEntity<ResponseDTO> getCandidatesByOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getCandidatesByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CandidatesVO> candidatesVO = null;
		try {
			candidatesVO = recruitmentManagementService.getCandidatesByOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "candidates found by ORGID");
			responseObjectsMap.put("candidatesVO", candidatesVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "candidates not found for orgID: " + orgId;
			responseDTO = createServiceResponseError(responseObjectsMap, "candidates not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("getCandidatesById")
	public ResponseEntity<ResponseDTO> getCandidatesById(@RequestParam Long id) {
		String methodName = "getCandidatesById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		CandidatesVO candidatesVO = null;
		try {
			candidatesVO = recruitmentManagementService.getCandidatesById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "candidates found by ID");
			responseObjectsMap.put("candidatesVO", candidatesVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "candidates not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "candidates not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("getSchedulerCandidatesByOrgId")
	public ResponseEntity<ResponseDTO> getSchedulerCandidatesByOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getSchedulerCandidatesByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CandidatesVO> candidatesVO = null;
		try {
			candidatesVO = recruitmentManagementService.getSchedulerCandidatesByOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "candidates found by ORGID");
			responseObjectsMap.put("candidatesVO", candidatesVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "candidates not found for orgID: " + orgId;
			responseDTO = createServiceResponseError(responseObjectsMap, "candidates not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@PutMapping("/createUpdateOfferLetter")
	public ResponseEntity<ResponseDTO> createUpdateOfferLetter(@RequestBody OfferLetterDTO offerLetterDTO) {
		String methodName = "createUpdateOfferLetter()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		Map<String, Object> responseObjectsMap = new HashMap<String, Object>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> offerLetterVO = recruitmentManagementService.createUpdateOfferLetter(offerLetterDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, offerLetterVO.get("message"));
			responseObjectsMap.put("offerLetterVO", offerLetterVO.get("offerLetterVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	
	@GetMapping("getOfferLetterById")
	public ResponseEntity<ResponseDTO> getOfferLetterById(@RequestParam Long id) {
		String methodName = "getOfferLetterById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		OfferLetterVO offerLetterVO = null;
		try {
			offerLetterVO = recruitmentManagementService.getOfferLetterById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "OfferLetter found by ID");
			responseObjectsMap.put("offerLetterVO", offerLetterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "OfferLeter not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "OfferLetter not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("getOfferLetterByOrgId")
	public ResponseEntity<ResponseDTO> getOfferLetterByOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getOfferLetterByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<OfferLetterVO> offerLetterVO = null;
		try {
			offerLetterVO = recruitmentManagementService.getOfferLetterByOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "OfferLetter found by ORGID");
			responseObjectsMap.put("offerLetterVO", offerLetterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "OfferLetter not found for orgID: " + orgId;
			responseDTO = createServiceResponseError(responseObjectsMap, "OfferLetter not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	
	@PutMapping("/createUpdateCreateOffer")
	public ResponseEntity<ResponseDTO> createUpdateCreateOffer(@RequestBody CreateOfferDTO createOfferDTO) throws ApplicationException {
	    String methodName = "createUpdateCreateOffer()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        // Call service method
	        Map<String, Object> createOfferVO = recruitmentManagementService.createUpdateCreateOffer(createOfferDTO);

	        // Extract message and data
	        Object salaryStructureVO = createOfferVO.get("paramObjectsMap");
	        String message = (String) createOfferVO.getOrDefault("message", "createOffer completed successfully.");

	        // Populate response map
	        responseObjectsMap.put("createOfferVO", createOfferVO);
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, message);


	        // Create structured response
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } catch (Exception e) {
	        LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
	        responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", "Something went wrong.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok(responseDTO);
	}
	
	
	@GetMapping("getCreateOfferById")
	public ResponseEntity<ResponseDTO> getCreateOfferById(@RequestParam Long id) {
		String methodName = "getCreateOfferById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		CreateOfferVO createOfferVO = null;
		try {
			createOfferVO = recruitmentManagementService.getCreateOfferById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CreateOffer found by ID");
			responseObjectsMap.put("createOfferVO", createOfferVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "createOffer not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "createOffer not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("getCreateOfferByOrgId")
	public ResponseEntity<ResponseDTO> getCreateOfferByOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getCreateOfferByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CreateOfferVO> createOfferVO = null;
		try {
			createOfferVO = recruitmentManagementService.getCreateOfferByOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "createOffer found by ORGID");
			responseObjectsMap.put("createOfferVO", createOfferVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "createOffer not found for orgID: " + orgId;
			responseDTO = createServiceResponseError(responseObjectsMap, "createOffer not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("getApprovedCreateOfferByCompany")
	public ResponseEntity<ResponseDTO> getApprovedCreateOfferByCompany(@RequestParam Long orgId,@RequestParam String branchCode,@RequestParam String candidateName) {
		String methodName = "getCreateOfferByStatusAndDepartment()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> createOfferVO = null;
		try {
			createOfferVO = recruitmentManagementService.getApprovedCreateOfferByCompany(orgId,branchCode,candidateName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "createOffer found by ORGID");
			responseObjectsMap.put("createOfferVO", createOfferVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "createOffer not found for orgID: " + orgId;
			responseDTO = createServiceResponseError(responseObjectsMap, "createOffer not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("getCreateOfferByOrgIdAndDepartment")
	public ResponseEntity<ResponseDTO> getCreateOfferByOrgIdAndDepartment(@RequestParam Long orgId,@RequestParam String branchCode,@RequestParam String status ,@RequestParam String department) {
		String methodName = "getCreateOfferByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CreateOfferVO> createOfferVO = null;
		try {
			createOfferVO = recruitmentManagementService.getCreateOfferByOrgIdAndDepartment(orgId,branchCode,status,department);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "createOffer found by ORGID");
			responseObjectsMap.put("createOfferVO", createOfferVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "createOffer not found for orgID: " + orgId;
			responseDTO = createServiceResponseError(responseObjectsMap, "createOffer not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("getPendingCreateOfferByOrgId")
	public ResponseEntity<ResponseDTO> getPendingCreateOfferByOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getPendingCreateOfferByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CreateOfferVO> createOfferVO = null;
		try {
			createOfferVO = recruitmentManagementService.getPendingCreateOfferByOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "createOffer found by ORGID");
			responseObjectsMap.put("createOfferVO", createOfferVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "createOffer not found for orgID: " + orgId;
			responseDTO = createServiceResponseError(responseObjectsMap, "createOffer not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	

	@PutMapping("/createApprovalCreateOffer")
	public ResponseEntity<ResponseDTO> createApprovalCreateOffer(@RequestParam Long orgId, @RequestParam Long id,
			@RequestParam String candidateName, @RequestParam String action, @RequestParam String actionBy,
			@RequestParam(required = false) String notifyCode, @RequestParam(required = false) String notify,@RequestParam(required = false) String screenName,@RequestParam(required = false) String email) {
		String methodName = "createApprovalCreateOffer()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> result = recruitmentManagementService.createApprovalCreateOffer(orgId, id, candidateName, action,
					actionBy, notifyCode, notify,screenName,email);

			// ✅ Correct keys from the returned map
			responseObjectsMap.put("createOfferVO", result.get("createOfferVO"));
			responseObjectsMap.put("message", result.get("message"));

			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("getCreateOfferCountByOrgId")
	public ResponseEntity<ResponseDTO> getCreateOfferCountByOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getCreateOfferCountByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> createOfferVO = null;
		try {
			createOfferVO = recruitmentManagementService.getCreateOfferCountByOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CreateOffer found by ORGID");
			responseObjectsMap.put("createOfferVO", createOfferVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "CreateOffer not found for orgID: " + orgId;
			responseDTO = createServiceResponseError(responseObjectsMap, "CreateOffer not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	
	@GetMapping("getSelectedCandidates")
	public ResponseEntity<ResponseDTO> getSelectedCandidates(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getSelectedCandidates()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CandidatesVO> candidatesVO = null;
		try {
			candidatesVO = recruitmentManagementService.getSelectedCandidates(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Get Selected Candidates");
			responseObjectsMap.put("candidatesVO", candidatesVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "candidates not found for orgID: " + orgId;
			responseDTO = createServiceResponseError(responseObjectsMap, "Candidates not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
}
