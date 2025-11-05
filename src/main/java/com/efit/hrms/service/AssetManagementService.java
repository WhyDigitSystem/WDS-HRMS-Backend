package com.efit.hrms.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efit.hrms.dto.AssetAllocationDTO;
import com.efit.hrms.dto.AssetMasterDTO;
import com.efit.hrms.dto.ExpenseClaimsDTO;
import com.efit.hrms.dto.TravelRequestsDTO;
import com.efit.hrms.entity.AssetAllocationVO;
import com.efit.hrms.entity.AssetMasterVO;
import com.efit.hrms.entity.ExpenseClaimsVO;
import com.efit.hrms.entity.TravelRequestsVO;
import com.efit.hrms.exception.ApplicationException;

@Service
public interface AssetManagementService {

	Map<String, Object> CreateUpdateAssetMaster(AssetMasterDTO assetMasterDTO) throws ApplicationException;

	AssetMasterVO getAssetMasterById(Long id);

//	List<AssetMasterVO> getAssetMasterByOrgId(Long orgId);

	List<AssetMasterVO> getAssetMasterByOrgId(Long orgId, String branchCode);

	Map<String, Object> CreateUpdateAssetAllocation(AssetAllocationDTO assetAllocationDTO) throws ApplicationException;

	List<Map<String, Object>> getAssetNameCodeByOrgId(Long orgId, String branchCode);

	List<AssetAllocationVO> getAssetAllocationByOrgId(Long orgId, String branchCode);

	AssetAllocationVO getAssetAllocationById(Long id);

	List<Map<String, Object>> getAssetCountByOrgId(Long orgId, String branchCode);

	List<Map<String, Object>> getAssetDashboardByOrgId(Long orgId, String branchCode);

	Map<String, Object> uploadMultipleAssetImages(Long assetMasterId, List<MultipartFile> files, String createdBy) throws IOException, ApplicationException, GeneralSecurityException;

	
	//expenseClaims
	
	Map<String, Object> CreateUpdateExpenseClaims(ExpenseClaimsDTO expenseClaimsDTO) throws ApplicationException;

	List<ExpenseClaimsVO> getExpenseClaimsByOrgId(Long orgId, String branchCode, String employeeCode);

	ExpenseClaimsVO getExpenseClaimsById(Long id);	
	
	//TravelRequests
	
	Map<String, Object> CreateUpdateTravelRequests(TravelRequestsDTO travelRequestsDTO) throws ApplicationException;

	List<TravelRequestsVO> getTravelRequestsByOrgId(Long orgId, String branchCode, String employeeCode);

	TravelRequestsVO getTravelRequestsById(Long id);

	Map<String, Object> createApprovalExpenseClaims(Long orgId, Long id, String employeeCode, String action,
			String actionBy, String notifyCode, String notify, String screenName, String email, BigDecimal approvedAmount) throws Exception;

	Map<String, Object> createApprovalTravelRequests(Long orgId, Long id, String employeeCode, String action,
			String actionBy, String notifyCode, String notify, String screenName, String email, BigDecimal approvedAmount) throws Exception;

	List<TravelRequestsVO> getTravelRequestsForDashBoard(Long orgId, String reportingPersonCode, String branchCode);

	List<ExpenseClaimsVO> getExpenseClaimsForDashBoard(Long orgId, String reportingPersonCode, String branchCode);

	ExpenseClaimsVO uploadExpenseClaimsImageInBloob(MultipartFile file, Long id) throws IOException, IOException;

	List<Map<String, Object>> getApprovalExpenseAndTravelByOrgId(Long orgId, String branchCode, String employeeCode);

	List<Map<String, Object>> getAssetAllocationReportByOrgId(Long orgId, String branchCode, String employeeCode);

	List<Map<String, Object>> getExpenseCountByOrgId(Long orgId, String branchCode, String employeeCode);

	
	
	



}
