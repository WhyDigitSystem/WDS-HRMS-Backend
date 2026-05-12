package com.efit.hrms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efit.hrms.dto.DocTypeDTO;
import com.efit.hrms.dto.DocTypeMappingDTO;
import com.efit.hrms.dto.DocTypeMappingDetailsDTO;
import com.efit.hrms.entity.BranchVO;
import com.efit.hrms.entity.DocTypeMappingDetailsVO;
import com.efit.hrms.entity.DocTypeMappingVO;
import com.efit.hrms.entity.DocTypeVO;
import com.efit.hrms.exception.ApplicationException;
import com.efit.hrms.repo.BranchRepo;
import com.efit.hrms.repo.DocTypeMappingDetailsRepo;
import com.efit.hrms.repo.DocTypeMappingRepo;
import com.efit.hrms.repo.DocTypeRepo;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

	public static final Logger LOGGER = LoggerFactory.getLogger(DocumentTypeServiceImpl.class);

	@Autowired
	DocTypeRepo docTypeRepo;

	@Autowired
	DocTypeMappingRepo docTypeMappingRepo;

	@Autowired
	BranchRepo branchRepo;

	@Autowired
	DocTypeMappingDetailsRepo docTypeMappingDetailsRepo;

	@Override
	public DocTypeVO createDocType(DocTypeDTO docTypeDTO) throws ApplicationException {

		DocTypeVO docTypeVO = new DocTypeVO();

		if (docTypeRepo.existsByScreenCode(docTypeDTO.getScreenCode())) {
			String errorMessage = String.format("The Screen Code: %s already exists ", docTypeDTO.getScreenCode());
			throw new ApplicationException(errorMessage);
		}

		if (docTypeRepo.existsByDocCode(docTypeDTO.getDocCode())) {
			String errorMessage = String.format("The Doc Code: %s already exists ", docTypeDTO.getScreenCode());
			throw new ApplicationException(errorMessage);
		}
		if (docTypeRepo.existsByScreenName(docTypeDTO.getScreenName())) {
			String errorMessage = String.format("The Screen Name: %s already exists ", docTypeDTO.getScreenName());
			throw new ApplicationException(errorMessage);
		}

		docTypeVO.setOrgId(docTypeDTO.getOrgId());
		docTypeVO.setScreenCode(docTypeDTO.getScreenCode());
		docTypeVO.setScreenName(docTypeDTO.getScreenName());
		docTypeVO.setDocCode(docTypeDTO.getDocCode());
		docTypeVO.setDocCodePos(docTypeDTO.getDocCodePos());
		docTypeVO.setBranchCodePos(docTypeDTO.getBranchCodePos());
//		docTypeVO.setFinYearPos(docTypeDTO.getFinYearPos());
		docTypeVO.setSeqPos(docTypeDTO.getSeqPos());
		docTypeVO.setSeqPos(docTypeDTO.getSeqPos());
		docTypeVO.setSeqDigit(docTypeDTO.getSeqDigit());
		docTypeVO.setCodePattern(docTypeDTO.getCodePattern());

		docTypeRepo.save(docTypeVO);

		return docTypeVO;
	}

