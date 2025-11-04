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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efit.hrms.common.CommonConstant;
import com.efit.hrms.common.UserConstants;
import com.efit.hrms.dto.AssetAllocationDTO;
import com.efit.hrms.dto.AssetMasterDTO;
import com.efit.hrms.dto.ExpenseClaimsDTO;
import com.efit.hrms.dto.ResponseDTO;
import com.efit.hrms.dto.TravelRequestsDTO;
import com.efit.hrms.entity.AssetAllocationVO;
import com.efit.hrms.entity.AssetMasterVO;
import com.efit.hrms.entity.ExpenseClaimsVO;
import com.efit.hrms.entity.TravelRequestsVO;
import com.efit.hrms.service.AssetManagementService;

@CrossOrigin
@RestController
@RequestMapping("/api/assetmanagement")
public class AssetManagementController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(CheckInOutController.class);

	@Autowired
	AssetManagementService assetManagementService;
	
	@PutMapping("/CreateUpdateAssetMaster")
	public ResponseEntity<ResponseDTO> CreateUpdateAssetMaster(@RequestBody AssetMasterDTO assetMasterDTO) {
		String methodName = "CreateUpdateAssetMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		Map<String, Object> responseObjectsMap = new HashMap<String, Object>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> assetMasterVO = assetManagementService.CreateUpdateAssetMaster(assetMasterDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, assetMasterVO.get("message"));
			responseObjectsMap.put("assetMasterVO", assetMasterVO.get("assetMasterVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PostMapping("/uploadAssetImages/{assetMasterId}")
	public ResponseEntity<ResponseDTO> uploadAssetImages(
	        @PathVariable Long assetMasterId,
	        @RequestParam("files") List<MultipartFile> files,
	        @RequestParam("createdBy") String createdBy) {

	    String methodName = "uploadAssetImages()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        Map<String, Object> result = assetManagementService.uploadMultipleAssetImages(assetMasterId, files, createdBy);
	        responseObjectsMap.putAll(result);
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } catch (Exception e) {
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());
	        responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok(responseDTO);
	}

	
	
	@GetMapping("getAssetMasterById")
	public ResponseEntity<ResponseDTO> getAssetMasterById(@RequestParam Long id) {
		String methodName = "getAssetMasterById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		AssetMasterVO assetMasterVO = null;
		try {
			assetMasterVO = assetManagementService.getAssetMasterById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "AssetMaster found by ID");
			responseObjectsMap.put("assetMasterVO", assetMasterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "AssetMaster not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "AssetMaster not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("getAssetMasterByOrgId")
	public ResponseEntity<ResponseDTO> getAssetMasterByOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getAssetMasterByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<AssetMasterVO> assetMasterVO = null;
		try {
			assetMasterVO = assetManagementService.getAssetMasterByOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "AssetMaster found by ORGID");
			responseObjectsMap.put("assetMasterVO", assetMasterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "AssetMaster not found for orgID: " + orgId;
			responseDTO = createServiceResponseError(responseObjectsMap, "AssetMaster not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	
	//assetallocation
	
	@PutMapping("/CreateUpdateAssetAllocation")
	public ResponseEntity<ResponseDTO> CreateUpdateAssetAllocation(@RequestBody AssetAllocationDTO assetAllocationDTO) {
		String methodName = "CreateUpdateAssetAllocation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		Map<String, Object> responseObjectsMap = new HashMap<String, Object>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> assetAllocationVO = assetManagementService.CreateUpdateAssetAllocation(assetAllocationDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, assetAllocationVO.get("message"));
			responseObjectsMap.put("assetAllocationVO", assetAllocationVO.get("assetAllocationVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("getAssetNameCodeByOrgId")
	public ResponseEntity<ResponseDTO> getAssetNameCodeByOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getAssetNameCodeByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> assetMasterVO = null;
		try {
			assetMasterVO = assetManagementService.getAssetNameCodeByOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "AssetMasterVO Name and Code found by ORGID");
			responseObjectsMap.put("assetMasterVO", assetMasterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "AssetMasterVO Name and Code  not found for orgID: " + orgId;
			responseDTO = createServiceResponseError(responseObjectsMap, "AssetMasterVO Name and Code  not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("getAssetAllocationByOrgId")
	public ResponseEntity<ResponseDTO> getAssetAllocationByOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getAssetAllocationByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<AssetAllocationVO> assetAllocationVO = null;
		try {
			assetAllocationVO = assetManagementService.getAssetAllocationByOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "AssetAllocation found by ORGID");
			responseObjectsMap.put("assetAllocationVO", assetAllocationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "AssetAllocation not found for orgID: " + orgId;
			responseDTO = createServiceResponseError(responseObjectsMap, "AssetAllocation not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("getAssetAllocationById")
	public ResponseEntity<ResponseDTO> getAssetAllocationById(@RequestParam Long id) {
		String methodName = "getAssetAllocationById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		AssetAllocationVO assetAllocationVO = null;
		try {
			assetAllocationVO = assetManagementService.getAssetAllocationById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "AssetAllocation found by ID");
			responseObjectsMap.put("assetAllocationVO", assetAllocationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "AssetAllocation not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "AssetAllocation not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("getAssetCountByOrgId")
	public ResponseEntity<ResponseDTO> getAssetCountByOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getAssetCountByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> assetAllocationVO = null;
		try {
			assetAllocationVO = assetManagementService.getAssetCountByOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "AssetCount found by ORGID");
			responseObjectsMap.put("assetAllocationVO", assetAllocationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "AssetCount not found for orgID: " + orgId;
			responseDTO = createServiceResponseError(responseObjectsMap, "AssetCount not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("getAssetDashboardByOrgId")
	public ResponseEntity<ResponseDTO> getAssetDashboardByOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getAssetDashboardByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> assetAllocationVO = null;
		try {
			assetAllocationVO = assetManagementService.getAssetDashboardByOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "AssetCount found by ORGID");
			responseObjectsMap.put("assetAllocationVO", assetAllocationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "AssetCount not found for orgID: " + orgId;
			responseDTO = createServiceResponseError(responseObjectsMap, "AssetCount not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	//ExpenseClaims
	
	@PutMapping("/CreateUpdateExpenseClaims")
	public ResponseEntity<ResponseDTO> CreateUpdateExpenseClaims(@RequestBody ExpenseClaimsDTO expenseClaimsDTO) {
		String methodName = "CreateUpdateExpenseClaims()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		Map<String, Object> responseObjectsMap = new HashMap<String, Object>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> expenseClaimsVO = assetManagementService.CreateUpdateExpenseClaims(expenseClaimsDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, expenseClaimsVO.get("message"));
			responseObjectsMap.put("expenseClaimsVO", expenseClaimsVO.get("expenseClaimsVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("getExpenseClaimsByOrgId")
	public ResponseEntity<ResponseDTO> getExpenseClaimsByOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getExpenseClaimsByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ExpenseClaimsVO> expenseClaimsVO = null;
		try {
			expenseClaimsVO = assetManagementService.getExpenseClaimsByOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ExpenseClaims found by ORGID");
			responseObjectsMap.put("expenseClaimsVO", expenseClaimsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "ExpenseClaims not found for orgID: " + orgId;
			responseDTO = createServiceResponseError(responseObjectsMap, "ExpenseClaims not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("getExpenseClaimsById")
	public ResponseEntity<ResponseDTO> getExpenseClaimsById(@RequestParam Long id) {
		String methodName = "getExpenseClaimsById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		ExpenseClaimsVO expenseClaimsVO = null;
		try {
			expenseClaimsVO = assetManagementService.getExpenseClaimsById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ExpenseClaims found by ID");
			responseObjectsMap.put("expenseClaimsVO", expenseClaimsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "ExpenseClaims not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "ExpenseClaims not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	//TravelRequests
	
	@PutMapping("/CreateUpdateTravelRequests")
	public ResponseEntity<ResponseDTO> CreateUpdateTravelRequests(@RequestBody TravelRequestsDTO travelRequestsDTO) {
		String methodName = "CreateUpdateTravelRequests()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		Map<String, Object> responseObjectsMap = new HashMap<String, Object>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> travelRequestsVO = assetManagementService.CreateUpdateTravelRequests(travelRequestsDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, travelRequestsVO.get("message"));
			responseObjectsMap.put("travelRequestsVO", travelRequestsVO.get("travelRequestsVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("getTravelRequestsByOrgId")
	public ResponseEntity<ResponseDTO> getTravelRequestsByOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getTravelRequestsByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<TravelRequestsVO> travelRequestsVO = null;
		try {
			travelRequestsVO = assetManagementService.getTravelRequestsByOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "TravelRequests found by ORGID");
			responseObjectsMap.put("travelRequestsVO", travelRequestsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "TravelRequests not found for orgID: " + orgId;
			responseDTO = createServiceResponseError(responseObjectsMap, "TravelRequests not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("getTravelRequestsById")
	public ResponseEntity<ResponseDTO> getTravelRequestsById(@RequestParam Long id) {
		String methodName = "getTravelRequestsById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		TravelRequestsVO travelRequestsVO = null;
		try {
			travelRequestsVO = assetManagementService.getTravelRequestsById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "TravelRequests found by ID");
			responseObjectsMap.put("travelRequestsVO", travelRequestsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "TravelRequests not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "TravelRequests not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	
}
