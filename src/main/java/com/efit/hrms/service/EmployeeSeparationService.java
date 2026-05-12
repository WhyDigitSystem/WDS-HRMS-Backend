package com.efit.hrms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efit.hrms.dto.DepartmentHeadDTO;
import com.efit.hrms.dto.ExitInterviewDepartmentDTO;
import com.efit.hrms.dto.InitiateSeparationDTO;
import com.efit.hrms.entity.DepartmentHeadVO;
import com.efit.hrms.entity.ExitInterviewDepartmentVO;
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

	List<Map<String, Object>> getGeneralManagerByOrgId(Long orgId);

	Map<String, Object> createUpdateExitInterviewQuestion(ExitInterviewDepartmentDTO exitInterviewDepartmentDTO) throws ApplicationException;

	ExitInterviewDepartmentVO getExitInterviewDepartmentById(Long id);

	List<ExitInterviewDepartmentVO> getExitInterviewDepartmentVOByOrgId(Long orgId, String branchCode);

	List<ExitInterviewDepartmentVO> getExitInterviewBasedOnDesignation(Long orgId, String branchCode,
			String designation);

	List<ExitInterviewDepartmentVO> uploadExitInterviewExcel(MultipartFile file, String branch, String branchCode, Long orgId, String createdBy) throws Exception;

	Map<String, Object> createUpdateDepartmentHeadDTO(DepartmentHeadDTO departmentHeadDTO) throws ApplicationException;

	List<Map<String, Object>> getEmployeeforDepartmentHeadByOrgId(Long orgId, String department, String branchCode);

	DepartmentHeadVO getDepartmentHeadById(Long id);

	List<DepartmentHeadVO> getDepartmentHeadByOrgId(Long orgId, String branchCode);

	List<Map<String, Object>> getCleranceDetailsByEmployeeCode(String employeeCode, Long orgId, String branchCode);

	List<Map<String, Object>> getAccessoriesByEmployeeCode(String employeeCode, Long orgId, String department,
			String branchCode);

	List<Map<String, Object>> getStatusForClearance(String employeeCode, Long orgId, String branchCode);

	List<Map<String, Object>> getAssetAllocationDetailsForClearance(String employeeCode, Long orgId, String branchCode,
			String department);

	List<Map<String, Object>> getAssetReturnForClearance(String employeeCode, Long orgId, String branchCode,
			String department);

	List<Map<String, Object>> getSeparationEmployeeByOrgId(Long orgId);



}
