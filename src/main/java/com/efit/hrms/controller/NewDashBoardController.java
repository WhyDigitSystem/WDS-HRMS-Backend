package com.efit.hrms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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


	@GetMapping("/getLeaveCountForDashBoard")
	public ResponseEntity<ResponseDTO> getLeaveCountForDashBoard(@RequestParam String employeeCode,
	@RequestParam Long orgId, @RequestParam String department,
			@RequestParam String branch, @RequestParam String type, @RequestParam(required = false) String contractor) {

		String methodName = "getLeaveDetailsForLeaveProcess()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;
		List<Map<String, Object>> leaveDetailsList;

		try {
			leaveDetailsList = newDashBoardService.getLeaveCountForDashBoard(employeeCode, orgId,
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
}
