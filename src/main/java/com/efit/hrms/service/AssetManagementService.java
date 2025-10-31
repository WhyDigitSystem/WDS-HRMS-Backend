package com.efit.hrms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efit.hrms.dto.AssetAllocationDTO;
import com.efit.hrms.dto.AssetMasterDTO;
import com.efit.hrms.entity.AssetAllocationVO;
import com.efit.hrms.entity.AssetMasterVO;
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



}
