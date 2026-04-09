package com.efit.hrms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.springframework.web.servlet.ModelAndView;

import com.efit.hrms.common.CommonConstant;
import com.efit.hrms.common.UserConstants;
import com.efit.hrms.dto.DepartmentHeadDTO;
import com.efit.hrms.dto.ExitInterviewDepartmentDTO;
import com.efit.hrms.dto.InitiateSeparationDTO;
import com.efit.hrms.dto.ResponseDTO;
import com.efit.hrms.entity.DepartmentHeadVO;
import com.efit.hrms.entity.ExitInterviewDepartmentVO;
import com.efit.hrms.entity.InitiateSeparationVO;
import com.efit.hrms.service.EmployeeSeparationService;
import com.efit.hrms.service.SeparationMailService;


@CrossOrigin
@RestController
@RequestMapping("/api/employeseparation")
public class EmployeeSeparationController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(BasicMasterController.class);	

	@Autowired
	EmployeeSeparationService employeeSeparationService;
	
	@Autowired
	SeparationMailService separationMailService;
	
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
	@GetMapping("getGeneralManagerByOrgId")
	public ResponseEntity<ResponseDTO> getGeneralManagerByOrgId(@RequestParam Long orgId) {
		String methodName = "getGeneralManagerByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> employeeVO = null;
		try {
			employeeVO = employeeSeparationService.getGeneralManagerByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Employee found by ORGID");
			responseObjectsMap.put("employeeVO", employeeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Employee not found for orgID: " + orgId;
			responseDTO = createServiceResponseError(responseObjectsMap, "Employee not found", errorMsg);
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
	
//	@GetMapping("/approve/{id}")
//	public ModelAndView approveSeparation(@PathVariable Long id) {
//
//	    String message = employeeSeparationService.updateSeparationStatus(id,"APPROVED");
//
////	    ModelAndView mv = new ModelAndView("separation_approved.html");
////	    mv.addObject("message", message);
//
//	    return "Approved Successfully";
//	}
	
	@GetMapping("/approve/{id}")
	public ResponseEntity<ResponseDTO> approveSeparation(@PathVariable Long id) {

	    String methodName = "approveSeparation()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;

	    try {
	        String message = employeeSeparationService.updateSeparationStatus(id, "APPROVED");

	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, message);
	        responseObjectsMap.put("status", "APPROVED");

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	    }

	    if (!StringUtils.isEmpty(errorMsg)) {
	        errorMsg = "Approval failed for ID: " + id;

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Approval Failed",
	                errorMsg
	        );
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
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
	
	@PostMapping("/sendExperienceLetter")
	public ResponseEntity<ResponseDTO> sendExperienceLetter(
	        @RequestParam("files") MultipartFile file,
	        @RequestParam String employeeEmail,
	        @RequestParam String employeeName) {

	    String methodName = "sendExperienceLetter()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        separationMailService.sendExperienceLetter(employeeEmail, employeeName, file);

	        Map<String, Object> paramObjectsMap = new HashMap<>();
	        paramObjectsMap.put("message", "Experience Letter Sent Successfully");
	        paramObjectsMap.put("employeeEmail", employeeEmail);

	        responseObjectsMap.put("paramObjectsMap", paramObjectsMap);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        String errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	  
	  @PostMapping("/sendRelievingLetter")
	  public ResponseEntity<ResponseDTO> sendRelievingLetter(
	          @RequestParam("files") MultipartFile file,
	          @RequestParam String employeeEmail,
	          @RequestParam String employeeName) {

	      String methodName = "sendRelievingLetter()";
	      LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	      Map<String, Object> responseObjectsMap = new HashMap<>();
	      ResponseDTO responseDTO;

	      try {

	          separationMailService.sendRelievingLetter(employeeEmail, employeeName, file);

	          Map<String, Object> paramObjectsMap = new HashMap<>();
	          paramObjectsMap.put("message", "Relieving Letter Sent Successfully");
	          paramObjectsMap.put("employeeEmail", employeeEmail);

	          responseObjectsMap.put("paramObjectsMap", paramObjectsMap);

	          responseDTO = createServiceResponse(responseObjectsMap);

	      } catch (Exception e) {

	          String errorMsg = e.getMessage();
	          LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	          responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	      }

	      LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	      return ResponseEntity.ok().body(responseDTO);
	  }
	

		@PutMapping("/createUpdateExitInterviewQuestion")
		public ResponseEntity<ResponseDTO> createUpdateExitInterviewQuestion(
		        @RequestBody ExitInterviewDepartmentDTO exitInterviewDepartmentDTO) {

		    String methodName = "createUpdateExitInterviewQuestion()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		        Map<String, Object> serviceResponse =
		                employeeSeparationService.createUpdateExitInterviewQuestion(exitInterviewDepartmentDTO);

		        // ✅ Correctly extract paramObjectsMap
		        Map<String, Object> paramObjectsMap = (Map<String, Object>) serviceResponse.get("paramObjectsMap");

		        responseObjectsMap.put("message", paramObjectsMap.get("message"));
		        responseObjectsMap.put("exitInterviewDepartmentVO", paramObjectsMap.get("exitInterviewDepartmentVO"));

		        responseDTO = createServiceResponse(responseObjectsMap);

		    } catch (Exception e) {
		        String errorMsg = e.getMessage();
		        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		    }

		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		    return ResponseEntity.ok().body(responseDTO);
		}
		
		@GetMapping("getExitInterviewDepartmentById")
		public ResponseEntity<ResponseDTO> getExitInterviewDepartmentById(@RequestParam Long id) {
			String methodName = "getExitInterviewDepartmentById()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			ExitInterviewDepartmentVO exitInterviewDepartmentVO = null;
			try {
				exitInterviewDepartmentVO = employeeSeparationService.getExitInterviewDepartmentById(id);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "exitInterviewDepartmentVO found by ID");
				responseObjectsMap.put("exitInterviewDepartmentVO", exitInterviewDepartmentVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "exitInterviewDepartmentVO not found for ID: " + id;
				responseDTO = createServiceResponseError(responseObjectsMap, "exitInterviewDepartmentVO not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		
		@GetMapping("getExitInterviewDepartmentVOByOrgId")
		public ResponseEntity<ResponseDTO> getExitInterviewDepartmentVOByOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
			String methodName = "getExitInterviewDepartmentVOByOrgId()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<ExitInterviewDepartmentVO> exitInterviewDepartmentVO = null;
			try {
				exitInterviewDepartmentVO = employeeSeparationService.getExitInterviewDepartmentVOByOrgId(orgId,branchCode);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "exitInterviewDepartment found by ORGID");
				responseObjectsMap.put("exitInterviewDepartmentVO", exitInterviewDepartmentVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "exitInterviewDepartment not found for orgID: " + orgId;
				responseDTO = createServiceResponseError(responseObjectsMap, "exitInterviewDepartment not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		
		@GetMapping("getExitInterviewBasedOnDesignation")
		public ResponseEntity<ResponseDTO> getExitInterviewBasedOnDesignation(@RequestParam Long orgId,@RequestParam String branchCode,@RequestParam String designation) {
			String methodName = "getExitInterviewBasedOnDesignation()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<ExitInterviewDepartmentVO> exitInterviewDepartmentVO = null;
			try {
				exitInterviewDepartmentVO = employeeSeparationService.getExitInterviewBasedOnDesignation(orgId,branchCode,designation);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "exitInterviewDepartment found by Department");
				responseObjectsMap.put("exitInterviewDepartmentVO", exitInterviewDepartmentVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "exitInterviewDepartment not found for orgID: " + orgId;
				responseDTO = createServiceResponseError(responseObjectsMap, "exitInterviewDepartment not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		
		@PostMapping("/uploadExitInterviewExcel")
		public ResponseEntity<Map<String, Object>> uploadExitInterviewExcel(
		        @RequestParam("files") MultipartFile file,
		        @RequestParam String branch,
		        @RequestParam String branchCode,
		        @RequestParam Long orgId,
		        @RequestParam String createdBy) throws Exception {

		    Map<String, Object> response = new LinkedHashMap<>();

		    List<ExitInterviewDepartmentVO> savedList =
		            employeeSeparationService.uploadExitInterviewExcel(
		                    file, branch, branchCode, orgId, createdBy);

		    response.put("statusFlag", "Ok");
		    response.put("status", true);

		    Map<String, Object> paramObjectsMap = new LinkedHashMap<>();
		    paramObjectsMap.put("message", "Excel Uploaded & Saved Successfully");
		    paramObjectsMap.put("data", savedList);

		    response.put("paramObjectsMap", paramObjectsMap);

		    return ResponseEntity.ok(response);
		}
	 
		
		@PutMapping("/createUpdateDepartmentHead")
		public ResponseEntity<ResponseDTO> createUpdateDepartmentHead(
		        @RequestBody DepartmentHeadDTO departmentHeadDTO) {

		    String methodName = "createUpdateDepartmentHead()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {
		        Map<String, Object> serviceResponse =
		                employeeSeparationService.createUpdateDepartmentHeadDTO(departmentHeadDTO);

		        // ✅ Correctly extract paramObjectsMap
		        Map<String, Object> paramObjectsMap = (Map<String, Object>) serviceResponse.get("paramObjectsMap");

		        responseObjectsMap.put("message", paramObjectsMap.get("message"));
		        responseObjectsMap.put("departmentHeadVO", paramObjectsMap.get("departmentHeadVO"));

		        responseDTO = createServiceResponse(responseObjectsMap);

		    } catch (Exception e) {
		        String errorMsg = e.getMessage();
		        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		    }

		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		    return ResponseEntity.ok().body(responseDTO);
		}
		
		@GetMapping("getEmployeeforDepartmentHeadByOrgId")
		public ResponseEntity<ResponseDTO> getEmployeeforDepartmentHeadByOrgId(@RequestParam Long orgId,@RequestParam String department,@RequestParam String branchCode) {
			String methodName = "getEmployeeforDepartmentHeadByOrgId()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<Map<String, Object>> employeeVO = null;
			try {
				employeeVO = employeeSeparationService.getEmployeeforDepartmentHeadByOrgId(orgId,department,branchCode);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Employee found by ORGID");
				responseObjectsMap.put("employeeVO", employeeVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Employee not found for orgID: " + orgId;
				responseDTO = createServiceResponseError(responseObjectsMap, "Employee not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		@GetMapping("getDepartmentHeadById")
		public ResponseEntity<ResponseDTO> getDepartmentHeadById(@RequestParam Long id) {
			String methodName = "getDepartmentHeadById()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			DepartmentHeadVO departmentHeadVO = null;
			try {
				departmentHeadVO = employeeSeparationService.getDepartmentHeadById(id);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "departmentHeadVO found by ID");
				responseObjectsMap.put("departmentHeadVO", departmentHeadVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "departmentHeadVO not found for ID: " + id;
				responseDTO = createServiceResponseError(responseObjectsMap, "departmentHeadVO not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		
		@GetMapping("getDepartmentHeadByOrgId")
		public ResponseEntity<ResponseDTO> getDepartmentHeadByOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
			String methodName = "getDepartmentHeadByOrgId()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<DepartmentHeadVO> departmentHeadVO = null;
			try {
				departmentHeadVO = employeeSeparationService.getDepartmentHeadByOrgId(orgId,branchCode);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "departmentHeadVO found by ORGID");
				responseObjectsMap.put("departmentHeadVO", departmentHeadVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "departmentHeadVO not found for orgID: " + orgId;
				responseDTO = createServiceResponseError(responseObjectsMap, "departmentHeadVO not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		@GetMapping("getCleranceDetailsByEmployeeCode")
		public ResponseEntity<ResponseDTO> getCleranceDetailsByEmployeeCode(@RequestParam String employeeCode,@RequestParam Long orgId,@RequestParam String branchCode) {
			String methodName = "getCleranceDetailsByEmployeeCode()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<Map<String, Object>> initiateSeparationVO = null;
			try {
				initiateSeparationVO = employeeSeparationService.getCleranceDetailsByEmployeeCode(employeeCode,orgId,branchCode);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Clearance found by ORGID");
				responseObjectsMap.put("initiateSeparationVO", initiateSeparationVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Clearance not found for orgID: " + orgId;
				responseDTO = createServiceResponseError(responseObjectsMap, "Clearance not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		@GetMapping("getAccessoriesByEmployeeCode")
		public ResponseEntity<ResponseDTO> getAccessoriesByEmployeeCode(@RequestParam String employeeCode,@RequestParam Long orgId,@RequestParam String department,@RequestParam String branchCode) {
			String methodName = "getAccessoriesByEmployeeCode()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<Map<String, Object>> assetAllocationVO = null;
			try {
				assetAllocationVO = employeeSeparationService.getAccessoriesByEmployeeCode(employeeCode,orgId,department,branchCode);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "assetAllocation found by ORGID");
				responseObjectsMap.put("assetAllocationVO", assetAllocationVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "assetAllocation not found for orgID: " + orgId;
				responseDTO = createServiceResponseError(responseObjectsMap, "assetAllocation not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		@GetMapping("getStatusForClearance")
		public ResponseEntity<ResponseDTO> getStatusForClearance(@RequestParam String employeeCode,@RequestParam Long orgId,@RequestParam String branchCode) {
			String methodName = "getStatusForClearance()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<Map<String, Object>> assetStatus = null;
			try {
				assetStatus = employeeSeparationService.getStatusForClearance(employeeCode,orgId,branchCode);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "assetStatus found by ORGID");
				responseObjectsMap.put("assetStatus", assetStatus);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "assetStatus not found for orgID: " + orgId;
				responseDTO = createServiceResponseError(responseObjectsMap, "assetStatus not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		@GetMapping("getAssetAllocationDetailsForClearance")
		public ResponseEntity<ResponseDTO> getAssetAllocationDetailsForClearance(@RequestParam String employeeCode,@RequestParam Long orgId,@RequestParam String branchCode,@RequestParam String department) {
			String methodName = "getAssetAllocationDetailsForClearance()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<Map<String, Object>> assetAllocation = null;
			try {
				assetAllocation = employeeSeparationService.getAssetAllocationDetailsForClearance(employeeCode,orgId,branchCode,department);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "assetAllocation found by ORGID");
				responseObjectsMap.put("assetAllocation", assetAllocation);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "assetAllocation not found for orgID: " + orgId;
				responseDTO = createServiceResponseError(responseObjectsMap, "assetAllocation not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		@GetMapping("getAssetReturnForClearance")
		public ResponseEntity<ResponseDTO> getAssetReturnForClearance(@RequestParam String employeeCode,@RequestParam Long orgId,@RequestParam String branchCode,@RequestParam String department) {
			String methodName = "getAssetReturnForClearance()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<Map<String, Object>> assetAllocation = null;
			try {
				assetAllocation = employeeSeparationService.getAssetReturnForClearance(employeeCode,orgId,branchCode,department);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "assetAllocation found by ORGID");
				responseObjectsMap.put("assetAllocation", assetAllocation);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "assetAllocation not found for orgID: " + orgId;
				responseDTO = createServiceResponseError(responseObjectsMap, "assetAllocation not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		@GetMapping("getAssetReturnDetailsForClearance")
		public ResponseEntity<ResponseDTO> getAssetReturnDetailsForClearance(@RequestParam String employeeCode,@RequestParam Long orgId,@RequestParam String branchCode,@RequestParam String department) {
			String methodName = "getAssetReturnDetailsForClearance()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<Map<String, Object>> assetReset = null;
			try {
				assetReset = employeeSeparationService.getAssetReturnForClearance(employeeCode,orgId,branchCode,department);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "assetReset found by ORGID");
				responseObjectsMap.put("assetReset", assetReset);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "assetReset not found for orgID: " + orgId;
				responseDTO = createServiceResponseError(responseObjectsMap, "assetReset not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		
		@GetMapping("/getSeparationEmployeeByOrgId")
		public ResponseEntity<ResponseDTO> getSeparationEmployeeByOrgId(@RequestParam Long orgId) {
			String methodName = "getSeparationEmployeeByOrgId()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<Map<String, Object>> employeeVO = new ArrayList<>();
			try {
				employeeVO = employeeSeparationService.getSeparationEmployeeByOrgId(orgId);
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
