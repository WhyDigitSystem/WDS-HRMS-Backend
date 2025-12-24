package com.efit.hrms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.efit.hrms.dto.ClearanceManagementDTO;
import com.efit.hrms.dto.InitiateSeparationDTO;
import com.efit.hrms.entity.ClearanceManagementVO;
import com.efit.hrms.entity.EmployeeVO;
import com.efit.hrms.entity.InitiateSeparationVO;
import com.efit.hrms.exception.ApplicationException;
import com.efit.hrms.repo.ClearanceManagementRepo;
import com.efit.hrms.repo.EmployeeRepo;
import com.efit.hrms.repo.InitiateSeparationRepo;

@Service
public class EmployeeSeparationServiceImpl implements EmployeeSeparationService {

	private final EmployeeRepo employeeRepo;

	public static final Logger LOGGER = LoggerFactory.getLogger(EmployeeSeparationServiceImpl.class);

	@Autowired
	InitiateSeparationRepo initiateSeparationRepo;

	@Autowired
	ClearanceManagementRepo clearanceManagementRepo;

	EmployeeSeparationServiceImpl(EmployeeRepo employeeRepo) {
		this.employeeRepo = employeeRepo;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateInitiateSeparation(InitiateSeparationDTO initiateSeparationDTO)
			throws ApplicationException {

		String methodName = "createUpdateInitiateSeparation()";
		LOGGER.debug("Starting method: {}", methodName);

		Map<String, Object> response = new LinkedHashMap<>();
		InitiateSeparationVO initiateSeparationVO;
		String message;

		// 1️⃣ Create or Update
		if (initiateSeparationDTO.getId() != null && initiateSeparationDTO.getId() > 0) {
			initiateSeparationVO = initiateSeparationRepo.findById(initiateSeparationDTO.getId())
					.orElseThrow(() -> new ApplicationException(
							"Error: Initiate Separation ID " + initiateSeparationDTO.getId() + " not found!"));
			initiateSeparationVO.setUpdatedBy(initiateSeparationDTO.getCreatedBy());
			initiateSeparationVO.setStatus(initiateSeparationDTO.getStatus());

			message = "Initiate Separation Updated Successfully";
		} else {
			initiateSeparationVO = new InitiateSeparationVO();
			initiateSeparationVO.setCreatedBy(initiateSeparationDTO.getCreatedBy());
			initiateSeparationVO.setUpdatedBy(initiateSeparationDTO.getCreatedBy());
			initiateSeparationVO.setStatus("PENDING");

			message = "Initiate Separation Created Successfully";
		}

		// 2️⃣ Map fields
		mapDTOtoEntity(initiateSeparationDTO, initiateSeparationVO);

		// 3️⃣ Handle Child Table (Safely for Hibernate)
		if (initiateSeparationVO.getClearanceManagementVO() == null) {
			initiateSeparationVO.setClearanceManagementVO(new ArrayList<>());
		} else {
			initiateSeparationVO.getClearanceManagementVO().clear();
		}

		if (initiateSeparationDTO.getClearanceManagementDTO() != null
				&& !initiateSeparationDTO.getClearanceManagementDTO().isEmpty()) {

			for (ClearanceManagementDTO childDTO : initiateSeparationDTO.getClearanceManagementDTO()) {
				ClearanceManagementVO childVO = new ClearanceManagementVO();
				childVO.setClearanceItem(childDTO.getClearanceItem());
				childVO.setInitiateSeparationVO(initiateSeparationVO);
				initiateSeparationVO.getClearanceManagementVO().add(childVO);
			}
		}

		// 4️⃣ Save parent (Hibernate will cascade to children automatically)
		InitiateSeparationVO savedVO = initiateSeparationRepo.save(initiateSeparationVO);

		// 5️⃣ Prepare Response
		response.put("statusFlag", "Ok");
		response.put("status", true);

		Map<String, Object> paramObjectsMap = new LinkedHashMap<>();
		paramObjectsMap.put("message", message);
		paramObjectsMap.put("initiateSeparationVO", savedVO);
		response.put("paramObjectsMap", paramObjectsMap);

		LOGGER.debug("Ending method: {}", methodName);
		return response;
	}

	private void mapDTOtoEntity(InitiateSeparationDTO dto, InitiateSeparationVO vo) {
		vo.setEmployeeName(dto.getEmployeeName());
		vo.setEmployeeCode(dto.getEmployeeCode());

		EmployeeVO emplpoyeeVO = employeeRepo.findByEmployeeNameAndEmployeeCode(dto.getEmployeeName(),
				dto.getEmployeeCode());

		if (emplpoyeeVO == null) {
			throw new ApplicationContextException("employeeDetails is null");
		} else {
			emplpoyeeVO.setActive(false);
			employeeRepo.save(emplpoyeeVO);
		}

		vo.setDepartment(dto.getDepartment());
		vo.setPosition(dto.getPosition());
		vo.setJoiningDate(dto.getJoiningDate());
		vo.setSeparationType(dto.getSeparationType());
		vo.setResignation(dto.getResignation());
		vo.setLastWorkingDate(dto.getLastWorkingDate());
		vo.setNoticeDate(dto.getNoticeDate());
		vo.setReasonCategory(dto.getReasonCategory());
		vo.setRehireEligible(dto.getRehireEligible());
		vo.setDetailedReason(dto.getDetailedReason());
		vo.setBranchCode(dto.getBranchCode());
		vo.setBranch(dto.getBranch());
		vo.setOrgId(dto.getOrgId());
		vo.setReportingPerson(dto.getReportingPerson());
		vo.setReportingPersonCode(dto.getReportingPersonCode());
		vo.setReportingPersonEmail(dto.getReportingPersonEmail());
		vo.setInterviewDate(dto.getInterviewDate());
		vo.setExperienceRating(dto.getExperienceRating());
		vo.setExitInterviewFeedback(dto.getExitInterviewFeedback());

	}

	@Override
	public InitiateSeparationVO getInitiateSeparationById(Long id) {
		return initiateSeparationRepo.getInitiateSeparationById(id);
	}

	@Override
	public List<InitiateSeparationVO> getInitiateSeparationByOrgId(Long orgId, String branchCode) {
		// TODO Auto-generated method stub
		return initiateSeparationRepo.getInitiateSeparationByOrgId(orgId, branchCode);
	}

	@Override
	public List<InitiateSeparationVO> getInitiateSeparationByDepartment(Long orgId, String branchCode,
			String department, String type) {
		// TODO Auto-generated method stub
		return initiateSeparationRepo.getInitiateSeparationByDepartment(orgId, branchCode, department, type);
	}

	@Override
	public List<Map<String, Object>> getInitiateSeparationCountByOrgId(Long orgId, String branchCode) {
		List<Object[]> results = initiateSeparationRepo.getInitiateSeparationCountByOrgId(orgId, branchCode);
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] row : results) {
			Map<String, Object> map = new HashMap<>();
			map.put("totalCount", row[0]);
			map.put("pendingCount", row[1]);
			map.put("completedCount", row[2]);

			list.add(map);
		}

		return list;
	}
}
