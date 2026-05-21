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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efit.hrms.common.CommonConstant;
import com.efit.hrms.common.UserConstants;
import com.efit.hrms.dto.ResponseDTO;
import com.efit.hrms.service.NewDashBoardService;


@CrossOrigin
@RestController
@RequestMapping("/api/newdashboard")
public class NewDashBoardController extends BaseController {

	@Autowired
	NewDashBoardService newDashBoardService;

	public static final Logger LOGGER = LoggerFactory.getLogger(AdvanceController.class);


	@GetMapping("/getMonthlyAttendanceForDashBoard")
	public ResponseEntity<ResponseDTO> getLeaveCountForDashBoard(@RequestParam String employeeCode,
	@RequestParam Long orgId, @RequestParam String department,
			@RequestParam String branch, @RequestParam String type, @RequestParam(required = false) String contractor) {

		String methodName = "getMonthlyAttendanceForDashBoard()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;
		List<Map<String, Object>> leaveDetailsList;

		try {
			leaveDetailsList = newDashBoardService.getMonthlyAttendanceForDashBoard(employeeCode, orgId,
					department, branch, type, contractor);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "AttendanceProcess details retrieved successfully");
			responseObjectsMap.put("attendanceProcessVO", leaveDetailsList); // ✅ Correct key name
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			String errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve AttendanceProcess details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getLeaveTakenReport")
	public ResponseEntity<ResponseDTO> getLeaveTakenReport(
	        @RequestParam Long orgId,
	        @RequestParam String employeecode
	) {

	    String methodName = "getLeaveTakenReport()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;

	    Map<String, Object> responseObjectsMap =
	            new HashMap<>();

	    ResponseDTO responseDTO = null;

	    List<Map<String, Object>> mapp =
	            new ArrayList<>();

	    try {

	        mapp = newDashBoardService.getLeaveTakenReport(
	                orgId,
	                employeecode
	        );

	    } catch (Exception e) {

	        errorMsg = e.getMessage();

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                errorMsg
	        );
	    }

	    if (StringUtils.isBlank(errorMsg)) {

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Leave Taken Report Retrieved Successfully"
	        );

	        responseObjectsMap.put(
	                "leaveTakenReport",
	                mapp
	        );

	        responseDTO =
	                createServiceResponse(responseObjectsMap);

	    } else {

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        "Failed To Retrieve Leave Taken Report",
	                        errorMsg
	                );
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	// ============================================

	@GetMapping("/getLateLoginReportforDashBoard")
	public ResponseEntity<ResponseDTO> getLateCheckinReport(
	        @RequestParam Long orgId,
	        @RequestParam String branchcode,
	        @RequestParam String employeecode
	) {

	    String methodName = "getLateLoginReportforDashBoard()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    List<Map<String, Object>> mapp = new ArrayList<>();

	    try {

	        mapp = newDashBoardService.getLateLoginReportforDashBoard(
	                orgId,
	                branchcode,
	                employeecode
	        );

	    } catch (Exception e) {

	        errorMsg = e.getMessage();

	        LOGGER.error( UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,errorMsg );
	    }

	    if (errorMsg == null || errorMsg.trim().isEmpty()) {
	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Late Checkin Report Retrieved Successfully"
	        );

	        responseObjectsMap.put( "lateCheckinReport",mapp );
	        responseDTO = createServiceResponse(responseObjectsMap);

	    } else {

	        responseDTO =
	                createServiceResponseError( responseObjectsMap,
	                        "Failed To Retrieve Late Checkin Report",errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}
	
	
	//pending request
	
	@GetMapping("/pendingApprovalForDashBoard")
	public ResponseEntity<ResponseDTO> getPendingApprovalForDashBoard(
	        @RequestParam Long orgId,
	        @RequestParam String employeeCode) {

	    String methodName = "getpendingApprovalForDashBoard()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap =
	            new HashMap<>();

	    ResponseDTO responseDTO;

	    List<Map<String, Object>> approvalList;

	    try {

	        approvalList =
	        		newDashBoardService
	                        .pendingApprovalForDashBoard(orgId,employeeCode);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Pending Approval details retrieved successfully");

	        responseObjectsMap.put(
	                "pendingApprovalVO",
	                approvalList);

	        responseDTO =
	                createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        String errorMsg = e.getMessage();

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                errorMsg);

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        "Failed to retrieve Pending Approval details",
	                        errorMsg);
	    }

	    LOGGER.debug(
	            CommonConstant.ENDING_METHOD,
	            methodName);

	    return ResponseEntity
	            .ok()
	            .body(responseDTO);
	}
	
	//working hours api
	
	@GetMapping("/getLastMonthSummaryDashboard")
	public ResponseEntity<ResponseDTO> getAttendanceDashboard(
	        @RequestParam Long orgId,
	        @RequestParam String employeecode) {

	    String methodName = "getAttendanceDashboard()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    ResponseDTO responseDTO = null;

	    List<Map<String, Object>> mapp = new ArrayList<>();

	    try {

	        mapp = newDashBoardService
	                .getAttendanceDashboard(orgId, employeecode);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                errorMsg
	        );
	    }

	    if (StringUtils.isEmpty(errorMsg)) {

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Attendance Dashboard retrieved successfully"
	        );

	        responseObjectsMap.put(
	                "attendanceDashboard",
	                mapp
	        );

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } else {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Failed to retrieve Attendance Dashboard",
	                errorMsg
	        );
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getRejectedRequests")
	public ResponseEntity<ResponseDTO> getRejectedRequests(
	        @RequestParam Long orgid,
	        @RequestParam String fromDate,
	        @RequestParam String toDate,
	        @RequestParam String employeecode,
	        @RequestParam String type) {

	    String methodName = "getRejectedRequests()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    ResponseDTO responseDTO = null;

	    List<Map<String, Object>> rejectedRequestList = new ArrayList<>();

	    try {

	        rejectedRequestList = newDashBoardService.getRejectedRequests(
	                orgid,
	                fromDate,
	                toDate,
	                employeecode,
	                type);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();

	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                errorMsg);
	    }

	    if (StringUtils.isBlank(errorMsg)) {

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Rejected Requests information fetched successfully");

	        responseObjectsMap.put(
	                "rejectedRequestList",
	                rejectedRequestList);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } else {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Rejected Requests information fetch failed",
	                errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
}
