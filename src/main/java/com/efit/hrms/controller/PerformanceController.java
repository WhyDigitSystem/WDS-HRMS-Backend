package com.efit.hrms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efit.hrms.common.CommonConstant;
import com.efit.hrms.common.UserConstants;
import com.efit.hrms.dto.PerformanceGoalsDTO;
import com.efit.hrms.dto.ResponseDTO;
import com.efit.hrms.entity.PerformanceGoalsVO;
import com.efit.hrms.repo.PerformanceGoalsDetailsRepo;
import com.efit.hrms.service.PerformanceGoalsService;

@RestController
@RequestMapping("/api/performancegoals")
public class PerformanceController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(PerformanceController.class);

	@Autowired
	PerformanceGoalsService performanceGoalsService;

	@Autowired
	PerformanceGoalsDetailsRepo performanceGoalsDetailsRepo;

	@PutMapping("/createUpdatePerformanceGoals")
	public ResponseEntity<ResponseDTO> createUpdatePerformanceGoals(@RequestBody PerformanceGoalsDTO performanceGoalsDTO) {
		String methodName = "createUpdatePerformanceGoals()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> performanceGoalsVO = performanceGoalsService
					.createUpdatePerformanceGoals(performanceGoalsDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, performanceGoalsVO.get("message"));
			responseObjectsMap.put("performanceGoalsVO", performanceGoalsVO.get("performanceGoalsVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getReportingUserName")
	public ResponseEntity<ResponseDTO> getReportingUserName(@RequestParam String username) {
		String methodName = "getReportingUserName()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> getReportingUserName = null;
		try {
			// Don't cast! Just receive as Object or correct type
			getReportingUserName = performanceGoalsService.getReportingUserName(username);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Reporting Person Details found Successfully");
			responseObjectsMap.put("getReportingUserName", getReportingUserName);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Pre Goals Details information receive failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPerformanceGoalsByOrgId")
	public ResponseEntity<ResponseDTO> getPerformanceGoalsByOrgId(@RequestParam Long orgId) {
		String methodName = "getPerformanceGoalsByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PerformanceGoalsVO> performanceVO = new ArrayList<>();
		try {
			performanceVO = performanceGoalsService.getPerformanceGoalsByOrgId(orgId);
			responseObjectsMap.put("performanceVO", performanceVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPerformanceGoalsbyreportingto")
	public ResponseEntity<ResponseDTO> getPerformanceGoalsbyreportingto(@RequestParam String reportingto) {
		String methodName = "getPerformanceGoalsbyreportingto()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> getPerformanceGoalsbyreportingto = null;
		try {

			getPerformanceGoalsbyreportingto = performanceGoalsService.getPerformanceGoalsbyreportingto(reportingto);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Pre Goals Details found Successfully");
			responseObjectsMap.put("getPerformanceGoalsbyreportingto", getPerformanceGoalsbyreportingto);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Pre Goals Details information receive failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPerformanceGoals")
	public ResponseEntity<ResponseDTO> getPerformanceGoals(@RequestParam String userName) {
		String methodName = "getPerformanceGoals()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> getPerformanceGoals = null;
		try {
			// Don't cast! Just receive as Object or correct type
			getPerformanceGoals = performanceGoalsService.getPerformanceGoalsbyUserName(userName);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Pre Goals Details found Successfully");
			responseObjectsMap.put("getPerformanceGoals", getPerformanceGoals);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Pre Goals Details information receive failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPerformanceGoalsById")
	public ResponseEntity<ResponseDTO> getPerformanceGoalsById(@RequestParam Long id) {
		String methodName = "getPerformanceGoalsById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		PerformanceGoalsVO performanceGoalsVO = new PerformanceGoalsVO();
		try {
			// Don't cast! Just receive as Object or correct type
			performanceGoalsVO = performanceGoalsService.getPerformanceGoalsById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "performanceGoalsById found Successfully");
			responseObjectsMap.put("performanceGoalsVO", performanceGoalsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"performanceGoalsById information receive failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPerformanceGoalsDetailsReport")
	public ResponseEntity<ResponseDTO> getPerformanceGoalsDetailsReport(@RequestParam Long orgId,
			@RequestParam String pmonth, @RequestParam String branch, @RequestParam String appraisalYear) {
		String methodName = "getPerformanceGoalsDetailsReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PerformanceGoalsVO> getPerformanceGoalsDetails = new ArrayList<>();
		try {
			getPerformanceGoalsDetails = performanceGoalsService.getPerformanceGoalsDetailsReport(orgId, pmonth, branch,
					appraisalYear);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PerformanceGoalsDetails information get successfully ByOrgId");
			responseObjectsMap.put("getPerformanceGoalsDetails", getPerformanceGoalsDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PerformanceGoalsDetails information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updatePerformanceGoalsApprovedDetails")
	public ResponseEntity<ResponseDTO> updatePerformanceGoalsApprovedDetails(@RequestParam(required = false) Long id,
			@RequestParam(required = false) String approve1, @RequestParam(required = false) String approve1name) {

		String methodName = "updatePerformanceGoalsApprovedDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			// Assuming this updates the ticket status internally
			PerformanceGoalsVO preformanceGoalsVO = performanceGoalsService.updatePerformanceGoalsApprovedDetails(id,
					approve1, approve1name);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PreGoalsVO  updated successfully");
			responseObjectsMap.put("preformanceGoalsVO", preformanceGoalsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "PreGoalsVO  update failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPerformanceGoalsByOrgIdAndReportingPerson")
	public ResponseEntity<ResponseDTO> getPerformanceGoalsByOrgIdAndReportingPerson(@RequestParam Long orgId,
			@RequestParam String reportingPerson) {
		String methodName = "getPerformanceGoalsByOrgIdAndReportingPerson()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PerformanceGoalsVO> performanceGoalsVO = new ArrayList<>();
		try {
			performanceGoalsVO = performanceGoalsService.getPerformanceGoalsByOrgIdAndReportingPerson(orgId,
					reportingPerson);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "details information get successfully ByOrgId");
			responseObjectsMap.put("performanceGoalsVO", performanceGoalsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PerformanceGoalsDetails information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	
	@GetMapping("/getPerformanceGoalsByOrgIdAndEmployeeCode")
	public ResponseEntity<ResponseDTO> getPerformanceGoalsByOrgIdAndEmployeeCode(@RequestParam Long orgId,
			@RequestParam String employeeCode) {
		String methodName = "getPerformanceGoalsByOrgIdAndEmployeeCode()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PerformanceGoalsVO> performanceGoalsVO = new ArrayList<>();
		try {
			performanceGoalsVO = performanceGoalsService.getPerformanceGoalsByOrgIdAndEmployeeCode(orgId,
					employeeCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PerformanceGoalsDetails information get successfully ByOrgId");
			responseObjectsMap.put("performanceGoalsVO", performanceGoalsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PerformanceGoalsDetails information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	
	@GetMapping("/getDashBoardDetails")
	public ResponseEntity<ResponseDTO> getDashBoardDetails(@RequestParam Long orgId,
			@RequestParam String pmonth,  @RequestParam String appraisalYear, @RequestParam String employeeCode) {
		String methodName = "getDashBoardDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PerformanceGoalsVO> getPerformanceGoalsDetails = new ArrayList<>();
		try {
			getPerformanceGoalsDetails = performanceGoalsService.getDashBoardDetails(orgId, pmonth,
					appraisalYear,employeeCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"DashBoardDetals information get successfully ByOrgId");
			responseObjectsMap.put("getPerformanceGoalsDetails", getPerformanceGoalsDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"DashBoardDetals information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
}