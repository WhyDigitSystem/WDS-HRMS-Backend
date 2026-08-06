package com.efit.hrms.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efit.hrms.dto.InvestmentDeclarationDTO;
import com.efit.hrms.entity.FormVO;
import com.efit.hrms.entity.InvestmentDeclarationVO;
import com.efit.hrms.exception.ApplicationException;

@Service
public interface InvestmentDeclarationService {

	Map<String, Object> updateCreateInvestmentDeclaration(@Valid InvestmentDeclarationDTO investmentDeclarationDTO)
			throws ApplicationException;

	List<InvestmentDeclarationVO> getInvestmentDeclarationDetails(Long orgId, String branch, String employeeCode,Long finYear);

	InvestmentDeclarationVO getInvestmentDeclarationById(Long id);

	List<Map<String, Object>> getDashBoardDetailsNew(Long orgId, String branch, String employeeCode);

	Map<String, Object> uploadImageInvestmentDeclarationDetails(List<MultipartFile> files, Long investmentDeclarationId,
			List<Long> investmentDeclarationDetailsId) throws IOException, ApplicationException;

	Map<String, Object> approveInvestmentDeclaration(Long orgId, Long id, String employeeCode, String action,
			String actionBy, Long sourceId) throws Exception;

	List<Map<String, Object>> getDashBoardDetailsOldRegime(Long orgId, String branch, String employeeCode);

	List<Map<String, Object>> getTdsSummaryDetails(Long orgId, String branch, String employeeCode);

	List<Map<String, Object>> getTaxRegimeComparisonDetails(Long orgId, String branch, String employeeCode);

	ResponseEntity<byte[]> viewFileInvestment(HttpServletRequest request) throws IOException;

	List<Map<String, Object>> getSalaryHeadsTdsAmount(Long orgId, String branch, String employeeCode);

	List<FormVO> getFormDetails(Long orgId, String branch, String employeeCode, Long finYear);

	ResponseEntity<byte[]> viewTicketImageForm(HttpServletRequest request) throws IOException;

	FormVO uploadImageForm16(MultipartFile file, Long orgId, String branch, String branchCode, String employeeCode,
			String employeeName, Long finYear, String createdBy) throws IOException;

}
