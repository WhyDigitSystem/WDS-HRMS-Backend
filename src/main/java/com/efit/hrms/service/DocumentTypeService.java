package com.efit.hrms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efit.hrms.dto.DocTypeDTO;
import com.efit.hrms.dto.DocTypeMappingDTO;
import com.efit.hrms.entity.DocTypeMappingVO;
import com.efit.hrms.entity.DocTypeVO;
import com.efit.hrms.exception.ApplicationException;

@Service
public interface DocumentTypeService {

	DocTypeVO createDocType(DocTypeDTO docTypeDTO) throws ApplicationException;


	DocTypeMappingVO createDocTypeMappingVO(DocTypeMappingDTO docTypeMappingDTO) throws ApplicationException;


	List<Map<String, Object>> getPendingDocTypeMapping(String branch, String branchCode)
			throws NumberFormatException, ApplicationException;


	String getDocid(String branchCode, String screenCode) throws ApplicationException;


//	Map<String, Object> createUpdateDocType(DocTypeDTO docTypeDTO) throws ApplicationException;

}
