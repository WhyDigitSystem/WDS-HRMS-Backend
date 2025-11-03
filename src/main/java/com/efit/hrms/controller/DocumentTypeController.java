package com.efit.hrms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.efit.hrms.dto.DocTypeDTO;
import com.efit.hrms.dto.DocTypeMappingDTO;
import com.efit.hrms.dto.ResponseDTO;
import com.efit.hrms.entity.DocTypeMappingVO;
import com.efit.hrms.entity.DocTypeVO;
import com.efit.hrms.entity.ExpenseClaimsVO;
import com.efit.hrms.exception.ApplicationException;
import com.efit.hrms.service.DocumentTypeService;

@CrossOrigin
@RestController
@RequestMapping("/api/documenttypecontroller")
public class DocumentTypeController  extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(BasicMasterController.class);

	@Autowired
	DocumentTypeService documentTypeService;
	
	@PutMapping("/createDocType")
	public ResponseEntity<ResponseDTO> createDocType(@RequestBody DocTypeDTO docTypeDTO) throws ApplicationException {
	    String methodName = "createDocType()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        // ✅ Call service method
	        DocTypeVO docTypeVO = documentTypeService.createDocType(docTypeDTO);

	        // ✅ Add success message and data to response map
	        responseObjectsMap.put("docTypeVO", docTypeVO);
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DocType created successfully.");

	        // ✅ Create success response
	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (ApplicationException ae) {
	        LOGGER.error("{} - Business Error: {}", methodName, ae.getMessage(), ae);
	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Business Error",
	                ae.getMessage()
	        );
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDTO);

	    } catch (Exception e) {
	        LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Unexpected Error",
	                "Something went wrong while creating DocType."
	        );
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok(responseDTO);
	}
	
	
	@PutMapping("/createUpdateDocTypeMapping")
	public ResponseEntity<ResponseDTO> createUpdateDocTypeMapping(@RequestBody DocTypeMappingDTO docTypeMappingDTO)
	        throws ApplicationException {
	    String methodName = "createUpdateDocTypeMapping()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        // Call service method
	        DocTypeMappingVO docTypeMappingVO = documentTypeService.createDocTypeMappingVO(docTypeMappingDTO);

	        // Build response map
	        responseObjectsMap.put("docTypeMappingVO", docTypeMappingVO);
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Document Type Mapping saved successfully.");

	        // Create structured response
	        responseDTO = createServiceResponse(responseObjectsMap);

	        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	        return ResponseEntity.ok(responseDTO);

	    } catch (Exception e) {
	        LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);
	        responseDTO = createServiceResponseError(responseObjectsMap, "Unexpected Error", "Something went wrong.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
	    }
	}
	
	
	@GetMapping("getPendingDocTypeMapping")
	public ResponseEntity<ResponseDTO> getPendingDocTypeMapping(@RequestParam String branch,@RequestParam String branchCode) {
		String methodName = "getPendingDocTypeMapping()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> docTypeMappingVO = null;
		try {
			docTypeMappingVO = documentTypeService.getPendingDocTypeMapping(branch,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DocTypeMapping found by branch");
			responseObjectsMap.put("docTypeMappingVO", docTypeMappingVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "DocTypeMapping not found for branch: " +branch;
			responseDTO = createServiceResponseError(responseObjectsMap, "DocTypeMapping not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	

	@GetMapping("getDocId")
	public ResponseEntity<ResponseDTO> getDocId(@RequestParam String branchCode, @RequestParam String screenCode) {
	    String methodName = "getDocId()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    String generatedDocId = null;

	    try {
	        generatedDocId = documentTypeService.getDocid(branchCode, screenCode);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	    }

	    if (StringUtils.isEmpty(errorMsg)) {
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Document ID generated successfully");
	        responseObjectsMap.put("generatedDocId", generatedDocId);
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } else {
	        errorMsg = "Unable to generate Document ID for branch: " + branchCode;
	        responseDTO = createServiceResponseError(responseObjectsMap, "Document ID generation failed", errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}



}
