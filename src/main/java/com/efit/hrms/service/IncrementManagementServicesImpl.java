package com.efit.hrms.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efit.hrms.dto.IncrementManagementDTO;
import com.efit.hrms.dto.IncrementManagementDetailsDTO;
import com.efit.hrms.entity.EmployeeVO;
import com.efit.hrms.entity.IncrementManagementDetailsVO;
import com.efit.hrms.entity.IncrementManagementVO;
import com.efit.hrms.entity.SalaryEarningDetailsVO;
import com.efit.hrms.entity.SalaryStructureVO;
import com.efit.hrms.exception.ApplicationException;
import com.efit.hrms.repo.EmployeeRepo;
import com.efit.hrms.repo.IncrementManagementDetailsRepo;
import com.efit.hrms.repo.IncrementManagementRepo;
import com.efit.hrms.repo.SalaryEarningDetailsRepo;
import com.efit.hrms.repo.SalaryStructureRepo;


@Service
public class IncrementManagementServicesImpl implements IncrementManagementServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncrementManagementServicesImpl.class);

    @Autowired
    private IncrementManagementRepo incrementManagementRepo;

    @Autowired
    private IncrementManagementDetailsRepo incrementManagementDetailsRepo;
    
    @Autowired
    SalaryStructureRepo salaryStructureRepo;
    
    @Autowired
    SalaryEarningDetailsRepo salaryEarningDetailsRepo;
    
    @Autowired
    EmployeeRepo employeeRepo;
    
	 @Override
	    public Map<String, Object> createUpdateIncrementManagement(IncrementManagementDTO dto) throws ApplicationException {
	        String methodName = "createUpdateIncrementManagement()";
	        LOGGER.debug("Starting method: {}", methodName);

	        Map<String, Object> response = new HashMap<>();
	        IncrementManagementVO entity;
	        String message;

	        // --- CREATE or UPDATE logic ---
	        if (dto.getId() != null) {
	            entity = incrementManagementRepo.findById(dto.getId())
	                    .orElseThrow(() -> new ApplicationException("Invalid Increment Management record"));
	            entity.setUpdatedBy(dto.getCreatedBy());
	            message = "Increment Management Updated Successfully";
	        } else {
	            entity = new IncrementManagementVO();
	            entity.setCreatedBy(dto.getCreatedBy());
	            entity.setUpdatedBy(dto.getCreatedBy());
	            message = "Increment Management Created Successfully";
	        }

	        // --- Map DTO to Entity ---
	        mapDTOtoEntity(dto, entity);

	        // --- Save Parent (with Cascade for child list) ---
	        incrementManagementRepo.save(entity);

	        response.put("paramObjectsMap", entity);
	        response.put("message", message);

	        LOGGER.debug("Ending method: {}", methodName);
	        return response;
	    }

	    private void mapDTOtoEntity(IncrementManagementDTO dto, IncrementManagementVO entity) {

	        entity.setEmployeeName(dto.getEmployeeName());
	        entity.setEmployeeCode(dto.getEmployeeCode());
	        entity.setDepartment(dto.getDepartment());
	        entity.setDesignation(dto.getDesignation());
	        entity.setLocation(dto.getLocation());
	        entity.setJoiningDate(dto.getJoiningDate());
	        entity.setReportingTo(dto.getReportingTo());
	        entity.setIncrementCycle(dto.getIncrementCycle());
	        entity.setEffectiveFrom(dto.getEffectiveFrom());
	        entity.setAdjustmentType(dto.getAdjustmentType());
	        entity.setAdjustmentValue(dto.getAdjustmentValue());
	        entity.setNewDesignation(dto.getNewDesignation());
	        entity.setNewGrade(dto.getNewGrade());
	        entity.setRemarks(dto.getRemarks());
	        entity.setNextApproval(dto.getNextApproval());
//	        entity.setTotalctc(dto.getTotalctc());
	        entity.setTotalCtcPercentage(dto.getAdjustmentValue());
	        entity.setApproveStatus("PENDING");
	        entity.setOrgId(dto.getOrgId());
	        entity.setReportingPersonCode(dto.getReportingPersonCode());
	        entity.setReportingPersonEmail(dto.getReportingPersonEmail());
	        entity.setBranch(dto.getBranch());
	        entity.setBranchCode(dto.getBranchCode());

//	        entity.setApproveBy(dto.getApproveBy());
//	        entity.setApproveOn(dto.getApproveOn());

	        // --- If Updating: Remove old child records ---
	        if (dto.getId() != null && entity.getIncrementManagementDetailsVO() != null) {
	            // Fetch existing child details for this parent
	            List<IncrementManagementDetailsVO> existingDetails = 
	                incrementManagementDetailsRepo.findByIncrementManagementVO(entity);

	            // Delete old child records before saving new ones
	            if (!existingDetails.isEmpty()) {
	                incrementManagementDetailsRepo.deleteAll(existingDetails);
	            }
	        }
	        
	       
	        BigDecimal totalCtc = BigDecimal.ZERO;
	        List<IncrementManagementDetailsVO> detailsList = new ArrayList<>();

	        if (dto.getIncrementManagementDetailsDTO() != null) {
	            for (IncrementManagementDetailsDTO detailsDTO : dto.getIncrementManagementDetailsDTO()) {
	                IncrementManagementDetailsVO detailsVO = new IncrementManagementDetailsVO();
	                detailsVO.setHeading(detailsDTO.getHeading());
	                detailsVO.setAmount(detailsDTO.getAmount());
	                detailsVO.setIncrementManagementVO(entity); // set parent reference
	                detailsList.add(detailsVO);

	                // Add to total CTC
	                if (detailsDTO.getAmount() != null) {
	                    totalCtc = totalCtc.add(detailsDTO.getAmount());
	                }
	            }
	        }

	        // --- Set Calculated Total CTC ---
	        entity.setTotalctc(totalCtc);

	        entity.setIncrementManagementDetailsVO(detailsList);
	    }

	    @Override
	    public Map<String, Object> createApprovalIncrementManagement(
	            Long orgId,
	            Long id,
	            String employeeCode,
	            String action,
	            String actionBy,
	            String notifyCode,
	            String notify,
	            String screenName,
	            String email) throws Exception {

	        // Response map
	        Map<String, Object> response = new HashMap<>();
	        String message = "";

	        // 1️⃣ Fetch increment management record
	        IncrementManagementVO incrementManagementVO =
	                incrementManagementRepo.findByOrgIdAndIdAndEmployeeCode(orgId, id, employeeCode);

	        if (incrementManagementVO == null) {
	            throw new ApplicationException("Increment Management record not found for the given details.");
	        }

	        // 2️⃣ Check if already approved or rejected
	        String currentStatus = incrementManagementVO.getApproveStatus();
	        if (currentStatus != null &&
	            (currentStatus.equalsIgnoreCase("Approved") || currentStatus.equalsIgnoreCase("Rejected"))) {
	            throw new ApplicationException(
	                    "This IncrementManagement is already " + currentStatus + ".");
	        }

	        // 3️⃣ Proceed only if action is valid
	        if ("APPROVED".equalsIgnoreCase(action)) {

	            // Fetch existing salary structure
	            SalaryStructureVO existingStructure =
	                    salaryStructureRepo.findByOrgIdAndEmployeeCode(orgId, employeeCode);

	            if (existingStructure == null) {
	                throw new ApplicationException("No existing salary structure found for employee " + employeeCode);
	            }

	            // Create new Salary Structure entry
	            SalaryStructureVO newStructure = new SalaryStructureVO();
	            newStructure.setEmployeeName(existingStructure.getEmployeeName());
	            newStructure.setEmployeeCode(existingStructure.getEmployeeCode());
	            newStructure.setDateOfBirth(existingStructure.getDateOfBirth());
	            newStructure.setGrade(existingStructure.getGrade());
	            newStructure.setDepartment(existingStructure.getDepartment());
	            newStructure.setPanNo(existingStructure.getPanNo());
	            newStructure.setBankAccountNo(existingStructure.getBankAccountNo());
	            newStructure.setDateOfJoining(existingStructure.getDateOfJoining());
	            newStructure.setOrgId(existingStructure.getOrgId());
	            newStructure.setBranch(existingStructure.getBranch());
	            newStructure.setBranchCode(existingStructure.getBranchCode());
	            newStructure.setDesignation(existingStructure.getDesignation());
	            newStructure.setPfPercentage(existingStructure.getPfPercentage());
	            newStructure.setEsiPercentage(existingStructure.getEsiPercentage());
	            newStructure.setEffectiveFrom(incrementManagementVO.getEffectiveFrom());
	            newStructure.setCreatedBy(actionBy);


	            // Save new structure
	            SalaryStructureVO savedStructure = salaryStructureRepo.save(newStructure);

	            // Set total earnings in header
	            savedStructure.setSumOfEarning(incrementManagementVO.getTotalctc());
	            salaryStructureRepo.save(savedStructure);

	            // Create earning details from increment management details
	            List<SalaryEarningDetailsVO> earningDetails = incrementManagementVO.getIncrementManagementDetailsVO()
	                    .stream()
	                    .filter(dto -> dto.getAmount() != null && dto.getAmount().compareTo(BigDecimal.ZERO) > 0)
	                    .map(dto -> {
	                        SalaryEarningDetailsVO vo = new SalaryEarningDetailsVO();
	                        vo.setHeading(dto.getHeading());
	                        vo.setAmount(dto.getAmount());
	                        vo.setSalaryStructureVO(savedStructure);
	                        return vo;
	                    })
	                    .collect(Collectors.toList());

	            // Save new earnings
	            if (!earningDetails.isEmpty()) {
	                salaryEarningDetailsRepo.saveAll(earningDetails);
	            }
	            
	            EmployeeVO employee = employeeRepo.findByOrgIdAndEmployeeCode(orgId, employeeCode);

	            if (employee != null) {
	                employee.setDesignation(incrementManagementVO.getNewDesignation());
	                employee.setGrade(incrementManagementVO.getNewGrade());
	                employeeRepo.save(employee);
	            } else {
	                throw new Exception("Employee not found for code: " + employeeCode);
	            }
	            
	            
	            message = "Approved Successfully";

	        } else if ("REJECTED".equalsIgnoreCase(action)) {
	            // Just mark as rejected
	            message = "Rejected Successfully";

	        } else {
	            throw new ApplicationException("Invalid action: must be APPROVED or REJECTED.");
	        }

	        // 4️⃣ Update approval details
	        incrementManagementVO.setApproveStatus(action);
	        incrementManagementVO.setApproveBy(actionBy);

	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
	        incrementManagementVO.setApproveOn(LocalDateTime.now().format(formatter).toUpperCase());

	        incrementManagementRepo.save(incrementManagementVO);

	        // 5️⃣ Prepare response
	        response.put("incrementManagementVO", incrementManagementVO);
	        response.put("message", message);
	        return response;
	    }

	    
	    @Override
		public List<IncrementManagementVO> getIncrementManagementForDashBoard(Long orgId, String reportingPersonCode,String branchCode) {
			return  incrementManagementRepo.getIncrementManagementForDashBoard(orgId, reportingPersonCode,branchCode);
		}

	    
		@Override
		public List<SalaryStructureVO> getLatestSalaryStructureByOrgId(Long orgId, String employeeCode) {
			return incrementManagementRepo.getLatestSalaryStructureByOrgId(orgId,employeeCode);
		}
		
	    
		
		@Override
	    public List<Map<String, Object>> getSalaryHistoryforIncrement(Long orgId, String employeeCode) {
	        List<Object[]> results = incrementManagementRepo.getSalaryHistoryforIncrement(orgId, employeeCode);
	        List<Map<String, Object>> list = new ArrayList<>();

	        for (Object[] row : results) {
	            Map<String, Object> map = new HashMap<>();
	            map.put("id", row[0]);
	            map.put("year", row[1]);
	            map.put("newSalaryEffectivedate", row[2]);
	            map.put("previousSalary", row[3]);
	            map.put("newSalary", row[4]);
	            map.put("totalctcPercentage", row[5]);
	            map.put("approvedBy", row[6]);
	            map.put("status", row[7]);
	            list.add(map);
	        }

	        return list;
	    }
}
