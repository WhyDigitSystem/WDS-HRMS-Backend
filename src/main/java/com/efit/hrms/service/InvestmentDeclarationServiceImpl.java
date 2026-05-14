package com.efit.hrms.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efit.hrms.dto.InvestmentDeclarationDTO;
import com.efit.hrms.dto.InvestmentDeclarationDetailsDTO;
import com.efit.hrms.entity.InvestmentDeclarationDetailsVO;
import com.efit.hrms.entity.InvestmentDeclarationVO;
import com.efit.hrms.exception.ApplicationException;
import com.efit.hrms.repo.InvestmentDeclarationDetailsRepo;
import com.efit.hrms.repo.InvestmentDeclarationRepo;

@Service
public class InvestmentDeclarationServiceImpl implements InvestmentDeclarationService {

	public static final Logger LOGGER = LoggerFactory.getLogger(InvestmentDeclarationServiceImpl.class);

	@Autowired
	InvestmentDeclarationRepo investmentDeclarationRepo;

	@Autowired
	InvestmentDeclarationDetailsRepo investmentDeclarationDetailsRepo;

	// CostEstimation

	@Override
	public List<InvestmentDeclarationVO> getInvestmentDeclarationDetails(Long orgId, String branchCode,
			String employeeCode) {

		return investmentDeclarationRepo.getInvestmentDeclarationDetails(orgId, branchCode, employeeCode);
	}

	@Override
	public InvestmentDeclarationVO getInvestmentDeclarationById(Long id) {

		return investmentDeclarationRepo.getInvestmentDeclarationById(id);
	}

	@Override
	public Map<String, Object> updateCreateInvestmentDeclaration(
			@Valid InvestmentDeclarationDTO investmentDeclarationDTO) throws ApplicationException {

		InvestmentDeclarationVO investmentDeclarationVO = new InvestmentDeclarationVO();

		String message;

		if (ObjectUtils.isNotEmpty(investmentDeclarationDTO.getId())) {

			investmentDeclarationVO = investmentDeclarationRepo.findById(investmentDeclarationDTO.getId())
					.orElseThrow(() -> new ApplicationException("Investment Declaration Not Found!"));

			investmentDeclarationVO.setModifiedBy(investmentDeclarationDTO.getCreatedBy());

			message = "Investment Declaration Updated Successfully";

		} else {

			investmentDeclarationVO.setCreatedBy(investmentDeclarationDTO.getCreatedBy());
			investmentDeclarationVO.setModifiedBy(investmentDeclarationDTO.getCreatedBy());

			message = "Investment Declaration Created Successfully";
		}

		createUpdateInvestmentDeclarationVOByDTO(investmentDeclarationDTO, investmentDeclarationVO);

		investmentDeclarationRepo.save(investmentDeclarationVO);

		Map<String, Object> response = new HashMap<>();
		response.put("investmentDeclarationVO", investmentDeclarationVO);
		response.put("message", message);

		return response;
	}

	private void createUpdateInvestmentDeclarationVOByDTO(@Valid InvestmentDeclarationDTO investmentDeclarationDTO,
			InvestmentDeclarationVO investmentDeclarationVO) throws ApplicationException {

		investmentDeclarationVO.setBranch(investmentDeclarationDTO.getBranch());
		investmentDeclarationVO.setBranchCode(investmentDeclarationDTO.getBranchCode());
		investmentDeclarationVO.setEmployeeName(investmentDeclarationDTO.getEmployeeName());
		investmentDeclarationVO.setEmployeeCode(investmentDeclarationDTO.getEmployeeCode());
		investmentDeclarationVO.setCreatedBy(investmentDeclarationDTO.getCreatedBy());
		investmentDeclarationVO.setOrgId(investmentDeclarationDTO.getOrgId());

		if (ObjectUtils.isNotEmpty(investmentDeclarationVO.getId())) {

			List<InvestmentDeclarationDetailsVO> existingDetails = investmentDeclarationDetailsRepo
					.findByInvestmentDeclarationVO(investmentDeclarationVO);

			investmentDeclarationDetailsRepo.deleteAll(existingDetails);
		}

		List<InvestmentDeclarationDetailsVO> investmentDeclarationDetailsVOs = new ArrayList<>();

		for (InvestmentDeclarationDetailsDTO investmentDeclarationDetailsDTO : investmentDeclarationDTO
				.getInvestmentDeclarationDetailsDTO()) {

			InvestmentDeclarationDetailsVO investmentDeclarationDetailsVO = new InvestmentDeclarationDetailsVO();

			investmentDeclarationDetailsVO.setSection(investmentDeclarationDetailsDTO.getSection());

			investmentDeclarationDetailsVO.setInvestmentType(investmentDeclarationDetailsDTO.getInvestmentType());

			investmentDeclarationDetailsVO.setLimitAmount(investmentDeclarationDetailsDTO.getLimitAmount());

			if (investmentDeclarationDetailsDTO.getDeclared()
					.compareTo(investmentDeclarationDetailsDTO.getLimitAmount()) <= 0) {

				investmentDeclarationDetailsVO.setDeclared(investmentDeclarationDetailsDTO.getDeclared());

			} else {

				throw new ApplicationException("Declared amount should not be greater than limit amount");
			}

			investmentDeclarationDetailsVO.setInvestmentDeclarationVO(investmentDeclarationVO);

			investmentDeclarationDetailsVOs.add(investmentDeclarationDetailsVO);
		}

		investmentDeclarationVO.setInvestmentDeclarationDetailsVO(investmentDeclarationDetailsVOs);
	}

