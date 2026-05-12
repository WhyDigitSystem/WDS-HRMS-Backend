package com.efit.hrms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efit.hrms.dto.EmployeeDocumentsDTO;
import com.efit.hrms.entity.EmployeeDocumentsVO;
import com.efit.hrms.exception.ApplicationException;

import io.jsonwebtoken.io.IOException;

@Service
public interface EmployeeDocumentsService {
    EmployeeDocumentsVO uploadDocument(EmployeeDocumentsDTO dto) throws IOException, java.io.IOException;
//    List<EmployeeDocumentsVO> getDocumentsByEmployeeCode(String employeeCode);

	List<EmployeeDocumentsVO> getEmployeeDocumentsByEmpCodeAndOrgId(Long orgId, String employeeCode);
	
	void deleteEmployeeDocById(Long id) throws ApplicationException;

	List<EmployeeDocumentsVO> uploadDocuments(
	        String employeeCode,
	        String employeeName,
	        String documentName,
	        Long orgId,
	        List<MultipartFile> files) throws IOException, java.io.IOException;

}

