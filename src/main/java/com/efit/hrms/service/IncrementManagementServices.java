package com.efit.hrms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efit.hrms.dto.IncrementManagementDTO;
import com.efit.hrms.entity.IncrementManagementVO;
import com.efit.hrms.entity.SalaryStructureVO;
import com.efit.hrms.exception.ApplicationException;

@Service
public interface IncrementManagementServices {

	Map<String, Object> createUpdateIncrementManagement(IncrementManagementDTO incrementManagementDTO) throws ApplicationException;

	Map<String, Object> createApprovalIncrementManagement(Long orgId, Long id, String employeeCode, String action,
			String actionBy, String notifyCode, String notify, String screenName, String email) throws ApplicationException, Exception;

	List<IncrementManagementVO> getIncrementManagementForDashBoard(Long orgId, String reportingPersonCode,
			String branchCode);

	List<SalaryStructureVO> getLatestSalaryStructureByOrgId(Long orgId, String employeeCode);

	List<Map<String, Object>> getSalaryHistoryforIncrement(Long orgId, String employeeCode);

}
