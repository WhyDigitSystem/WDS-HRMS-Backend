package com.efit.hrms.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efit.hrms.dto.InvestmentDeclarationDTO;
import com.efit.hrms.entity.InvestmentDeclarationVO;
import com.efit.hrms.exception.ApplicationException;

@Service
public interface InvestmentDeclarationService {

	Map<String, Object> updateCreateInvestmentDeclaration(@Valid InvestmentDeclarationDTO investmentDeclarationDTO)
			throws ApplicationException;

	List<InvestmentDeclarationVO> getInvestmentDeclarationDetails(Long orgId, String branchCode, String employeeCode);

	InvestmentDeclarationVO getInvestmentDeclarationById(Long id);

	List<Map<String, Object>> getDashBoardDetailsNew(Long orgId, String branch, String employeeCode);

	String uploadImageInvestmentDeclarationDetails(List<MultipartFile> files, Long investmentDeclarationId,
			List<Long> investmentDeclarationDetailsId) throws IOException;

	Map<String, Object> approveInvestmentDeclaration(Long orgId, Long id, String employeeCode, String action,
			String actionBy, Long sourceId) throws Exception;

}
