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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efit.hrms.common.CommonConstant;
import com.efit.hrms.common.UserConstants;
import com.efit.hrms.dto.ResponseDTO;
import com.efit.hrms.service.PayslipSettlementServie;

@RestController
@RequestMapping("/api/payslipsettlement")
public class PayslipSettlementController extends BaseController {
	
	
	@Autowired
	PayslipSettlementServie payslipSettlementServie;
	public static final Logger LOGGER = LoggerFactory.getLogger(PayslipSettlementController.class);
	
	@GetMapping("/getpayslipSettlementDetails")
	public ResponseEntity<ResponseDTO> getpayslipSettlementDetails(@RequestParam Long orgId ,@RequestParam String empCode,String branchCode) {
		String methodName = "getpayslipSettlementDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> payslipSettlementDetails = null;
		try {
			// Don't cast! Just receive as Object or correct type
			payslipSettlementDetails = payslipSettlementServie.getpayslipSettlementDetails(orgId,empCode, branchCode);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "payslipSettlementDetails found Successfully");
			responseObjectsMap.put("payslipSettlementDetails", payslipSettlementDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "payslipSettlementDetails information receive failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getSeparationEmployeeForSettlement")
	public ResponseEntity<ResponseDTO> getSeparationEmployeeForSettlement(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getSeparationEmployeeForSettlement()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> employeeVO = new ArrayList<>();
		try {
			employeeVO = payslipSettlementServie.getSeparationEmployeeForSettlement(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Employee information get successfully");
			responseObjectsMap.put("employeeVO", employeeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Employee information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}