//@Override
//	public List<Map<String, Object>> getPendingDocTypeMapping(String branch, String branchCode) throws NumberFormatException, ApplicationException {
//
//		Set<Object[]> pendingDocTypeDetails = docTypeMappingRepo.getPendingDocTypeMappingDetails(branch, branchCode);
//		return getDetails(pendingDocTypeDetails);
//	}
//
//	private List<Map<String, Object>> getDetails(Set<Object[]> pendingDocTypeDetails)
//			throws NumberFormatException, ApplicationException {
//
//		List<Map<String, Object>> detailsList = new ArrayList<>();
//
//		for (Object[] record : pendingDocTypeDetails) {
//			Map<String, Object> map = new HashMap<>();
//			map.put("branch", record[0] != null ? record[0].toString() : null);
//			map.put("branchCode", record[1] != null ? record[1].toString() : null);
////			map.put("finYear", record[2] != null ? record[2].toString() : null);
////			map.put("finYearId", record[3] != null ? record[3].toString() : null);
//			map.put("docCode", record[2] != null ? record[2].toString() : null);
//			map.put("screenCode", record[3] != null ? record[3].toString() : null);
//			map.put("screenName", record[4] != null ? record[4].toString() : null);
//
//			String prefix = generateDocIdUsingPrefix(record[0].toString(),
//					record[3].toString());
//			map.put("prefix", prefix != null ? prefix : null);
//			map.put("lastNo", 1);
//
//			detailsList.add(map);
//		}
//		return detailsList;
//
//	}
//
//	public String generateDocIdUsingPrefix(String branch, String screenCode) throws ApplicationException {
//
//		DocTypeVO config = docTypeRepo.findByScreenCode(screenCode)
//				.orElseThrow(() -> new RuntimeException("No code config found for screenCode: " + screenCode));
//
////		FinancialYearVO financialYearVO = financialYearRepo.findByFinYear(finYear);
//		BranchVO branchVO = branchRepo.findByBranch(branch);
//		// Step 3: Prepare value map
//		Map<String, Object> values = new HashMap<>();
//		if (config.getDocCode() != null) {
//			values.put("docCode", config.getDocCode());
//		}
//		if (branchVO.getBranchCode() != null) {
//			values.put("branchCode", branchVO.getBranchCode());
//		} else {
//			throw new ApplicationException("Branch Does not have Branch Code or not Found: " + branch);
//		}
////		if (financialYearVO.getFinYrIdentifier() != null) {
////			values.put("finYear", financialYearVO.getFinYrIdentifier());
////		} else {
////			throw new ApplicationException("FinYear Does not have Finyear ID or not Found: " + finYear);
////		}
//
//		// Step 4: Replace pattern dynamically
//		String code = resolvePatternWithSmartSkippingNew(config.getCodePattern(), values);
//		System.out.println("EmployeeCode: " + code);
//		return code;
//	}
//
//	private String resolvePatternWithSmartSkippingNew(String pattern, Map<String, Object> values) {
//		Pattern regex = Pattern.compile("\\$\\{(.*?)}");
//		Matcher matcher = regex.matcher(pattern);
//
//		StringBuilder result = new StringBuilder();
//		int lastIndex = 0;
//		while (matcher.find()) {
//			String placeholder = matcher.group(1); // e.g., companyCode
//			Object value = values.get(placeholder);
//
//			// Extract separator text before placeholder
//			String separator = pattern.substring(lastIndex, matcher.start());
//
//			// Include only if value is not zero
//			if (value != null && !(value instanceof Integer && (Integer) value == 0)) {
//				result.append(separator).append(value);
//			}
//
//			lastIndex = matcher.end();
//		}
//
//		// Append trailing part after last placeholder
//		result.append(pattern.substring(lastIndex));
//
//		// Optional cleanup
//		return result.toString().replaceAll("[-_/\\.]{2,}", "-") // prevent multiple symbols
//				.replaceAll("^[-_/\\.]+|[-_/\\.]+$", ""); // trim ends
//	}

	@Override
	public List<Map<String, Object>> getPendingDocTypeMapping(String branch, String branchCode)
			throws NumberFormatException, ApplicationException {

		Set<Object[]> pendingDocTypeDetails = docTypeMappingRepo.getPendingDocTypeMappingDetails(branch, branchCode);
		return getDetails(pendingDocTypeDetails);
	}

	private List<Map<String, Object>> getDetails(Set<Object[]> pendingDocTypeDetails)
			throws NumberFormatException, ApplicationException {

		List<Map<String, Object>> detailsList = new ArrayList<>();

		for (Object[] record : pendingDocTypeDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("branch", record[0] != null ? record[0].toString() : null);
			map.put("branchCode", record[1] != null ? record[1].toString() : null);
			map.put("docCode", record[2] != null ? record[2].toString() : null);
			map.put("screenCode", record[3] != null ? record[3].toString() : null);
			map.put("screenName", record[4] != null ? record[4].toString() : null);

			// ✅ Use branchCode for prefix generation
			String prefix = generateDocIdUsingPrefix(record[1].toString(), record[3].toString());
			map.put("prefix", prefix != null ? prefix : null);
			map.put("lastNo", 1);

			detailsList.add(map);
		}
		return detailsList;
	}

	public String generateDocIdUsingPrefix(String branchCode, String screenCode) throws ApplicationException {
		// Step 1: Get DocType configuration
		DocTypeVO config = docTypeRepo.findByScreenCode(screenCode)
				.orElseThrow(() -> new RuntimeException("No code config found for screenCode: " + screenCode));

		// Step 2: Get branch details by branchCode (✅ unique)
		BranchVO branchVO = branchRepo.findByBranchCode(branchCode);
		if (branchVO == null) {
			throw new ApplicationException("Branch not found for branchCode: " + branchCode);
		}

		// Step 3: Prepare value map
		Map<String, Object> values = new HashMap<>();
		if (config.getDocCode() != null) {
			values.put("docCode", config.getDocCode());
		}
		if (branchVO.getBranchCode() != null) {
			values.put("branchCode", branchVO.getBranchCode());
		} else {
			throw new ApplicationException("Branch does not have a valid Branch Code: " + branchCode);
		}

		// Step 4: Replace pattern dynamically
		String code = resolvePatternWithSmartSkippingNew(config.getCodePattern(), values);
		System.out.println("Generated Prefix: " + code);
		return code;
	}

	private String resolvePatternWithSmartSkippingNew(String pattern, Map<String, Object> values) {
		Pattern regex = Pattern.compile("\\$\\{(.*?)}");
		Matcher matcher = regex.matcher(pattern);

		StringBuilder result = new StringBuilder();
		int lastIndex = 0;

		while (matcher.find()) {
			String placeholder = matcher.group(1);
			Object value = values.get(placeholder);

			String separator = pattern.substring(lastIndex, matcher.start());

			if (value != null && !(value instanceof Integer && (Integer) value == 0)) {
				result.append(separator).append(value);
			}

			lastIndex = matcher.end();
		}

		result.append(pattern.substring(lastIndex));

		return result.toString().replaceAll("[-_/\\.]{2,}", "-") // prevent duplicate separators
				.replaceAll("^[-_/\\.]+|[-_/\\.]+$", ""); // trim edges
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public DocTypeMappingVO createDocTypeMappingVO(DocTypeMappingDTO docTypeMappingDTO) throws ApplicationException {

		DocTypeMappingVO docTypeMappingVO = new DocTypeMappingVO();
		docTypeMappingVO.setOrgId(docTypeMappingDTO.getOrgId());
		docTypeMappingVO.setBranch(docTypeMappingDTO.getBranch());
		docTypeMappingVO.setBranchCode(docTypeMappingDTO.getBranchCode());
		docTypeMappingVO.setCreatedBy(docTypeMappingDTO.getCreatedBy());
//		docTypeMappingVO.setFinYear(docTypeMappingDTO.getFinYear());
//		docTypeMappingVO.setFinYearId(docTypeMappingDTO.getFinYearId());
		docTypeMappingVO.setModifiedBy(docTypeMappingDTO.getCreatedBy());

		List<DocTypeMappingDetailsVO> detailsVOs = new ArrayList<>();

		if (docTypeMappingDTO.getDocTypeMappingDetailsDTO() != null) {
			for (DocTypeMappingDetailsDTO detailsDTO : docTypeMappingDTO.getDocTypeMappingDetailsDTO()) {
				DocTypeMappingDetailsVO documentTypeMappingDetailsVO = new DocTypeMappingDetailsVO();
				documentTypeMappingDetailsVO.setBranch(detailsDTO.getBranch());
				documentTypeMappingDetailsVO.setBranchCode(detailsDTO.getBranchCode());
//		        documentTypeMappingDetailsVO.setFinYear(detailsDTO.getFinYear());
//		        documentTypeMappingDetailsVO.setFinYearId(detailsDTO.getFinYearId());
				documentTypeMappingDetailsVO.setScreenCode(detailsDTO.getScreenCode());
				documentTypeMappingDetailsVO.setScreenName(detailsDTO.getScreenName());
				documentTypeMappingDetailsVO.setDocCode(detailsDTO.getDocCode());
				documentTypeMappingDetailsVO.setPrefix(detailsDTO.getPrefix());
				documentTypeMappingDetailsVO.setLastNo(1);
				documentTypeMappingDetailsVO.setOrgId(docTypeMappingDTO.getOrgId());
				documentTypeMappingDetailsVO.setDocTypeMappingVO(docTypeMappingVO);
				detailsVOs.add(documentTypeMappingDetailsVO);
			}
		}

		docTypeMappingVO.setDocumentTypeMappingDetailsVO(detailsVOs);
		docTypeMappingRepo.save(docTypeMappingVO);
		return docTypeMappingVO;
	}

	@Override
	@Transactional
	public String getDocid(String branchCode, String screenCode) throws ApplicationException {

		return generateDocId(branchCode, screenCode);

	}

	public String generateDocId(String branchCode, String screenCode) throws ApplicationException {

		DocTypeVO config = docTypeRepo.findByScreenCode(screenCode)
				.orElseThrow(() -> new RuntimeException("No code config found for screenCode: " + screenCode));

		DocTypeMappingDetailsVO docTypeMappingDetailsVO = docTypeMappingDetailsRepo
				.findByBranchCodeAndScreenCode(branchCode, screenCode);
//		FinancialYearVO financialYearVO = financialYearRepo.findByFinYear(finYear);
		BranchVO branchVO = branchRepo.findByBranchCode(branchCode);
		// Step 3: Prepare value map
		Map<String, Object> values = new HashMap<>();
		if (config.getDocCode() != null) {
			values.put("docCode", config.getDocCode());
		}
		if (branchVO.getBranchCode() != null) {
			values.put("branchCode", branchVO.getBranchCode());
		} else {
			throw new ApplicationException("Branch Does not have Branch Code or not Found: " + branchCode);
		}
//		if (financialYearVO.getFinYrIdentifier() != null) {
//			values.put("finYear", financialYearVO.getFinYrIdentifier());
//		} else {
//			throw new ApplicationException("FinYear Does not have Finyear ID or not Found: " + finYear);
//		}

		String paddedSeq = String.format("%0" + config.getSeqDigit() + "d", docTypeMappingDetailsVO.getLastNo());
		System.out.println(paddedSeq);
		values.put("seq", paddedSeq);

		// Step 4: Replace pattern dynamically
		String code = resolvePatternWithSmartSkipping(config.getCodePattern(), values);
		System.out.println("EmployeeCode: " + code);
		return code;
	}

	private String resolvePatternWithSmartSkipping(String pattern, Map<String, Object> values) {
		Pattern regex = Pattern.compile("\\$\\{(.*?)}");
		Matcher matcher = regex.matcher(pattern);

		StringBuilder result = new StringBuilder();
		int lastIndex = 0;

		while (matcher.find()) {
			String placeholder = matcher.group(1);
			Object value = values.get(placeholder);

			String separator = pattern.substring(lastIndex, matcher.start());

			// include if value exists (seq like 00001 should not be skipped)
			if (value != null) {
				result.append(separator).append(value);
			}

			lastIndex = matcher.end();
		}

		result.append(pattern.substring(lastIndex));

		return result.toString().replaceAll("[-_/\\.]{2,}", "-").replaceAll("^[-_/\\.]+|[-_/\\.]+$", "");
	}

//	private String resolvePatternWithSmartSkipping(String pattern, Map<String, Object> values) {
//		Pattern regex = Pattern.compile("\\$\\{(.*?)}");
//		Matcher matcher = regex.matcher(pattern);
//
//		StringBuilder result = new StringBuilder();
//		int lastIndex = 0;
//		while (matcher.find()) {
//			String placeholder = matcher.group(1); // e.g., companyCode
//			Object value = values.get(placeholder);
//
//			// Extract separator text before placeholder
//			String separator = pattern.substring(lastIndex, matcher.start());
//
//			// Include only if value is not zero
//			if (value != null && !(value instanceof Integer && (Integer) value == 0)) {
//				result.append(separator).append(value);
//			}
//
//			lastIndex = matcher.end();
//		}
//
//		// Append trailing part after last placeholder
//		result.append(pattern.substring(lastIndex));
//
//		// Optional cleanup
//		return result.toString().replaceAll("[-_/\\.]{2,}", "-") // prevent multiple symbols
//				.replaceAll("^[-_/\\.]+|[-_/\\.]+$", ""); // trim ends
//	}

//	@Override
//	public Map<String, Object> createUpdateDocType(DocTypeDTO docTypeDTO) throws ApplicationException {
//
//		DocTypeVO docTypeVO = new DocTypeVO();
//		String message;
//		
//		if (ObjectUtils.isNotEmpty(docTypeDTO.getId())) {
//			docTypeVO = docTypeRepo.findById(docTypeDTO.getId())
//					.orElseThrow(() -> new ApplicationException("Invalid DocType details"));
//			
//			docTypeVO.setUpdatedBy(docTypeDTO.getCreatedBy());
//
//
//			message = "DocType Updated Successfully";
//		} else {
//
//			docTypeVO.setCreatedBy(docTypeDTO.getCreatedBy());
//			docTypeVO.setUpdatedBy(docTypeDTO.getCreatedBy());
//			message = "DocTypeDTO Created Successfully";
//		}
//
//		createUpdateDocTypeVOByDocTypeDTO(docTypeVO, docTypeDTO);
//		docTypeRepo.save(docTypeVO);
//		Map<String, Object> response = new HashMap<>();
//		response.put("docTypeVO", docTypeVO);
//		response.put("message", message);
//		return response;
//	}
//
//	private void createUpdateDocTypeVOByDocTypeDTO(DocTypeVO docTypeVO, DocTypeDTO docTypeDTO) {
//		docTypeVO.setScreenName(docTypeDTO.getScreenName());
//		docTypeVO.setScreenCode(docTypeDTO.getScreenCode());
//		docTypeVO.setDocCode(docTypeDTO.getDocCode());
//		docTypeVO.setOrgId(docTypeDTO.getOrgId());
//		docTypeVO.setDocCodePos(docTypeDTO.getDocCodePos());
//		docTypeVO.setBranchCodePos(docTypeDTO.getBranchCodePos());
//		docTypeVO.setSeqPos(docTypeDTO.getSeqPos());
//		docTypeVO.setSeqDigit(docTypeDTO.getSeqDigit());
//		docTypeVO.setCodePattern(docTypeDTO.getCodePattern());
//
//
//	}
//

}