	@Override
	public String uploadImageInvestmentDeclarationDetails(List<MultipartFile> files, Long investmentDeclarationId,
			List<Long> investmentDeclarationDetailsId) throws IOException {

		if (files.size() != investmentDeclarationDetailsId.size()) {
			throw new IllegalArgumentException("Mismatch between number of files and detail IDs.");
		}

		InvestmentDeclarationVO investmentDeclarationVO = investmentDeclarationRepo.findById(investmentDeclarationId)
				.orElseThrow(() -> new RuntimeException("CostEstimation not found"));

		for (int i = 0; i < files.size(); i++) {
			MultipartFile file = files.get(i);
			Long detailId = investmentDeclarationDetailsId.get(i);

			InvestmentDeclarationDetailsVO detail = investmentDeclarationDetailsRepo.findById(detailId)
					.orElseThrow(() -> new RuntimeException("CostEstimationDetail not found with ID: " + detailId));

			if (!detail.getInvestmentDeclarationVO().getId().equals(investmentDeclarationVO.getId())) {
				throw new IllegalArgumentException(
						"Detail with ID " + detailId + " does not belong to the specified cost estimation.");
			}

			detail.setUploadFile(file.getBytes());
			investmentDeclarationDetailsRepo.save(detail);
		}

		return "Files uploaded successfully";
	}

	@Override
	public List<Map<String, Object>> getDashBoardDetailsNew(Long orgId, String branch, String employeeCode) {
		Set<Object[]> chType = investmentDeclarationRepo.getDashBoardDetailsNew(orgId, branch, employeeCode);
		return getDashBoardDetailsNew(chType);
	}

	private List<Map<String, Object>> getDashBoardDetailsNew(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("grossIncome", ch[0] != null ? new BigDecimal(ch[0].toString()) : BigDecimal.ZERO);
			map.put("totalDedcutions", ch[1] != null ? new BigDecimal(ch[1].toString()) : BigDecimal.ZERO);
			map.put("taxableIncome", ch[2] != null ? new BigDecimal(ch[2].toString()) : BigDecimal.ZERO);
			map.put("yearlyTds", ch[3] != null ? new BigDecimal(ch[3].toString()) : BigDecimal.ZERO);
			List1.add(map);
		}
		return List1;
	}

	@Override
	public Map<String, Object> approveInvestmentDeclaration(Long orgId, Long id, String employeeCode, String action,
			String actionBy, Long sourceId) throws Exception {

		Map<String, Object> response = new HashMap<>();

		String message = "";

		InvestmentDeclarationVO investmentDeclarationVO = investmentDeclarationRepo
				.findByOrgIdAndIdAndEmployeeCode(orgId, id, employeeCode);

		if (investmentDeclarationVO == null) {

			throw new ApplicationException("Investment Declaration record not found.");
		}

		InvestmentDeclarationDetailsVO detailsVO = investmentDeclarationDetailsRepo
				.findByInvestmentDeclarationDetailsId(sourceId);

		if (detailsVO == null) {

			throw new ApplicationException("Investment Declaration Details not found.");
		}

		if (!detailsVO.getInvestmentDeclarationVO().getId().equals(id)) {

			throw new ApplicationException("Sub record does not belong to this main record.");
		}

		String currentStatus = detailsVO.getApproveStatus();

		if (currentStatus != null
				&& ("APPROVED".equalsIgnoreCase(currentStatus) || "REJECTED".equalsIgnoreCase(currentStatus))) {

			throw new ApplicationException("This record already " + currentStatus);
		}

		if ("APPROVED".equalsIgnoreCase(action)) {

			detailsVO.setApproveStatus(action);

			message = "Approved Successfully";

		} else if ("REJECTED".equalsIgnoreCase(action)) {

			detailsVO.setApproveStatus(action);

			message = "Rejected Successfully";

		} else {

			throw new ApplicationException("Invalid action. Use APPROVED or REJECTED.");
		}

		detailsVO.setApproveBy(actionBy);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");

		detailsVO.setApproveOn(LocalDateTime.now().format(formatter).toUpperCase());
		detailsVO.setStatus(action);

		investmentDeclarationDetailsRepo.save(detailsVO);

		response.put("investmentDeclarationId", investmentDeclarationVO.getId());

		response.put("investmentDeclarationDetailsId", detailsVO.getId());

		response.put("employeeCode", investmentDeclarationVO.getEmployeeCode());

		response.put("employeeName", investmentDeclarationVO.getEmployeeName());

		response.put("approveStatus", detailsVO.getApproveStatus());

		response.put("approveBy", detailsVO.getApproveBy());

		response.put("approveOn", detailsVO.getApproveOn());

		response.put("message", message);

		return response;
	}

}
