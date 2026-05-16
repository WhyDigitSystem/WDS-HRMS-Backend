package com.efit.hrms.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

	@Value("${file.upload-dirs}")
	private String uploadBasePath;

	@Override
	public List<InvestmentDeclarationVO> getInvestmentDeclarationDetails(Long orgId, String branch,
			String employeeCode) {

		return investmentDeclarationRepo.getInvestmentDeclarationDetails(orgId, branch, employeeCode);
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

		BigDecimal totalAmount = BigDecimal.ZERO;

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

			totalAmount = totalAmount.add(investmentDeclarationDetailsVO.getDeclared());

			investmentDeclarationDetailsVO.setInvestmentDeclarationVO(investmentDeclarationVO);

			investmentDeclarationDetailsVOs.add(investmentDeclarationDetailsVO);
		}

		investmentDeclarationVO.setTotalAmount(totalAmount);

		investmentDeclarationVO.setInvestmentDeclarationDetailsVO(investmentDeclarationDetailsVOs);
	}

//	@Override
//	public String uploadImageInvestmentDeclarationDetails(List<MultipartFile> files, Long investmentDeclarationId,
//			List<Long> investmentDeclarationDetailsId) throws IOException {
//
//		if (files.size() != investmentDeclarationDetailsId.size()) {
//
//			throw new IllegalArgumentException("Mismatch between files and detail IDs.");
//		}
//
//		InvestmentDeclarationVO investmentDeclarationVO = investmentDeclarationRepo.findById(investmentDeclarationId)
//				.orElseThrow(() -> new RuntimeException("InvestmentDeclaration not found"));
//
//		String uploadDir = "C:/investmentfiles/";
//
//		File dir = new File(uploadDir);
//
//		if (!dir.exists()) {
//			dir.mkdirs();
//		}
//
//		for (int i = 0; i < files.size(); i++) {
//
//			MultipartFile file = files.get(i);
//
//			Long detailId = investmentDeclarationDetailsId.get(i);
//
//			InvestmentDeclarationDetailsVO detail = investmentDeclarationDetailsRepo.findById(detailId)
//					.orElseThrow(() -> new RuntimeException("Details not found ID : " + detailId));
//
//			if (!detail.getInvestmentDeclarationVO().getId().equals(investmentDeclarationVO.getId())) {
//
//				throw new IllegalArgumentException("Detail ID " + detailId + " does not belong to declaration.");
//			}
//
//			if (detail.getFilePath() != null && !detail.getFilePath().isEmpty()) {
//
//				File oldFile = new File(detail.getFilePath());
//
//				if (oldFile.exists()) {
//
//					boolean deleted = oldFile.delete();
//
//					if (deleted) {
//						System.out.println("Old image deleted successfully");
//					}
//				}
//			}
//
//			String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//
//			String filePath = uploadDir + filename;
//
//			File dest = new File(filePath);
//
//			file.transferTo(dest);
//
//			detail.setFileName(filename);
//
//			detail.setFilePath(filePath);
//
//			investmentDeclarationDetailsRepo.save(detail);
//		}
//
//		return "Image uploaded/updated successfully";
//	}

	@Override
	@Transactional
	public Map<String, Object> uploadImageInvestmentDeclarationDetails(List<MultipartFile> files,
			Long investmentDeclarationId, List<Long> investmentDeclarationDetailsId)
			throws ApplicationException, IOException {

		if (files == null || files.isEmpty()) {

			throw new RuntimeException("Files are required");
		}

		if (files.size() != investmentDeclarationDetailsId.size()) {

			throw new IllegalArgumentException("Mismatch between files and detail IDs.");
		}

		InvestmentDeclarationVO investmentDeclarationVO = investmentDeclarationRepo.findById(investmentDeclarationId)
				.orElseThrow(() -> new RuntimeException("InvestmentDeclaration not found"));

		// BASE FOLDER
		Path declarationFolder = Paths.get(uploadBasePath, "investmentfiles", investmentDeclarationId.toString());

		createDirectoryInvestment(declarationFolder);

		for (int i = 0; i < files.size(); i++) {

			MultipartFile file = files.get(i);

			Long detailId = investmentDeclarationDetailsId.get(i);

			InvestmentDeclarationDetailsVO detail = investmentDeclarationDetailsRepo.findById(detailId)
					.orElseThrow(() -> new RuntimeException("Details not found ID : " + detailId));

			// VALIDATION
			if (!detail.getInvestmentDeclarationVO().getId().equals(investmentDeclarationVO.getId())) {

				throw new IllegalArgumentException("Detail ID " + detailId + " does not belong to declaration.");
			}

			// DELETE OLD FILE
			if (detail.getFilePath() != null && !detail.getFilePath().isEmpty()) {

				deleteFileSafelyInvestment(detail.getFilePath());
			}

			// ORIGINAL FILE NAME
			String originalName = file.getOriginalFilename();

			if (originalName == null) {
				originalName = "file";
			}

			// REMOVE SPACES
			originalName = originalName.replaceAll("\\s+", "_");

			// EXTENSION
			String extension = "";

			if (originalName.contains(".")) {

				extension = originalName.substring(originalName.lastIndexOf("."));

				originalName = originalName.substring(0, originalName.lastIndexOf("."));
			}

			// NEW FILE NAME
			String fileName = originalName + "_" + detailId + extension;

			// FINAL FILE PATH
			Path filePath = declarationFolder.resolve(fileName);

			// SAVE FILE
			try (InputStream inputStream = file.getInputStream()) {

				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			}

			// BASE URL
			String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/api/investmentDeclaration/viewFile/").toUriString();

			// RELATIVE PATH
			String relativePath = uploadBasePath.replace("\\", "/");

			relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");

			// PUBLIC URL
			String publicUrl = baseUrl + relativePath;

			// SAVE DB
			detail.setFileName(fileName);

			detail.setFilePath(publicUrl);

			detail.setFileSize(file.getSize());

			detail.setContentType(file.getContentType());

			detail.setUploadOn(LocalDateTime.now());

			investmentDeclarationDetailsRepo.save(detail);

			System.out.println("FILE SAVED : " + filePath.toAbsolutePath());

			System.out.println("PUBLIC URL : " + publicUrl);
		}

		Map<String, Object> response = new HashMap<>();

		response.put("investmentDeclarationVO", investmentDeclarationVO);

		return response;
	}

	private void deleteFileSafelyInvestment(String fileUrl) {

		try {

			String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

			String relativePath = fileUrl.replace(baseUrl + "/api/investmentDeclaration/viewFile/", "");

			Path filePath = Paths.get(uploadBasePath, relativePath);

			if (Files.exists(filePath)) {

				Files.delete(filePath);

				System.out.println("Old file deleted : " + filePath);
			}

		} catch (Exception e) {

			System.err.println("Unable to delete file : " + fileUrl);
		}
	}

	private void createDirectoryInvestment(Path path) throws IOException {

		if (!Files.exists(path)) {

			Files.createDirectories(path);
		}
	}

	@Override
	public ResponseEntity<byte[]> viewFileInvestment(HttpServletRequest request) throws IOException {

		return serveFileInvestment(request, "/api/investmentDeclaration/viewFile/", uploadBasePath);
	}

	private ResponseEntity<byte[]> serveFileInvestment(HttpServletRequest request, String apiPrefix,
			String uploadBasePath) throws IOException {

		String uri = request.getRequestURI();

		// REMOVE API PREFIX
		String relativePath = uri.replace(apiPrefix, "");

		// URL DECODE
		relativePath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);

		// REMOVE uploads/
		if (relativePath.startsWith("uploads/")) {

			relativePath = relativePath.substring("uploads/".length());
		}

		Path baseDir = Paths.get(uploadBasePath).toAbsolutePath().normalize();

		Path filePath = baseDir.resolve(relativePath).normalize();

		// SECURITY CHECK
		if (!filePath.startsWith(baseDir)) {

			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		// FILE EXISTS
		if (!Files.exists(filePath)) {

			return ResponseEntity.notFound().build();
		}

		String contentType = Files.probeContentType(filePath);

		if (contentType == null) {

			contentType = "application/octet-stream";
		}

		byte[] data = Files.readAllBytes(filePath);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline").body(data);
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

	@Override
	public List<Map<String, Object>> getDashBoardDetailsOldRegime(Long orgId, String branch, String employeeCode) {
		Set<Object[]> chType = investmentDeclarationRepo.getDashBoardDetailsOldRegime(orgId, branch, employeeCode);
		return getDashBoardDetailsOldRegime(chType);
	}

	private List<Map<String, Object>> getDashBoardDetailsOldRegime(Set<Object[]> chType) {
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
	public List<Map<String, Object>> getTdsSummaryDetails(Long orgId, String branch, String employeeCode) {
		Set<Object[]> chType = investmentDeclarationRepo.getTdsSummaryDetails(orgId, branch, employeeCode);
		return getTdsSummaryDetails(chType);
	}

	private List<Map<String, Object>> getTdsSummaryDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("month", ch[0] != null ? ch[0].toString() : "");
			map.put("monthlyTdasAmount", ch[1] != null ? new BigDecimal(ch[1].toString()) : BigDecimal.ZERO);
//			map.put("taxableIncome", ch[2] != null ? new BigDecimal(ch[2].toString()) : BigDecimal.ZERO);
//			map.put("yearlyTds", ch[3] != null ? new BigDecimal(ch[3].toString()) : BigDecimal.ZERO);
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getTaxRegimeComparisonDetails(Long orgId, String branch, String employeeCode) {
		Set<Object[]> chType = investmentDeclarationRepo.getTaxRegimeComparisonDetails(orgId, branch, employeeCode);
		return getTaxRegimeComparisonDetails(chType);
	}

	private List<Map<String, Object>> getTaxRegimeComparisonDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("sNo", ch[0] != null ? ch[0].toString() : "");
			map.put("name", ch[1] != null ? ch[1].toString() : "");
			map.put("amount", ch[2] != null ? new BigDecimal(ch[2].toString()) : BigDecimal.ZERO);
//			map.put("yearlyTds", ch[3] != null ? new BigDecimal(ch[3].toString()) : BigDecimal.ZERO);
			List1.add(map);
		}
		return List1;
	}

	@Override
	public Resource viewInvestmentImage(Long detailId) throws IOException {

		InvestmentDeclarationDetailsVO detail = investmentDeclarationDetailsRepo.findById(detailId)
				.orElseThrow(() -> new RuntimeException("Image not found"));

		Path path = Paths.get(detail.getFilePath());

		Resource resource = new UrlResource(path.toUri());

		if (!resource.exists()) {
			throw new RuntimeException("Image not found in filepath");
		}

		return resource;
	}
}
