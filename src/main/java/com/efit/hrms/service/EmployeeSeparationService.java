package com.efit.hrms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efit.hrms.dto.InitiateSeparationDTO;
import com.efit.hrms.entity.InitiateSeparationVO;
import com.efit.hrms.exception.ApplicationException;

@Service
public interface EmployeeSeparationService {

	Map<String, Object> createUpdateInitiateSeparation(InitiateSeparationDTO initiateSeparationDTO) throws ApplicationException;

	InitiateSeparationVO getInitiateSeparationById(Long id);

	List<InitiateSeparationVO> getInitiateSeparationByOrgId(Long orgId, String branchCode);


	List<InitiateSeparationVO> getInitiateSeparationByDepartment(Long orgId, String branchCode, String department,
			String type, String empCode);

	List<Map<String, Object>> getInitiateSeparationCountByOrgId(Long orgId, String branchCode);

	List<InitiateSeparationVO> getInitiateSeparationByOrgIdforclearance(Long orgId, String branchCode, String empCode);

	String updateSeparationStatus(Long id, String string);

}
