package com.efit.hrms.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.efit.hrms.dto.IncrementManagementDTO;
import com.efit.hrms.dto.ResponseDTO;
import com.efit.hrms.entity.IncrementManagementVO;
import com.efit.hrms.entity.LeaveProcessVO;
import com.efit.hrms.entity.SalaryStructureVO;
import com.efit.hrms.exception.ApplicationException;
import com.efit.hrms.service.IncrementManagementServices;

@CrossOrigin
@RestController
@RequestMapping("/api/incrementmanagement")
public class IncrementManagementController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(BasicMasterController.class);	

	@Autowired
	IncrementManagementServices incrementManagementServices;
	
	
	@PutMapping("/createUpdateIncrementManagement")
	public ResponseEntity<ResponseDTO> createUpdateIncrementManagement(@RequestBody IncrementManagementDTO incrementManagementDTO) throws ApplicationException {
	    String methodName = "createUpdateIncrementManagement()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        // Call service method
	        Map<String, Object> incrementManagementVOs = incrementManagementServices.createUpdateIncrementManagement(incrementManagementDTO);

	        // Extract message and data
	        Object incrementManagementVO = incrementManagementVOs.get("paramObjectsMap");
	        String message = (String) incrementManagementVOs.getOrDefault("message", "IncrementManagement completed successfully.");

	        // Populate response map
	        responseObjectsMap.put("incrementManagementVO", incrementManagementVO);
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
	
	
	
	@PutMapping("/createApprovalIncrementManagement")
	public ResponseEntity<ResponseDTO> createApprovalIncrementManagement(@RequestParam Long orgId, @RequestParam Long id,
			@RequestParam String employeeCode, @RequestParam String action, @RequestParam String actionBy,
			@RequestParam String notifyCode, @RequestParam String notify,@RequestParam String screenName,@RequestParam(required = false) String email) {
		String methodName = "createApprovalIncrementManagement()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> result = incrementManagementServices.createApprovalIncrementManagement(orgId, id, employeeCode, action,
					actionBy, notifyCode, notify,screenName,email);

			// ✅ Correct keys from the returned map
			responseObjectsMap.put("incrementManagementVO", result.get("incrementManagementVO"));
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
	
	
//	@GetMapping("/getByOrgId")
//	public ResponseEntity<ResponseDTO> getLeaveProcessByOrgId(@RequestParam Long orgId,@RequestParam String employeeCode) {
//		String methodName = "getLeaveProcessByOrgId()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		List<LeaveProcessVO> leaveProcessVO = null;
//		try {
//			leaveProcessVO = leaveProcessService.getLeaveProcessByOrgId(orgId);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//		}
//		if (StringUtils.isEmpty(errorMsg)) {
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "leaveProcess found by ORGID");
//			responseObjectsMap.put("leaveProcessVO", leaveProcessVO);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} else {
//			errorMsg = "leaveProcess not found for orgID: " + orgId;
//			responseDTO = createServiceResponseError(responseObjectsMap, "leaveProcess not found", errorMsg);
//		}
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}

	
	@GetMapping("/getIncrementManagementForDashBoard")
	public ResponseEntity<ResponseDTO> getIncrementManagementForDashBoard(@RequestParam Long orgId,
			@RequestParam String reportingPersonCode, @RequestParam String branchCode) {

		String methodName = "getIncrementManagementForDashBoard()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;
		List<IncrementManagementVO> incrementManagementVO;

		try {
			incrementManagementVO = incrementManagementServices.getIncrementManagementForDashBoard(orgId, reportingPersonCode, branchCode);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "IncrementManagement details retrieved successfully");
			responseObjectsMap.put("incrementManagementVO", incrementManagementVO); // ✅ Correct key name
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			String errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve IncrementManagement details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getLatestSalaryStructureByOrgId")
	public ResponseEntity<ResponseDTO> getLatestSalaryStructureByOrgId(@RequestParam Long orgId,@RequestParam String employeeCode) {
	    String methodName = "getLatestSalaryStructureByOrgId()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;
	    
	    try {
	        // Fetch Salary Heads and handle nulls safely
	        List<SalaryStructureVO> salaryStructureVO = Optional.ofNullable(incrementManagementServices.getLatestSalaryStructureByOrgId(orgId,employeeCode))
	                                                    .orElseGet(Collections::emptyList);
	        
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SalaryStructure information retrieved successfully By OrgId");
	        responseObjectsMap.put("SalaryStructureVO", salaryStructureVO);
	        responseDTO = createServiceResponse(responseObjectsMap);
	        
	        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	        return ResponseEntity.ok(responseDTO);

	    } catch (Exception e) {
	        String errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	        responseDTO = createServiceResponseError(responseObjectsMap, 
	                     "Failed to retrieve SalaryStructure information By OrgId", errorMsg);
	        
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
	    }
	}

	
	
	@GetMapping("/getSalaryHistoryforIncrement")
	public ResponseEntity<ResponseDTO> getSalaryHistoryforIncrement(
	        @RequestParam Long orgId,
	        @RequestParam String employeeCode) {

	    String methodName = "getSalaryHistoryforIncrement()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        // Service call to fetch salary history
	        List<Map<String, Object>> salaryHistoryList = incrementManagementServices.getSalaryHistoryforIncrement(orgId, employeeCode);

	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, 
	                "Salary history retrieved successfully");
	        responseObjectsMap.put("salaryHistoryList", salaryHistoryList);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {
	        String errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Failed to retrieve salary history",
	                errorMsg
	        );
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}

	
}
