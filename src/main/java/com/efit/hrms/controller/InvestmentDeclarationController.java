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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efit.hrms.common.CommonConstant;
import com.efit.hrms.common.UserConstants;
import com.efit.hrms.dto.InvestmentDeclarationDTO;
import com.efit.hrms.dto.ResponseDTO;
import com.efit.hrms.entity.InvestmentDeclarationVO;
import com.efit.hrms.service.InvestmentDeclarationService;

@RestController
@RequestMapping("/api/investmentDeclaration")
public class InvestmentDeclarationController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(InvestmentDeclarationController.class);

	@Autowired
	InvestmentDeclarationService investmentDeclarationService;

	// TaxInvoice

	@GetMapping("/getAllCostEstimationByOrgId")
	public ResponseEntity<ResponseDTO> getAllCostEstimationByOrgId(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String employeeCode) {
		String methodName = "getAllCostEstimationByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<InvestmentDeclarationVO> investmentDeclarationVO = new ArrayList<>();
		try {
			investmentDeclarationVO = investmentDeclarationService.getInvestmentDeclarationDetails(orgId, branchCode,
					employeeCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"InvestmentDeclaration information get successfully ByOrgId");
			responseObjectsMap.put("investmentDeclarationVO", investmentDeclarationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"InvestmentDeclaration information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getInvestmentDeclarationById")
	public ResponseEntity<ResponseDTO> getInvestmentDeclarationById(@RequestParam Long id) {
		String methodName = "getInvestmentDeclarationById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		InvestmentDeclarationVO investmentDeclarationVO = new InvestmentDeclarationVO();
		try {
			investmentDeclarationVO = investmentDeclarationService.getInvestmentDeclarationById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CostEstimation information get successfully By id");
			responseObjectsMap.put("investmentDeclarationVO", investmentDeclarationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"CostEstimation information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCreateInvestmentDeclaration")
	public ResponseEntity<ResponseDTO> updateCreateInvestmentDeclaration(
			@RequestBody InvestmentDeclarationDTO costEstimationDTO) {
		String methodName = "updateCreateInvestmentDeclaration()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> investmentDeclarationVO = investmentDeclarationService
					.updateCreateInvestmentDeclaration(costEstimationDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, investmentDeclarationVO.get("message"));
			responseObjectsMap.put("investmentDeclarationVO", investmentDeclarationVO.get("investmentDeclarationVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDashBoardDetailsNew")
	public ResponseEntity<ResponseDTO> getDashBoardDetailsNew(@RequestParam Long orgId, @RequestParam String branch,
			@RequestParam String employeeCode) {
		String methodName = "getDashBoardDetailsNew()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> dashBoardDetails = new ArrayList<>();
		try {
			dashBoardDetails = investmentDeclarationService.getDashBoardDetailsNew(orgId, branch, employeeCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DashBoard information get successfully");
			responseObjectsMap.put("dashBoardDetails", dashBoardDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "DashBoard information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/uploadImageInvestmentDeclarationDetails")
	public ResponseEntity<ResponseDTO> uploadImageInvestmentDeclarationDetails(@RequestParam List<MultipartFile> file,
			@RequestParam Long investmentDeclarationId, @RequestParam List<Long> investmentDeclarationDetailsId) {

		String methodName = "uploadImageInvestmentDeclarationDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;
		String errorMsg = null;

		try {
			String investmentDeclarationVO = investmentDeclarationService.uploadImageInvestmentDeclarationDetails(file,
					investmentDeclarationId, investmentDeclarationDetailsId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "InvestmentDeclaration Successfully Uploaded");
			responseObjectsMap.put("investmentDeclarationVO", investmentDeclarationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Upload PartImage", methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "InvestmentDeclaration Upload Failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/approveInvestmentDeclaration")
	public ResponseEntity<ResponseDTO> approveInvestmentDeclaration(@RequestParam Long orgId, @RequestParam Long id,
			@RequestParam String employeeCode, @RequestParam String action, @RequestParam String actionBy,
			@RequestParam Long sourceId) {
		String methodName = "approveInvestmentDeclaration()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> investmentDeclarationVO = investmentDeclarationService
					.approveInvestmentDeclaration(orgId, id, employeeCode, action, actionBy, sourceId);
			responseObjectsMap.put("investmentDeclarationVO", investmentDeclarationVO);
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
