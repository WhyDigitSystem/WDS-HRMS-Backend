package com.efit.hrms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efit.hrms.dto.AssetAllocationDTO;
import com.efit.hrms.dto.AssetMasterDTO;
import com.efit.hrms.entity.AssetAllocationVO;
import com.efit.hrms.entity.AssetMasterVO;
import com.efit.hrms.exception.ApplicationException;
import com.efit.hrms.repo.AssetAllocationRepo;
import com.efit.hrms.repo.AssetMasterRepo;

@Service
public class AssetManagementServiceImpl implements AssetManagementService{

	
	@Autowired
	AssetMasterRepo assetMasterRepo;
	
	@Autowired
	AssetAllocationRepo assetAllocationRepo;
	
	@Override
	public Map<String, Object> CreateUpdateAssetMaster(AssetMasterDTO assetMasterDTO) throws ApplicationException {

		AssetMasterVO assetMasterVO = new AssetMasterVO();
		String message;
		
		if (ObjectUtils.isNotEmpty(assetMasterDTO.getId())) {
			assetMasterVO = assetMasterRepo.findById(assetMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid AssetMaster details"));
			
			assetMasterVO.setUpdatedBy(assetMasterDTO.getCreatedBy());


			message = "LeaveType Updated Successfully";
		} else {

			assetMasterVO.setCreatedBy(assetMasterDTO.getCreatedBy());
			assetMasterVO.setUpdatedBy(assetMasterDTO.getCreatedBy());
			message = "LeaveType Created Successfully";
		}

		createUpdateAssetMasterVOByAssetMasterDTO(assetMasterVO, assetMasterDTO);
		assetMasterRepo.save(assetMasterVO);
		Map<String, Object> response = new HashMap<>();
		response.put("assetMasterVO", assetMasterVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateAssetMasterVOByAssetMasterDTO(AssetMasterVO assetMasterVO, AssetMasterDTO assetMasterDTO) {
		assetMasterVO.setAssetName(assetMasterDTO.getAssetName());
		assetMasterVO.setAssetCode(assetMasterDTO.getAssetCode());
		assetMasterVO.setCategory(assetMasterDTO.getCategory());
		assetMasterVO.setBrand(assetMasterDTO.getBrand());
		assetMasterVO.setModel(assetMasterDTO.getModel());
		assetMasterVO.setSerialNumber(assetMasterDTO.getSerialNumber());
		assetMasterVO.setPurchaseDate(assetMasterDTO.getPurchaseDate());
		assetMasterVO.setPurchaseCost(assetMasterDTO.getPurchaseCost());
		assetMasterVO.setWarrantyExpiry(assetMasterDTO.getWarrantyExpiry());
		assetMasterVO.setLocation(assetMasterDTO.getLocation());
		assetMasterVO.setNotes(assetMasterDTO.getNotes());
		assetMasterVO.setBranch(assetMasterDTO.getBranch());
		assetMasterVO.setBranchCode(assetMasterDTO.getBranchCode());
		assetMasterVO.setFinyear(assetMasterDTO.getFinyear());
		assetMasterVO.setOrgId(assetMasterDTO.getOrgId());


	}
	
	
	@Override
	public AssetMasterVO getAssetMasterById(Long id) {

		return assetMasterRepo.getAssetMasterById(id);
	}

	@Override
	public List<AssetMasterVO> getAssetMasterByOrgId(Long orgId,String branchCode) {
		// TODO Auto-generated method stub
		return assetMasterRepo.getAssetMasterByOrgId(orgId,branchCode);
	}
	
	
	@Override
	public Map<String, Object> CreateUpdateAssetAllocation(AssetAllocationDTO assetAllocationDTO) throws ApplicationException {

		AssetAllocationVO assetAllocationVO = new AssetAllocationVO();
		String message;
		
		if (ObjectUtils.isNotEmpty(assetAllocationDTO.getId())) {
			assetAllocationVO = assetAllocationRepo.findById(assetAllocationDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid AssetAllocation details"));
			
			assetAllocationVO.setUpdatedBy(assetAllocationDTO.getCreatedBy());


			message = "AssetAllocation Updated Successfully";
		} else {

			assetAllocationVO.setCreatedBy(assetAllocationDTO.getCreatedBy());
			assetAllocationVO.setUpdatedBy(assetAllocationDTO.getCreatedBy());
			message = "AssetAllocation Created Successfully";
		}

		createUpdateAssetAllocationVOByAssetAllocationDTO(assetAllocationVO, assetAllocationDTO);
		assetAllocationRepo.save(assetAllocationVO);
		Map<String, Object> response = new HashMap<>();
		response.put("assetAllocationVO", assetAllocationVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateAssetAllocationVOByAssetAllocationDTO(AssetAllocationVO assetAllocationVO, AssetAllocationDTO assetAllocationDTO) {

	    assetAllocationVO.setAssetName(assetAllocationDTO.getAssetName());
	    assetAllocationVO.setAssetCode(assetAllocationDTO.getAssetCode());
	    assetAllocationVO.setEmployeeCode(assetAllocationDTO.getEmployeeCode());
	    assetAllocationVO.setEmployeeName(assetAllocationDTO.getEmployeeName());
	    assetAllocationVO.setAllocationDate(assetAllocationDTO.getAllocationDate());
	    assetAllocationVO.setExpectedreturndate(assetAllocationDTO.getExpectedreturndate());
	    assetAllocationVO.setAssetcondition(assetAllocationDTO.getAssetcondition());
	    assetAllocationVO.setAllocationnotes(assetAllocationDTO.getAllocationnotes());

	    assetAllocationVO.setBranch(assetAllocationDTO.getBranch());
	    assetAllocationVO.setBranchCode(assetAllocationDTO.getBranchCode());
	    assetAllocationVO.setFinyear(assetAllocationDTO.getFinyear());
	    assetAllocationVO.setCreatedBy(assetAllocationDTO.getCreatedBy());
	    assetAllocationVO.setOrgId(assetAllocationDTO.getOrgId());
	}
	
	
	@Override
    public List<Map<String, Object>> getAssetNameCodeByOrgId(Long orgId, String branchCode) {
        List<Object[]> results = assetAllocationRepo.getAssetNameCodeByOrgId(orgId, branchCode);
        List<Map<String, Object>> list = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("assetName", row[0] != null ? row[0] : "");
            map.put("assetCode", row[1] != null ? row[1] : "");
            list.add(map);
        }


        return list;
    }

	@Override
	public List<AssetAllocationVO> getAssetAllocationByOrgId(Long orgId, String branchCode) {
		// TODO Auto-generated method stub
		return assetAllocationRepo.getAssetAllocationByOrgId(orgId,branchCode);
	}
	
	@Override
	public AssetAllocationVO getAssetAllocationById(Long id) {
		return assetAllocationRepo.getAssetAllocationById(id);
	}
	

	@Override
    public List<Map<String, Object>> getAssetCountByOrgId(Long orgId, String branchCode) {
        List<Object[]> results = assetAllocationRepo.getAssetCountByOrgId(orgId, branchCode);
        List<Map<String, Object>> list = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("totalAsset", row[0]);
            map.put("allocatedAsset", row[1]);
            map.put("availableAsset", row[2]);

            list.add(map);
        }

        return list;
    }
	
	
	@Override
    public List<Map<String, Object>> getAssetDashboardByOrgId(Long orgId, String branchCode) {
        List<Object[]> results = assetAllocationRepo.getAssetDashboardByOrgId(orgId, branchCode);
        List<Map<String, Object>> list = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("assetname", row[0] != null ? row[0] : "");
            map.put("category", row[1] != null ? row[1] : "");
            map.put("status", row[2] != null ? row[2] : "");
            map.put("employeeName", row[3] != null ? row[3] : "");
            map.put("location", row[4] != null ? row[4] : "");

            list.add(map);
        }

        return list;
    }
	
}
