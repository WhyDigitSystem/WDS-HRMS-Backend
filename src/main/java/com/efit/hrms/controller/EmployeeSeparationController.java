package com.efit.hrms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.efit.hrms.common.CommonConstant;
import com.efit.hrms.common.UserConstants;
import com.efit.hrms.dto.InitiateSeparationDTO;
import com.efit.hrms.dto.ResponseDTO;
import com.efit.hrms.entity.InitiateSeparationVO;
import com.efit.hrms.service.EmployeeSeparationService;


@CrossOrigin
@RestController
@RequestMapping("/api/employeseparation")
public class EmployeeSeparationController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(BasicMasterController.class);	

	@Autowired
	EmployeeSeparationService employeeSeparationService;
	
	@PutMapping("/createUpdateInitiateSeparation")
	public ResponseEntity<ResponseDTO> createUpdateInitiateSeparation(
	        @RequestBody InitiateSeparationDTO initiateSeparationDTO) {

	    String methodName = "createUpdateEmployeeSeparation()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        Map<String, Object> serviceResponse =
	                employeeSeparationService.createUpdateInitiateSeparation(initiateSeparationDTO);

	        // ✅ Correctly extract paramObjectsMap
	        Map<String, Object> paramObjectsMap = (Map<String, Object>) serviceResponse.get("paramObjectsMap");

	        responseObjectsMap.put("message", paramObjectsMap.get("message"));
	        responseObjectsMap.put("initiateSeparationVO", paramObjectsMap.get("initiateSeparationVO"));

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {
	        String errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}

	
	@GetMapping("getInitiateSeparationById")
	public ResponseEntity<ResponseDTO> getInitiateSeparationById(@RequestParam Long id) {
		String methodName = "getInitiateSeparationById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		InitiateSeparationVO initiateSeparationVO = null;
		try {
			initiateSeparationVO = employeeSeparationService.getInitiateSeparationById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "InitiateSeparation found by ID");
			responseObjectsMap.put("initiateSeparationVO", initiateSeparationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "InitiateSeparation not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "InitiateSeparation not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("getInitiateSeparationByOrgId")
	public ResponseEntity<ResponseDTO> getInitiateSeparationByOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getInitiateSeparationByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<InitiateSeparationVO> initiateSeparationVO = null;
		try {
			initiateSeparationVO = employeeSeparationService.getInitiateSeparationByOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "InitiateSeparation found by ORGID");
			responseObjectsMap.put("initiateSeparationVO", initiateSeparationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "InitiateSeparation not found for orgID: " + orgId;
			responseDTO = createServiceResponseError(responseObjectsMap, "InitiateSeparation not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("getInitiateSeparationByDepartment")
	public ResponseEntity<ResponseDTO> getInitiateSeparationByDepartment(@RequestParam Long orgId,@RequestParam String branchCode,@RequestParam String department,@RequestParam String type,@RequestParam String empCode) {
		String methodName = "getInitiateSeparationByDepartment()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<InitiateSeparationVO> initiateSeparationVO = null;
		try {
			initiateSeparationVO = employeeSeparationService.getInitiateSeparationByDepartment(orgId,branchCode,department,type,empCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "InitiateSeparation found by Department");
			responseObjectsMap.put("initiateSeparationVO", initiateSeparationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "InitiateSeparation not found for Department: " + department;
			responseDTO = createServiceResponseError(responseObjectsMap, "InitiateSeparation not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("getInitiateSeparationCountByOrgId")
	public ResponseEntity<ResponseDTO> getInitiateSeparationCountByOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getInitiateSeparationCountByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> initiateSeparationVO = null;
		try {
			initiateSeparationVO = employeeSeparationService.getInitiateSeparationCountByOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "InitiateSeparation found by ORGID");
			responseObjectsMap.put("initiateSeparationVO", initiateSeparationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "InitiateSeparation not found for orgID: " + orgId;
			responseDTO = createServiceResponseError(responseObjectsMap, "InitiateSeparation not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	@GetMapping("getInitiateSeparationByOrgIdforclearance")
	public ResponseEntity<ResponseDTO> getInitiateSeparationByOrgIdforclearance(@RequestParam Long orgId,@RequestParam String branchCode,@RequestParam String empCode) {
		String methodName = "getInitiateSeparationByOrgIdforclearance()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<InitiateSeparationVO> initiateSeparationVO = null;
		try {
			initiateSeparationVO = employeeSeparationService.getInitiateSeparationByOrgIdforclearance(orgId,branchCode,empCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "InitiateSeparation found by ORGID");
			responseObjectsMap.put("initiateSeparationVO", initiateSeparationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "InitiateSeparation not found for orgID: " + orgId;
			responseDTO = createServiceResponseError(responseObjectsMap, "InitiateSeparation not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/approve/{id}")
	public ModelAndView approveSeparation(@PathVariable Long id) {

	    String message = employeeSeparationService.updateSeparationStatus(id,"APPROVED");

	    ModelAndView mv = new ModelAndView("separation_approved.html");
	    mv.addObject("message", message);

	    return mv;
	}
	
	@GetMapping("/reject/{id}")
	public ModelAndView rejectSeparation(@PathVariable Long id) {

	    String message = employeeSeparationService.updateSeparationStatus(id,"REJECTED");

	    ModelAndView mv = new ModelAndView("separation_approved.html");
	    mv.addObject("message", message);

	    return mv;
	}
	 
//	@GetMapping("/approve/{id}")
//	public ModelAndView approveSeparation(@PathVariable Long id) {
//
//	    String message = employeeSeparationService.updateSeparationStatus(id,"APPROVED");
//
//	    ModelAndView mv = new ModelAndView();
//	    mv.setViewName("forward:/separation_result.html");
//	    mv.addObject("message", message);
//
//	    return mv;
//	}
//
//	@GetMapping("/reject/{id}")
//	public ModelAndView rejectSeparation(@PathVariable Long id) {
//
//	    String message = employeeSeparationService.updateSeparationStatus(id,"REJECTED");
//
//	    ModelAndView mv = new ModelAndView();
//	    mv.setViewName("forward:/separation_result.html");
//	    mv.addObject("message", message);
//
//	    return mv;
//	}
	
}
