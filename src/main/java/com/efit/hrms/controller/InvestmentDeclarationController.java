package com.efit.hrms.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.efit.hrms.dto.InvestmentDeclarationDTO;
import com.efit.hrms.dto.ResponseDTO;
import com.efit.hrms.entity.InvestmentDeclarationDetailsVO;
import com.efit.hrms.entity.InvestmentDeclarationVO;
import com.efit.hrms.repo.InvestmentDeclarationDetailsRepo;
import com.efit.hrms.service.InvestmentDeclarationService;

@RestController
@RequestMapping("/api/investmentDeclaration")
public class InvestmentDeclarationController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(InvestmentDeclarationController.class);

	@Autowired
	InvestmentDeclarationService investmentDeclarationService;

	@Autowired
	private InvestmentDeclarationDetailsRepo investmentDeclarationDetailsRepo;

	// TaxInvoice

	@GetMapping("/getInvestmentDeclarationDetails")
	public ResponseEntity<ResponseDTO> getInvestmentDeclarationDetails(@RequestParam Long orgId,
			@RequestParam String branch, @RequestParam String employeeCode) {
		String methodName = "getInvestmentDeclarationDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<InvestmentDeclarationVO> investmentDeclarationVO = new ArrayList<>();
		try {
			investmentDeclarationVO = investmentDeclarationService.getInvestmentDeclarationDetails(orgId, branch,
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

//	@PostMapping("/uploadImageInvestmentDeclarationDetails")
//	public ResponseEntity<ResponseDTO> uploadImageInvestmentDeclarationDetails(@RequestParam List<MultipartFile> file,
//			@RequestParam Long investmentDeclarationId, @RequestParam List<Long> investmentDeclarationDetailsId) {
//
//		String methodName = "uploadImageInvestmentDeclarationDetails()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO;
//		String errorMsg = null;
//
//		try {
//			String investmentDeclarationVO = investmentDeclarationService.uploadImageInvestmentDeclarationDetails(file,
//					investmentDeclarationId, investmentDeclarationDetailsId);
//
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "InvestmentDeclaration Successfully Uploaded");
//			responseObjectsMap.put("investmentDeclarationVO", investmentDeclarationVO);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error("Unable To Upload PartImage", methodName, errorMsg);
//			responseDTO = createServiceResponseError(responseObjectsMap, "InvestmentDeclaration Upload Failed",
//					errorMsg);
//		}
//
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}

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

	@GetMapping("/getDashBoardDetailsOldRegime")
	public ResponseEntity<ResponseDTO> getDashBoardDetailsOldRegime(@RequestParam Long orgId,
			@RequestParam String branch, @RequestParam String employeeCode) {
		String methodName = "getDashBoardDetailsNew()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> dashBoardDetails = new ArrayList<>();
		try {
			dashBoardDetails = investmentDeclarationService.getDashBoardDetailsOldRegime(orgId, branch, employeeCode);
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

	@GetMapping("/getTdsSummaryDetails")
	public ResponseEntity<ResponseDTO> getTdsSummaryDetails(@RequestParam Long orgId, @RequestParam String branch,
			@RequestParam String employeeCode) {
		String methodName = "getTdsSummaryDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> dashBoardDetails = new ArrayList<>();
		try {
			dashBoardDetails = investmentDeclarationService.getTdsSummaryDetails(orgId, branch, employeeCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "TdsSummary information get successfully");
			responseObjectsMap.put("dashBoardDetails", dashBoardDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "TdsSummary information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getTaxRegimeComparisonDetails")
	public ResponseEntity<ResponseDTO> getTaxRegimeComparisonDetails(@RequestParam Long orgId,
			@RequestParam String branch, @RequestParam String employeeCode) {
		String methodName = "getTaxRegimeComparisonDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> dashBoardDetails = new ArrayList<>();
		try {
			dashBoardDetails = investmentDeclarationService.getTaxRegimeComparisonDetails(orgId, branch, employeeCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"TaxRegimeComparisonDetails information get successfully");
			responseObjectsMap.put("dashBoardDetails", dashBoardDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"TaxRegimeComparisonDetails information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/viewInvestmentImage/{detailId}")
	public ResponseEntity<Resource> viewInvestmentImage(@PathVariable Long detailId) throws IOException {

		InvestmentDeclarationDetailsVO detail = investmentDeclarationDetailsRepo.findById(detailId)
				.orElseThrow(() -> new RuntimeException("Image not found"));

		Resource resource = investmentDeclarationService.viewInvestmentImage(detailId);

		Path path = Paths.get(detail.getFilePath());

		String contentType = Files.probeContentType(path);

		if (contentType == null) {
			contentType = "image/jpeg";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
	}



	@PostMapping("/uploadImageInvestmentDeclarationDetails")
	public ResponseEntity<ResponseDTO> uploadImageInvestmentDeclarationDetails(

			@RequestParam List<MultipartFile> files,

			@RequestParam Long investmentDeclarationId,

			@RequestParam List<Long> investmentDeclarationDetailsId) {

		String methodName = "uploadImageInvestmentDeclarationDetails()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			Map<String, Object> response = investmentDeclarationService.uploadImageInvestmentDeclarationDetails(files,
					investmentDeclarationId, investmentDeclarationDetailsId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Investment Declaration Uploaded Successfully");

			responseObjectsMap.put("response", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, "Investment Declaration Upload Failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/viewFile/**")
	public ResponseEntity<byte[]> viewFileInvestment(HttpServletRequest request) throws IOException {

		return investmentDeclarationService.viewFileInvestment(request);
	}

}
