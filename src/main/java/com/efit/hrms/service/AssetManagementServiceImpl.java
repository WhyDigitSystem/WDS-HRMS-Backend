package com.efit.hrms.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efit.hrms.dto.AssetAllocationDTO;
import com.efit.hrms.dto.AssetMasterDTO;
import com.efit.hrms.dto.AssetReturnDTO;
import com.efit.hrms.dto.ExpenseClaimsDTO;
import com.efit.hrms.dto.TravelRequestsDTO;
import com.efit.hrms.entity.AssetAllocationVO;
import com.efit.hrms.entity.AssetImageVO;
import com.efit.hrms.entity.AssetMasterVO;
import com.efit.hrms.entity.AssetReturnVO;
import com.efit.hrms.entity.AssetStockVO;
import com.efit.hrms.entity.DocTypeMappingDetailsVO;
import com.efit.hrms.entity.EmployeeVO;
import com.efit.hrms.entity.ExpenseClaimsVO;
import com.efit.hrms.entity.TravelRequestsVO;
import com.efit.hrms.exception.ApplicationException;
import com.efit.hrms.repo.AssetAllocationRepo;
import com.efit.hrms.repo.AssetImageRepo;
import com.efit.hrms.repo.AssetMasterRepo;
import com.efit.hrms.repo.AssetReturnRepo;
import com.efit.hrms.repo.AssetStockRepo;
import com.efit.hrms.repo.DocTypeMappingDetailsRepo;
import com.efit.hrms.repo.EmployeeRepo;
import com.efit.hrms.repo.ExpenseClaimsRepo;
import com.efit.hrms.repo.TravelRequestsRepo;

@Service
public class AssetManagementServiceImpl implements AssetManagementService {

	@Autowired
	AssetMasterRepo assetMasterRepo;

	@Autowired
	AssetAllocationRepo assetAllocationRepo;

	@Autowired
	ExpenseClaimsRepo expenseClaimsRepo;

	@Autowired
	TravelRequestsRepo travelRequestsRepo;

	@Autowired
	AssetImageRepo assetImageRepo;

	@Autowired
	AssetStockRepo assetStockRepo;

	@Autowired
	AssetReturnRepo assetReturnRepo;

	@Autowired
	AssetImageRepo imageRepo;

	@Autowired
	DocumentTypeService documentTypeService;

	@Autowired
	EmployeeRepo employeeRepo;

	@Value("${file.upload-dir}")
	private String uploadDir;

	@Autowired
	DocTypeMappingDetailsRepo docTypeMappingDetailsRepo;

//	@Autowired
//	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Override
	public Map<String, Object> CreateUpdateAssetMaster(AssetMasterDTO assetMasterDTO) throws ApplicationException {

		AssetMasterVO assetMasterVO = new AssetMasterVO();
		String screenCode = "AM";
		String message;

		if (ObjectUtils.isNotEmpty(assetMasterDTO.getId())) {

			assetMasterVO = assetMasterRepo.findById(assetMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid AssetMaster details"));

			createUpdateAssetMasterVOByAssetMasterDTO(assetMasterVO, assetMasterDTO);
			assetMasterVO.setUpdatedBy(assetMasterDTO.getCreatedBy());
			assetMasterVO = assetMasterRepo.save(assetMasterVO);

			message = "AssetMaster Updated Successfully";
		} else {

			String docId = documentTypeService.getDocid(assetMasterDTO.getBranchCode(), screenCode);
			assetMasterVO.setAssetCode(docId);

			// GETDOCID LASTNO +1
			DocTypeMappingDetailsVO docTypeMappingDetailsVO = docTypeMappingDetailsRepo
					.findByBranchCodeAndScreenCode(assetMasterDTO.getBranchCode(), screenCode);
			docTypeMappingDetailsVO.setLastNo(docTypeMappingDetailsVO.getLastNo() + 1);
			docTypeMappingDetailsRepo.save(docTypeMappingDetailsVO);
			createUpdateAssetMasterVOByAssetMasterDTO(assetMasterVO, assetMasterDTO);
			assetMasterVO.setCreatedBy(assetMasterDTO.getCreatedBy());
			assetMasterVO.setUpdatedBy(assetMasterDTO.getCreatedBy());
			assetMasterVO = assetMasterRepo.save(assetMasterVO);
			AssetStockVO assetStockVO = new AssetStockVO();

			assetStockVO.setAssetName(assetMasterVO.getAssetName());
			assetStockVO.setAssetCode(assetMasterVO.getAssetCode());
			assetStockVO.setCategory(assetMasterVO.getCategory());
			assetStockVO.setBrand(assetMasterVO.getBrand());
			assetStockVO.setModel(assetMasterVO.getModel());
			assetStockVO.setSerialNumber(assetMasterVO.getSerialNumber());
			assetStockVO.setLocation(assetMasterVO.getLocation());
			assetStockVO.setBranch(assetMasterVO.getBranch());
			assetStockVO.setBranchCode(assetMasterVO.getBranchCode());
			assetStockVO.setFinyear(assetMasterVO.getFinyear());
			assetStockVO.setSourceId(assetMasterVO.getId());
			assetStockVO.setCreatedBy(assetMasterVO.getCreatedBy());
			assetStockVO.setUpdatedBy(assetMasterVO.getUpdatedBy());
			assetStockVO.setSourceScreen(assetMasterVO.getScreenName());
			assetStockVO.setSourceScreenCode(assetMasterVO.getScreenCode());
			assetStockVO.setOrgId(assetMasterVO.getOrgId());
			assetStockVO.setAssetStatus("A");
			assetStockVO.setQty(1);
			assetStockRepo.save(assetStockVO);
			message = "AssetMaster Created Successfully";

		}
		Map<String, Object> response = new HashMap<>();
		response.put("assetMasterVO", assetMasterVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateAssetMasterVOByAssetMasterDTO(AssetMasterVO assetMasterVO, AssetMasterDTO assetMasterDTO) {
		assetMasterVO.setAssetName(assetMasterDTO.getAssetName());
//		assetMasterVO.setAssetCode(assetMasterDTO.getAssetCode());
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
		assetMasterVO.setActive(assetMasterDTO.isActive());

	}

//	@Transactional
//	public Map<String, Object> uploadMultipleAssetImages(Long assetMasterId, List<MultipartFile> files, String createdBy) throws IOException, ApplicationException {
//	    Map<String, Object> response = new HashMap<>();
//
//	    AssetMasterVO assetMaster = assetMasterRepo.findById(assetMasterId)
//	        .orElseThrow(() -> new ApplicationException("Invalid AssetMaster ID"));
//
//	    List<AssetImageVO> uploadedImages = new ArrayList<>();
//
//	    for (MultipartFile file : files) {
//	        if (file.isEmpty()) continue;
//
//	        int seq = 1; // or start from 0 if needed
//
//	        String fileName = String.format("AssetImage%02d_%d_%s", 
//	            seq, 
//	            System.currentTimeMillis(), 
//	            file.getOriginalFilename()
//	        );
//	        seq++;
//
//	        Path filePath = Paths.get(uploadDir + File.separator + fileName);
//	        Files.createDirectories(filePath.getParent());
//	        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//	        // Create child object
//	        AssetImageVO imageVO = new AssetImageVO();
//	        imageVO.setFileName(fileName);
//	        imageVO.setImagePath(filePath.toString());
//	        imageVO.setAssetMaster(assetMaster);
//
//	        uploadedImages.add(imageVO);
//	    }
//
//	    // Add to header
//	    assetMaster.getAssetImages().addAll(uploadedImages);
//
//	    // Cascade saves all children
//	    assetMasterRepo.save(assetMaster);
//
//	    response.put("message", "Images uploaded successfully");
//	    response.put("uploadedCount", uploadedImages.size());
//	    response.put("imageList", uploadedImages);
//
//	    return response;
//	}
//
//	

//	@Transactional
//	public Map<String, Object> uploadMultipleAssetImages(Long assetMasterId, List<MultipartFile> files, String createdBy)
//	        throws IOException, ApplicationException, GeneralSecurityException {
//
//	    Map<String, Object> response = new HashMap<>();
//
//	    AssetMasterVO assetMaster = assetMasterRepo.findById(assetMasterId)
//	            .orElseThrow(() -> new ApplicationException("Invalid AssetMaster ID"));
//
//	    List<AssetImageVO> uploadedImages = new ArrayList<>();
//
//	    int seq = 1;
//
//	    for (MultipartFile file : files) {
//	        if (file.isEmpty()) continue;
//
//	        String fileName = String.format("AssetImage%02d_%d_%s",
//	                seq++,
//	                System.currentTimeMillis(),
//	                file.getOriginalFilename());
//
//	        // Save temporarily to local folder
//	        Path tempPath = Files.createTempFile("drive_upload_", file.getOriginalFilename());
//	        Files.copy(file.getInputStream(), tempPath, StandardCopyOption.REPLACE_EXISTING);
//
//	        java.io.File localFile = tempPath.toFile();
//
//	        // ✅ Upload to Google Drive
//	        String driveUrl = GoogleDriveUtil.uploadFileToDrive(localFile, fileName);
//
//	        // ✅ Create DB record
//	        AssetImageVO imageVO = new AssetImageVO();
//	        imageVO.setFileName(fileName);
////	        imageVO.setImagePath(driveUrl);
//	        imageVO.setAssetMaster(assetMaster);
//
//	        uploadedImages.add(imageVO);
//
//	        // Delete temp file after upload
//	        localFile.delete();
//	    }
//
//	    assetMaster.getAssetImages().addAll(uploadedImages);
//	    assetMasterRepo.save(assetMaster);
//
//	    response.put("message", "Images uploaded successfully to Google Drive");
//	    response.put("uploadedCount", uploadedImages.size());
//	    response.put("imageList", uploadedImages);
//
//	    return response;
//	}

	@Override
	public AssetMasterVO getAssetMasterById(Long id) {

		return assetMasterRepo.getAssetMasterById(id);
	}

	@Override
	public List<AssetMasterVO> getAssetMasterByOrgId(Long orgId, String branchCode) {
		// TODO Auto-generated method stub
		return assetMasterRepo.getAssetMasterByOrgId(orgId, branchCode);
	}

	@Transactional
	@Override
	public Map<String, Object> uploadImages(Long assetMasterId, List<MultipartFile> files) throws IOException {

		AssetMasterVO assetMaster = assetMasterRepo.findById(assetMasterId)
				.orElseThrow(() -> new RuntimeException("Asset not found with ID: " + assetMasterId));

		List<AssetImageVO> imageList = new ArrayList<>();
		List<String> failedFiles = new ArrayList<>();
		List<String> uploadedFileNames = new ArrayList<>();

		// Delete existing images
		assetImageRepo.deleteByAssetMasterId(assetMasterId);

		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				failedFiles.add(file.getOriginalFilename());
				continue;
			}

			try {
				AssetImageVO image = new AssetImageVO();
				image.setFileName(file.getOriginalFilename());
				image.setImageAttachment(file.getBytes()); // Save bytes to DB
				image.setAssetMaster(assetMaster);

				imageList.add(image);
				uploadedFileNames.add(file.getOriginalFilename());

			} catch (Exception ex) {
				failedFiles.add(file.getOriginalFilename());
			}
		}

		assetImageRepo.saveAll(imageList);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("assetMasterId", assetMasterId);
		response.put("uploadedCount", uploadedFileNames.size());
		response.put("failedCount", failedFiles.size());
		response.put("uploadedFiles", uploadedFileNames);
		response.put("failedFiles", failedFiles);

		if (failedFiles.isEmpty()) {
			response.put("message", "All images uploaded successfully.");
		} else {
			response.put("message", uploadedFileNames.size() + " images uploaded, " + failedFiles.size() + " failed.");
		}

		return response;
	}

	@Override
	public List<AssetImageVO> getImagesByAsset(Long assetMasterId) {
		AssetMasterVO assetMaster = assetMasterRepo.findById(assetMasterId)
				.orElseThrow(() -> new RuntimeException("Asset not found with ID: " + assetMasterId));
		return assetMaster.getAssetImages();
	}

	@Override
	public Map<String, Object> CreateUpdateAssetAllocation(AssetAllocationDTO assetAllocationDTO)
			throws ApplicationException {

		AssetAllocationVO assetAllocationVO = new AssetAllocationVO();
		String message;

		if (ObjectUtils.isNotEmpty(assetAllocationDTO.getId())) {
			assetAllocationVO = assetAllocationRepo.findById(assetAllocationDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid AssetAllocation details"));

			assetAllocationVO.setUpdatedBy(assetAllocationDTO.getCreatedBy());
			createUpdateAssetAllocationVOByAssetAllocationDTO(assetAllocationVO, assetAllocationDTO);
			assetAllocationRepo.save(assetAllocationVO);

			message = "AssetAllocation Updated Successfully";
		} else {

			assetAllocationVO.setCreatedBy(assetAllocationDTO.getCreatedBy());
			assetAllocationVO.setUpdatedBy(assetAllocationDTO.getCreatedBy());

			createUpdateAssetAllocationVOByAssetAllocationDTO(assetAllocationVO, assetAllocationDTO);
			assetAllocationRepo.save(assetAllocationVO);

			AssetMasterVO assetMasterVO = assetMasterRepo.findByAssetNameAndAssetCode(assetAllocationDTO.getAssetName(),
					assetAllocationDTO.getAssetCode());

			if (assetMasterVO == null) {
				throw new ApplicationContextException("No data found for AssetName and AssetCode");
			}

			AssetStockVO stockOut = new AssetStockVO();
			AssetStockVO stockIn = new AssetStockVO();

			stockOut.setLocation(assetMasterVO.getLocation());
			stockOut.setQty(-1);
			stockOut.setAStatus("S");

			stockIn.setLocation(assetAllocationDTO.getEmployeeName());
			stockIn.setLocationCode(assetAllocationDTO.getEmployeeCode());
			stockIn.setQty(1);
			stockIn.setAStatus("A");

			List<AssetStockVO> stockList = Arrays.asList(stockOut, stockIn);

			for (AssetStockVO vo : stockList) {

				vo.setAssetName(assetMasterVO.getAssetName());
				vo.setAssetCode(assetMasterVO.getAssetCode());
				vo.setSerialNumber(assetMasterVO.getSerialNumber());
				vo.setCategory(assetMasterVO.getCategory());
				vo.setBrand(assetMasterVO.getBrand());
				vo.setModel(assetMasterVO.getModel());
				vo.setBranch(assetMasterVO.getBranch());
				vo.setBranchCode(assetMasterVO.getBranchCode());
				vo.setFinyear(assetAllocationDTO.getFinyear());
				vo.setSourceId(assetAllocationVO.getId());
				vo.setCreatedBy(assetAllocationDTO.getCreatedBy());
				vo.setUpdatedBy(assetAllocationDTO.getCreatedBy());
				vo.setSourceScreen(assetAllocationVO.getScreenName());
				vo.setSourceScreenCode(assetAllocationVO.getScreenCode());
				vo.setOrgId(assetAllocationDTO.getOrgId());
				vo.setAssetStatus("A");

				assetStockRepo.save(vo);
			}
		}
		message = "AssetAllocation Created Successfully";

		Map<String, Object> response = new HashMap<>();
		response.put("assetAllocationVO", assetAllocationVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateAssetAllocationVOByAssetAllocationDTO(AssetAllocationVO assetAllocationVO,
			AssetAllocationDTO assetAllocationDTO) {

		AssetMasterVO assetMasterVO = assetMasterRepo.findByAssetNameAndAssetCode(assetAllocationDTO.getAssetName(),
				assetAllocationDTO.getAssetCode());

		if (assetMasterVO == null) {
			throw new ApplicationContextException("No data found for AssetName and AssetCode And SerialNumber");
		}
		assetAllocationVO.setAssetName(assetAllocationDTO.getAssetName());
		assetAllocationVO.setAssetCode(assetAllocationDTO.getAssetCode());
		assetAllocationVO.setEmployeeCode(assetAllocationDTO.getEmployeeCode());
		assetAllocationVO.setEmployeeName(assetAllocationDTO.getEmployeeName());
		assetAllocationVO.setAllocationDate(assetAllocationDTO.getAllocationDate());
		assetAllocationVO.setExpectedreturndate(assetAllocationDTO.getExpectedreturndate());
		assetAllocationVO.setAssetcondition(assetAllocationDTO.getAssetcondition());
		assetAllocationVO.setAllocationnotes(assetAllocationDTO.getAllocationnotes());
		assetAllocationVO.setSerialNumber(assetAllocationDTO.getSerialNumber());
		assetAllocationVO.setBranch(assetAllocationDTO.getBranch());
		assetAllocationVO.setBranchCode(assetAllocationDTO.getBranchCode());
		assetAllocationVO.setFinyear(assetAllocationDTO.getFinyear());
		assetAllocationVO.setCreatedBy(assetAllocationDTO.getCreatedBy());
		assetAllocationVO.setOrgId(assetAllocationDTO.getOrgId());
		assetAllocationVO.setActive(assetAllocationDTO.isActive());

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
		return assetAllocationRepo.getAssetAllocationByOrgId(orgId, branchCode);
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
			map.put("assetCode", row[0] != null ? row[0] : "");
			map.put("assetName", row[1] != null ? row[1] : "");
			map.put("serialNumber", row[2] != null ? row[2] : "");
			map.put("model", row[3] != null ? row[3] : "");
			map.put("category", row[4] != null ? row[4] : "");
			map.put("status", row[5] != null ? row[5] : "");
			map.put("location", row[6] != null ? row[6] : "");
			map.put("locationCode", row[7] != null ? row[7] : "");

			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getAssetAllocationReportByOrgId(Long orgId, String branchCode,
			String employeeCode) {
		List<Object[]> results = assetAllocationRepo.getAssetAllocationReportByOrgId(orgId, branchCode, employeeCode);
		Map<String, Object> employeeMap = new HashMap<>();
		List<Map<String, Object>> assetsList = new ArrayList<>();
		List<Map<String, Object>> finalList = new ArrayList<>();

		for (Object[] row : results) {
			// Employee Header (Parent) - only set once
			if (employeeMap.isEmpty()) {
				employeeMap.put("employeeName", row[0] != null ? row[0] : "");
				employeeMap.put("employeeCode", row[1] != null ? row[1] : "");
				employeeMap.put("email", row[2] != null ? row[2] : "");
				employeeMap.put("branch", row[3] != null ? row[3] : "");
				employeeMap.put("branchCode", row[4] != null ? row[4] : "");
				employeeMap.put("department", row[5] != null ? row[5] : "");
				employeeMap.put("designation", row[6] != null ? row[6] : "");
			}

			// Child List (Assets)
			Map<String, Object> asset = new HashMap<>();
			asset.put("assetName", row[7] != null ? row[7] : "");
			asset.put("assetCode", row[8] != null ? row[8] : "");
			asset.put("assetCondition", row[9] != null ? row[9] : "");
			asset.put("allocationDate", row[10] != null ? row[10] : "");
			asset.put("expectedReturnDate", row[11] != null ? row[11] : "");
			assetsList.add(asset);
		}

		// Attach child list
		employeeMap.put("assets", assetsList);

		// Wrap parent into final list (in case you add multiple employees later)
		finalList.add(employeeMap);

		return finalList;
	}

	// expenseclaims

	@Override
	public Map<String, Object> CreateUpdateExpenseClaims(ExpenseClaimsDTO expenseClaimsDTO)
			throws ApplicationException {

		ExpenseClaimsVO expenseClaimsVO = new ExpenseClaimsVO();
		String message;

		if (ObjectUtils.isNotEmpty(expenseClaimsDTO.getId())) {
			expenseClaimsVO = expenseClaimsRepo.findById(expenseClaimsDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid ExpenseClaims details"));

			expenseClaimsVO.setUpdatedBy(expenseClaimsDTO.getCreatedBy());

			message = "ExpenseClaims Updated Successfully";
		} else {

			expenseClaimsVO.setCreatedBy(expenseClaimsDTO.getCreatedBy());
			expenseClaimsVO.setUpdatedBy(expenseClaimsDTO.getCreatedBy());
			message = "ExpenseClaims Created Successfully";
		}

		createUpdateExpenseClaimsVOByExpenseClaimsDTO(expenseClaimsVO, expenseClaimsDTO);
		expenseClaimsRepo.save(expenseClaimsVO);
		Map<String, Object> response = new HashMap<>();
		response.put("expenseClaimsVO", expenseClaimsVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateExpenseClaimsVOByExpenseClaimsDTO(ExpenseClaimsVO expenseClaimsVO,
			ExpenseClaimsDTO expenseClaimsDTO) {

		EmployeeVO employeeVO = employeeRepo.findByEmployeeCode(expenseClaimsDTO.getEmployeeCode());

		expenseClaimsVO.setEmployeeName(expenseClaimsDTO.getEmployeeName());
		expenseClaimsVO.setEmployeeCode(expenseClaimsDTO.getEmployeeCode());
		expenseClaimsVO.setDepartment(expenseClaimsDTO.getDepartment());
		expenseClaimsVO.setExpenseTitle(expenseClaimsDTO.getExpenseTitle());
		expenseClaimsVO.setCategory(expenseClaimsDTO.getCategory());
		expenseClaimsVO.setAmount(expenseClaimsDTO.getAmount());
		expenseClaimsVO.setCurrency(expenseClaimsDTO.getCurrency());
		expenseClaimsVO.setExpenseDate(expenseClaimsDTO.getExpenseDate());
		expenseClaimsVO.setReceiptAttached(expenseClaimsDTO.getReceiptAttached());
		expenseClaimsVO.setDescription(expenseClaimsDTO.getDescription());
		expenseClaimsVO.setReportingPerson(employeeVO.getReportingPerson());
		expenseClaimsVO.setReportingPersonCode(employeeVO.getReportingPersonCode());
		expenseClaimsVO.setReportingPersonEmail(employeeVO.getReportingPersonEmail());
		expenseClaimsVO.setApproveStatus("PENDING");
		expenseClaimsVO.setBranchCode(expenseClaimsDTO.getBranchCode());
		expenseClaimsVO.setBranch(expenseClaimsDTO.getBranch());
		expenseClaimsVO.setCreatedBy(expenseClaimsDTO.getCreatedBy());
		expenseClaimsVO.setOrgId(expenseClaimsDTO.getOrgId());
	}

	@Override
	public List<ExpenseClaimsVO> getExpenseClaimsByOrgId(Long orgId, String branchCode, String employeeCode) {
		// TODO Auto-generated method stub
		return expenseClaimsRepo.getExpenseClaimsByOrgId(orgId, branchCode, employeeCode);
	}

	@Override
	public ExpenseClaimsVO getExpenseClaimsById(Long id) {
		return expenseClaimsRepo.getExpenseClaimsById(id);
	}

	// TravelRequests

	@Override
	public Map<String, Object> CreateUpdateTravelRequests(TravelRequestsDTO travelRequestsDTO)
			throws ApplicationException {

		TravelRequestsVO travelRequestsVO = new TravelRequestsVO();
		String message;

		if (ObjectUtils.isNotEmpty(travelRequestsDTO.getId())) {
			travelRequestsVO = travelRequestsRepo.findById(travelRequestsDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid TravelRequests details"));

			travelRequestsVO.setUpdatedBy(travelRequestsDTO.getCreatedBy());

			message = "TravelRequests Updated Successfully";
		} else {

			travelRequestsVO.setCreatedBy(travelRequestsDTO.getCreatedBy());
			travelRequestsVO.setUpdatedBy(travelRequestsDTO.getCreatedBy());
			message = "ExpenseClaims Created Successfully";
		}

		createUpdateTravelRequestsVOByTravelRequestsDTO(travelRequestsVO, travelRequestsDTO);
		travelRequestsRepo.save(travelRequestsVO);
		Map<String, Object> response = new HashMap<>();
		response.put("travelRequestsVO", travelRequestsVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateTravelRequestsVOByTravelRequestsDTO(TravelRequestsVO travelRequestsVO,
			TravelRequestsDTO travelRequestsDTO) {

		EmployeeVO employeeVO = employeeRepo.findByEmployeeCode(travelRequestsDTO.getEmployeeCode());

		travelRequestsVO.setEmployeeName(travelRequestsDTO.getEmployeeName());
		travelRequestsVO.setEmployeeCode(travelRequestsDTO.getEmployeeCode());
		travelRequestsVO.setDepartment(travelRequestsDTO.getDepartment());
		travelRequestsVO.setTravelTitle(travelRequestsDTO.getTravelTitle());
		travelRequestsVO.setFrom(travelRequestsDTO.getFrom());
		travelRequestsVO.setTo(travelRequestsDTO.getTo());
		travelRequestsVO.setDepartureDate(travelRequestsDTO.getDepartureDate());
		travelRequestsVO.setReturnDate(travelRequestsDTO.getReturnDate());
		travelRequestsVO.setTransportMode(travelRequestsDTO.getTransportMode());
		travelRequestsVO.setAccommodation(travelRequestsDTO.getAccommodation());
		travelRequestsVO.setEstimatedCost(travelRequestsDTO.getEstimatedCost());
		travelRequestsVO.setBusinessPurpose(travelRequestsDTO.getBusinessPurpose());
		travelRequestsVO.setBranchCode(travelRequestsDTO.getBranchCode());
		travelRequestsVO.setBranch(travelRequestsDTO.getBranch());
		travelRequestsVO.setCreatedBy(travelRequestsDTO.getCreatedBy());
		travelRequestsVO.setOrgId(travelRequestsDTO.getOrgId());
		travelRequestsVO.setReportingPerson(employeeVO.getReportingPerson());
		travelRequestsVO.setReportingPersonCode(employeeVO.getReportingPersonCode());
		travelRequestsVO.setReportingPersonEmail(employeeVO.getReportingPersonEmail());
		travelRequestsVO.setApproveStatus("PENDING");
	}

	@Override
	public List<TravelRequestsVO> getTravelRequestsByOrgId(Long orgId, String branchCode, String employeeCode) {
		// TODO Auto-generated method stub
		return travelRequestsRepo.getTravelRequestsByOrgId(orgId, branchCode, employeeCode);
	}

	@Override
	public TravelRequestsVO getTravelRequestsById(Long id) {
		return travelRequestsRepo.getTravelRequestsById(id);
	}

	@Override
	public Map<String, Object> createApprovalExpenseClaims(Long orgId, Long id, String employeeCode, String action,
			String actionBy, String notifyCode, String notify, String screenName, String email,
			BigDecimal approvalAmount) throws Exception {

		// Response map
		Map<String, Object> response = new HashMap<>();
		String message = "";

		// 1️⃣ Fetch increment management record
		ExpenseClaimsVO expenseClaimsVO = expenseClaimsRepo.findByOrgIdAndIdAndEmployeeCode(orgId, id, employeeCode);

		if (expenseClaimsVO == null) {
			throw new ApplicationException("ExpenseClaims record not found for the given details.");
		}

		// 2️⃣ Check if already approved or rejected
		String currentStatus = expenseClaimsVO.getApproveStatus();
		if (currentStatus != null
				&& (currentStatus.equalsIgnoreCase("Approved") || currentStatus.equalsIgnoreCase("Rejected"))) {
			throw new ApplicationException("This ExpenseClaims is already " + currentStatus + ".");
		}

		// 3️⃣ Proceed only if action is valid
		if ("APPROVED".equalsIgnoreCase(action)) {
			expenseClaimsVO.setApprovedAmount(approvalAmount);
			message = "Approved Successfully";

		} else if ("REJECTED".equalsIgnoreCase(action)) {
			// Just mark as rejected
			message = "Rejected Successfully";

		} else {
			throw new ApplicationException("Invalid action: must be APPROVED or REJECTED.");
		}

		// 4️⃣ Update approval details
		expenseClaimsVO.setApproveStatus(action);
		expenseClaimsVO.setApproveBy(actionBy);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
		expenseClaimsVO.setApproveOn(LocalDateTime.now().format(formatter).toUpperCase());

		expenseClaimsRepo.save(expenseClaimsVO);

		// 5️⃣ Prepare response
		response.put("expenseClaimsVO", expenseClaimsVO);
		response.put("message", message);
		return response;
	}

	@Override
	public Map<String, Object> createApprovalTravelRequests(Long orgId, Long id, String employeeCode, String action,
			String actionBy, String notifyCode, String notify, String screenName, String email,
			BigDecimal approvedAmount) throws Exception {

		// Response map
		Map<String, Object> response = new HashMap<>();
		String message = "";

		// 1️⃣ Fetch increment management record
		TravelRequestsVO travelRequestsVO = travelRequestsRepo.findByOrgIdAndIdAndEmployeeCode(orgId, id, employeeCode);

		if (travelRequestsVO == null) {
			throw new ApplicationException("TravelRequests record not found for the given details.");
		}

		// 2️⃣ Check if already approved or rejected
		String currentStatus = travelRequestsVO.getApproveStatus();
		if (currentStatus != null
				&& (currentStatus.equalsIgnoreCase("Approved") || currentStatus.equalsIgnoreCase("Rejected"))) {
			throw new ApplicationException("This TravelRequests is already " + currentStatus + ".");
		}

		// 3️⃣ Proceed only if action is valid
		if ("APPROVED".equalsIgnoreCase(action)) {
			travelRequestsVO.setApprovedAmount(approvedAmount);
			message = "Approved Successfully";

		} else if ("REJECTED".equalsIgnoreCase(action)) {
			// Just mark as rejected
			message = "Rejected Successfully";

		} else {
			throw new ApplicationException("Invalid action: must be APPROVED or REJECTED.");
		}

		// 4️⃣ Update approval details
		travelRequestsVO.setApproveStatus(action);
		travelRequestsVO.setApproveBy(actionBy);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
		travelRequestsVO.setApproveOn(LocalDateTime.now().format(formatter).toUpperCase());

		travelRequestsRepo.save(travelRequestsVO);

		// 5️⃣ Prepare response
		response.put("travelRequestsVO", travelRequestsVO);
		response.put("message", message);
		return response;
	}

	@Override
	public List<TravelRequestsVO> getTravelRequestsForDashBoard(Long orgId, String reportingPersonCode,
			String branchCode) {
		return travelRequestsRepo.getTravelRequestsForDashBoard(orgId, reportingPersonCode, branchCode);
	}

	@Override
	public List<ExpenseClaimsVO> getExpenseClaimsForDashBoard(Long orgId, String reportingPersonCode,
			String branchCode) {
		return expenseClaimsRepo.getExpenseClaimsForDashBoard(orgId, reportingPersonCode, branchCode);
	}

	@Override
	public ExpenseClaimsVO uploadExpenseClaimsImageInBloob(MultipartFile file, Long id)
			throws IOException, java.io.IOException {
		ExpenseClaimsVO expenseClaimsVO = expenseClaimsRepo.findById(id).get();
		if (file != null && !file.isEmpty()) {
			expenseClaimsVO.setExpenseAttachment(file.getBytes());

		}
		return expenseClaimsRepo.save(expenseClaimsVO);
	}

	@Override
	public List<Map<String, Object>> getApprovalExpenseAndTravelByOrgId(Long orgId, String branchCode,
			String employeeCode) {
		List<Object[]> results = expenseClaimsRepo.getApprovalExpenseAndTravelByOrgId(orgId, branchCode, employeeCode);
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] row : results) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", row[0] != null ? row[0] : "");
			map.put("type", row[1] != null ? row[1] : "");
			map.put("title", row[2] != null ? row[2] : "");
			map.put("employeeName", row[3] != null ? row[3] : "");
			map.put("employeeCode", row[4] != null ? row[4] : "");
			map.put("amount", row[5] != null ? row[5] : "");
			map.put("submitted", row[6] != null ? row[6] : "");
			map.put("status", row[7] != null ? row[7] : "");
			map.put("expenseLimit", row[8] != null ? row[8] : "");
			map.put("attachment", row[9] != null ? row[9] : "");

			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getExpenseCountByOrgId(Long orgId, String branchCode, String employeeCode,
			Long month, Long year) {
		List<Object[]> results = expenseClaimsRepo.getExpenseCountByOrgId(orgId, branchCode, employeeCode, month, year);
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] row : results) {
			Map<String, Object> map = new HashMap<>();
//	            map.put("pending", row[0]);
//	            map.put("approved", row[1]);
//	            map.put("rejected", row[2]);
//	            map.put("totalCount", row[3]);
//	            map.put("totalAmount", row[4]);
			map.put("expensePending", row[0] != null ? row[0] : "");
			map.put("expenseApproved", row[1] != null ? row[1] : "");
			map.put("expenseRejected", row[2] != null ? row[2] : "");
			map.put("expenseTotalCount", row[3] != null ? row[3] : "");
			map.put("expenseAmount", row[4] != null ? row[4] : "");
			map.put("travelPending", row[5] != null ? row[5] : "");
			map.put("travelApproved", row[6] != null ? row[6] : "");
			map.put("travelRejected", row[7] != null ? row[7] : "");
			map.put("travelTotalCount", row[8] != null ? row[8] : "");
			map.put("travelAmount", row[9] != null ? row[9] : "");

			list.add(map);
		}

		return list;
	}

	@Override
	public Map<String, List<Map<String, Object>>> getExpenseGraphByOrgId(Long orgId, String branchCode,
			String employeeCode, Long year, Long month) {
		List<Object[]> results = expenseClaimsRepo.getExpenseGraphByOrgId(orgId, branchCode, employeeCode, year, month);
		Map<String, List<Map<String, Object>>> response = new LinkedHashMap<>();

		String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

		for (Object[] row : results) {
			String category = (String) row[0];
			for (int i = 1; i <= 12; i++) {
				BigDecimal amount = (row[i] != null) ? new BigDecimal(row[i].toString()) : BigDecimal.ZERO;
				if (amount.compareTo(BigDecimal.ZERO) > 0) {
					Map<String, Object> detail = new LinkedHashMap<>();
					detail.put("category", category);
					detail.put("amount", amount);

					response.computeIfAbsent(months[i - 1], k -> new ArrayList<>()).add(detail);
				}
			}
		}
		return response;
	}

	// AssetReturn

	@Override
	public Map<String, Object> CreateUpdateAssetReturn(AssetReturnDTO assetReturnDTO) throws ApplicationException {

		AssetReturnVO assetReturnVO = new AssetReturnVO();
		String message;

		if (ObjectUtils.isNotEmpty(assetReturnDTO.getId())) {
			assetReturnVO = assetReturnRepo.findById(assetReturnDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid AssetReturn details"));

			assetReturnVO.setUpdatedBy(assetReturnDTO.getCreatedBy());
			createUpdateAssetReturnVOByAssetReturnDTO(assetReturnVO, assetReturnDTO);
			assetReturnRepo.save(assetReturnVO);

			message = "AssetReturn Updated Successfully";
		} else {

			assetReturnVO.setCreatedBy(assetReturnDTO.getCreatedBy());
			assetReturnVO.setUpdatedBy(assetReturnDTO.getCreatedBy());

			createUpdateAssetReturnVOByAssetReturnDTO(assetReturnVO, assetReturnDTO);
			assetReturnRepo.save(assetReturnVO);

			AssetMasterVO assetMasterVO = assetMasterRepo.findByAssetNameAndAssetCode(assetReturnDTO.getAssetName(),
					assetReturnDTO.getAssetCode());

			if (assetMasterVO == null) {
				throw new ApplicationContextException("No data found for AssetName and AssetCode");
			}

			// Prepare two stock entries
			AssetStockVO stockOut = new AssetStockVO(); // qty = -1
			AssetStockVO stockIn = new AssetStockVO(); // qty = 1

			// --------------------
			// STOCK OUT (Employee → Back to Store)
			// --------------------
			stockOut.setQty(-1);
			stockOut.setAStatus("A"); // A = return from employee
			stockOut.setLocation(assetReturnVO.getEmployeeName());
			stockOut.setLocationCode(assetReturnVO.getEmployeeCode());

			// --------------------
			// STOCK IN (Return to Store)
			// --------------------
			stockIn.setQty(1);
			stockIn.setAStatus("S"); // S = move to store
			stockIn.setLocation(assetMasterVO.getLocation());

			// Add both to list
			List<AssetStockVO> list = Arrays.asList(stockOut, stockIn);

			// --------------------
			// Apply COMMON FIELDS using FOR-EACH LOOP
			// --------------------
			for (AssetStockVO vo : list) {

				vo.setAssetName(assetReturnVO.getAssetName());
				vo.setAssetCode(assetReturnVO.getAssetCode());
				vo.setBranch(assetReturnVO.getBranch());
				vo.setBranchCode(assetReturnVO.getBranchCode());
				vo.setFinyear(assetReturnVO.getFinyear());
				vo.setSourceId(assetReturnVO.getId());
				vo.setCreatedBy(assetReturnVO.getCreatedBy());
				vo.setUpdatedBy(assetReturnVO.getUpdatedBy());
				vo.setSourceScreen(assetReturnVO.getScreenName());
				vo.setSourceScreenCode(assetReturnVO.getScreenCode());
				vo.setOrgId(assetReturnVO.getOrgId());

				// from master data
				vo.setSerialNumber(assetMasterVO.getSerialNumber());
				vo.setCategory(assetMasterVO.getCategory());
				vo.setBrand(assetMasterVO.getBrand());
				vo.setModel(assetMasterVO.getModel());
				vo.setAssetStatus("A");

				assetStockRepo.save(vo);
			}

		}
		message = "AssetReturn Created Successfully";

		Map<String, Object> response = new HashMap<>();
		response.put("assetReturnVO", assetReturnVO);
		response.put("message", message);
		return response;

	}

	private void createUpdateAssetReturnVOByAssetReturnDTO(AssetReturnVO assetReturnVO, AssetReturnDTO assetReturnDTO) {

		AssetMasterVO assetMasterVO = assetMasterRepo.findByAssetNameAndAssetCode(assetReturnDTO.getAssetName(),
				assetReturnDTO.getAssetCode());

		if (assetMasterVO == null) {
			throw new ApplicationContextException("No data found for AssetName and AssetCode");
		}

		assetReturnVO.setAssetName(assetReturnDTO.getAssetName());
		assetReturnVO.setAssetCode(assetReturnDTO.getAssetCode());
		assetReturnVO.setBranch(assetReturnDTO.getBranch());
		assetReturnVO.setBranchCode(assetReturnDTO.getBranchCode());
		assetReturnVO.setFinyear(assetReturnDTO.getFinyear());
		assetReturnVO.setCreatedBy(assetReturnDTO.getCreatedBy());
		assetReturnVO.setOrgId(assetReturnDTO.getOrgId());
		assetReturnVO.setEmployeeCode(assetReturnDTO.getEmployeeCode());
		assetReturnVO.setEmployeeName(assetReturnDTO.getEmployeeName());
		assetReturnVO.setAllocationDate(assetReturnDTO.getAllocationDate());
		assetReturnVO.setExpectedreturndate(assetReturnDTO.getExpectedreturndate());
		assetReturnVO.setAssetcondition(assetReturnDTO.getAssetcondition());
		assetReturnVO.setAllocationnotes(assetReturnDTO.getAllocationnotes());

		assetReturnVO.setCategory(assetReturnDTO.getCategory());
		assetReturnVO.setBrand(assetReturnDTO.getBrand());
		assetReturnVO.setModel(assetReturnDTO.getModel());
		assetReturnVO.setSerialNumber(assetReturnDTO.getSerialNumber());
		assetReturnVO.setPurchaseDate(assetReturnDTO.getPurchaseDate());

		assetReturnVO.setPurchaseCost(assetReturnDTO.getPurchaseCost());
		assetReturnVO.setWarrantyExpiry(assetReturnDTO.getWarrantyExpiry());
		assetReturnVO.setLocation(assetReturnDTO.getLocation());
		assetReturnVO.setNotes(assetReturnDTO.getNotes());
		assetReturnVO.setLocationCode(assetReturnDTO.getLocationCode());
	}

	@Override
	public List<AssetReturnVO> getAssetReturnByOrgId(Long orgId, String branchCode) {

		return assetReturnRepo.getAssetReturnByOrgId(orgId, branchCode);
	}

	@Override
	public AssetReturnVO getAssetReturnById(Long id) {
		return assetReturnRepo.getAssetReturnById(id);
	}

	@Override
	public List<Map<String, Object>> getAssetAllocationDetails(Long orgId, String branchCode) {
		List<Object[]> results = assetAllocationRepo.getAssetAllocationDetails(orgId, branchCode);
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] row : results) {
			Map<String, Object> map = new HashMap<>();
			map.put("assetCode", row[0] != null ? row[0] : "");
			map.put("assetName", row[1] != null ? row[1] : "");
			map.put("serialnumber", row[2] != null ? row[2] : "");
			map.put("category", row[3] != null ? row[3] : "");
			map.put("brand", row[4] != null ? row[4] : "");
			map.put("model", row[5] != null ? row[5] : "");
			map.put("total_qty", row[6] != null ? row[6] : "");

			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getAssetAllocationListAll(Long orgId, String branchCode, String employeeCode) {
		List<Object[]> results = assetAllocationRepo.getAssetAllocationListAll(orgId, branchCode, employeeCode);
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] row : results) {
			Map<String, Object> map = new HashMap<>();
			map.put("assetCode", row[0] != null ? row[0] : "");
			map.put("assetName", row[1] != null ? row[1] : "");
			map.put("serialnumber", row[2] != null ? row[2] : "");
			map.put("category", row[3] != null ? row[3] : "");
			map.put("brand", row[4] != null ? row[4] : "");
			map.put("model", row[5] != null ? row[5] : "");
			map.put("employeeCode", row[6] != null ? row[6] : "");
			map.put("employeeName", row[7] != null ? row[7] : "");
			map.put("qty", row[8] != null ? row[8] : "");
			list.add(map);
		}

		return list;
	}

	@Override
	public AssetMasterVO saveAsset(AssetMasterDTO dto) throws Exception {

		String screenCode = "AM";
		// Create new AssetMaster
		AssetMasterVO asset = new AssetMasterVO();

		String docId = documentTypeService.getDocid(dto.getBranchCode(), screenCode);
		asset.setAssetCode(docId);

		// GETDOCID LASTNO +1
		DocTypeMappingDetailsVO docTypeMappingDetailsVO = docTypeMappingDetailsRepo
				.findByBranchCodeAndScreenCode(dto.getBranchCode(), screenCode);
		docTypeMappingDetailsVO.setLastNo(docTypeMappingDetailsVO.getLastNo() + 1);
		docTypeMappingDetailsRepo.save(docTypeMappingDetailsVO);

		asset.setAssetName(dto.getAssetName());
//        asset.setAssetCode(dto.getAssetCode());
		asset.setCategory(dto.getCategory());
		asset.setBrand(dto.getBrand());
		asset.setModel(dto.getModel());
		asset.setSerialNumber(dto.getSerialNumber());
		asset.setPurchaseDate(dto.getPurchaseDate());
		asset.setPurchaseCost(dto.getPurchaseCost());
		asset.setWarrantyExpiry(dto.getWarrantyExpiry());
		asset.setLocation(dto.getLocation());
		asset.setNotes(dto.getNotes());
		asset.setActive(dto.isActive());
		asset.setBranch(dto.getBranch());
		asset.setBranchCode(dto.getBranchCode());
		asset.setFinyear(dto.getFinyear());
		asset.setOrgId(dto.getOrgId());
		asset.setCreatedBy(dto.getCreatedBy());
		asset.setUpdatedBy(dto.getCreatedBy());

		List<AssetImageVO> images = new ArrayList<>();

		// Save uploaded files to folder + DB
		if (dto.getFiles() != null) {
			File folder = new File(uploadDir);
			if (!folder.exists())
				folder.mkdirs();

			for (MultipartFile file : dto.getFiles()) {

				if (file != null && !file.isEmpty()) {

					String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
					Path path = Paths.get(uploadDir + "/" + fileName);

					// Save file to disk
					Files.write(path, file.getBytes());

					// Save record to DB
					AssetImageVO img = new AssetImageVO();
					img.setFileName(fileName);
					img.setAssetMaster(asset);

					images.add(img);
				}
			}
		}

		// Set images to master
		asset.setAssetImages(images);

		// Save master + images (cascade)
		AssetMasterVO savedAsset = assetMasterRepo.save(asset);

		// Create AssetStock after master saved
		AssetStockVO stock = new AssetStockVO();

		stock.setAssetName(savedAsset.getAssetName());
		stock.setAssetCode(savedAsset.getAssetCode());
		stock.setCategory(savedAsset.getCategory());
		stock.setBrand(savedAsset.getBrand());
		stock.setModel(savedAsset.getModel());
		stock.setSerialNumber(savedAsset.getSerialNumber());
		stock.setLocation(savedAsset.getLocation());
		stock.setBranch(savedAsset.getBranch());
		stock.setSourceScreen(savedAsset.getScreenName());
		stock.setSourceScreenCode(savedAsset.getScreenCode());
		stock.setBranchCode(savedAsset.getBranchCode());
		stock.setFinyear(savedAsset.getFinyear());
		stock.setSourceId(savedAsset.getId());
		stock.setCreatedBy(savedAsset.getCreatedBy());
		stock.setUpdatedBy(savedAsset.getUpdatedBy());
		stock.setOrgId(savedAsset.getOrgId());
		stock.setAssetStatus("A");
		stock.setAStatus("S");
		stock.setQty(1);

		assetStockRepo.save(stock);

		return savedAsset;
	}

	@Override
	public AssetMasterVO getAssetById(Long id) {
		return assetMasterRepo.findById(id).orElse(null);
	}

	@Override
	public byte[] viewImage(Long id) throws IOException {
		AssetImageVO asset = imageRepo.findById(id).orElse(null);

		if (asset == null || asset.getFileName() == null) {
			return null;
		}

		Path path = Paths.get(uploadDir + asset.getFileName());

		if (!Files.exists(path)) {
			throw new IOException("File not found on server");
		}

		return Files.readAllBytes(path);
	}

	public String getImageFileType(Long id) throws IOException {
		AssetImageVO asset = imageRepo.findById(id).orElse(null);
		if (asset == null)
			return null;

		Path path = Paths.get(uploadDir + asset.getFileName());
		return Files.probeContentType(path);
	}

	private int totalRows = 0;
	private int successfulUploads = 0;

	private final DataFormatter dataFormatter = new DataFormatter();

	@Transactional
	@Override
	public void excelUploadForAssetMaster(MultipartFile file, Long orgId, String createdBy, String branch,
			String branchCode, String finYear) throws ApplicationException {

		totalRows = 0;
		successfulUploads = 0;

		if (file.isEmpty()) {

			throw new ApplicationException("The supplied file '" + file.getOriginalFilename() + "' is empty.");
		}

		try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {

			Sheet sheet = workbook.getSheetAt(0);

			System.out.println("Processing file : " + file.getOriginalFilename());

			// HEADER VALIDATION
			Row headerRow = sheet.getRow(0);

			if (!isAssetHeaderValid(headerRow)) {

				throw new ApplicationException("Invalid Excel format. Expected headers : "
						+ "Asset Name, Category, Brand, Model, " + "Serial Number, Purchase Date, "
						+ "Purchase Cost, Warranty Expiry, " + "Location, Notes, Active");
			}

			// LOOP ROWS
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {

				Row row = sheet.getRow(i);

				if (row == null || isRowEmpty(row)) {

					continue;
				}

				totalRows++;

				System.out.println("Processing row : " + (i + 1));

				try {

					AssetMasterVO assetMasterVO = new AssetMasterVO();

					assetMasterVO.setAssetName(getStringCellValue(row.getCell(0)));

					assetMasterVO.setCategory(getStringCellValue(row.getCell(1)));

					assetMasterVO.setBrand(getStringCellValue(row.getCell(2)));

					assetMasterVO.setModel(getStringCellValue(row.getCell(3)));

					assetMasterVO.setSerialNumber(getStringCellValue(row.getCell(4)));

					assetMasterVO.setPurchaseDate(parseDate(row.getCell(5)));

					assetMasterVO.setPurchaseCost(getStringCellValue(row.getCell(6)));

					assetMasterVO.setWarrantyExpiry(parseDate(row.getCell(7)));

					assetMasterVO.setLocation(getStringCellValue(row.getCell(8)));

					assetMasterVO.setNotes(getStringCellValue(row.getCell(9)));

					assetMasterVO.setActive(getBooleanCellValue(row.getCell(10)));

					// COMMON PARAMS
					assetMasterVO.setOrgId(orgId);

					assetMasterVO.setBranch(branch);

					assetMasterVO.setBranchCode(branchCode);

					assetMasterVO.setFinyear(finYear);

					assetMasterVO.setCreatedBy(createdBy);

					assetMasterVO.setUpdatedBy(createdBy);

					// DOC ID
					String screenCode = "AM";

					String docId = documentTypeService.getDocid(branchCode, screenCode);

					assetMasterVO.setAssetCode(docId);

					assetMasterVO.setScreenCode(screenCode);

					assetMasterVO.setScreenName("Asset Master");

					// UPDATE LASTNO
					DocTypeMappingDetailsVO docTypeMappingDetailsVO = docTypeMappingDetailsRepo
							.findByBranchCodeAndScreenCode(branchCode, screenCode);

					if (docTypeMappingDetailsVO != null) {

						docTypeMappingDetailsVO.setLastNo(docTypeMappingDetailsVO.getLastNo() + 1);

						docTypeMappingDetailsRepo.save(docTypeMappingDetailsVO);
					}

					// SAVE ASSET MASTER
					assetMasterVO = assetMasterRepo.save(assetMasterVO);

					// SAVE STOCK
					AssetStockVO assetStockVO = new AssetStockVO();

					assetStockVO.setAssetName(assetMasterVO.getAssetName());

					assetStockVO.setAssetCode(assetMasterVO.getAssetCode());

					assetStockVO.setCategory(assetMasterVO.getCategory());

					assetStockVO.setBrand(assetMasterVO.getBrand());

					assetStockVO.setModel(assetMasterVO.getModel());

					assetStockVO.setSerialNumber(assetMasterVO.getSerialNumber());

					assetStockVO.setLocation(assetMasterVO.getLocation());

					assetStockVO.setBranch(assetMasterVO.getBranch());

					assetStockVO.setBranchCode(assetMasterVO.getBranchCode());

					assetStockVO.setFinyear(assetMasterVO.getFinyear());

					assetStockVO.setSourceId(assetMasterVO.getId());

					assetStockVO.setCreatedBy(assetMasterVO.getCreatedBy());

					assetStockVO.setUpdatedBy(assetMasterVO.getUpdatedBy());

					assetStockVO.setSourceScreen(assetMasterVO.getScreenName());

					assetStockVO.setSourceScreenCode(assetMasterVO.getScreenCode());

					assetStockVO.setOrgId(assetMasterVO.getOrgId());

					assetStockVO.setAssetStatus("A");

					assetStockVO.setQty(1);

					assetStockRepo.save(assetStockVO);

					successfulUploads++;

				} catch (Exception e) {

					System.err.println("Error at row " + (i + 1) + " : " + e.getMessage());
				}
			}

		} catch (IOException e) {

			throw new ApplicationException(
					"Failed to process file : " + file.getOriginalFilename() + " - " + e.getMessage());
		}

		System.out.println("Total Rows : " + totalRows);

		System.out.println("Successfully Uploaded : " + successfulUploads);
	}

	// ================= HELPER METHODS =================

	private boolean isAssetHeaderValid(Row headerRow) {

		if (headerRow == null) {

			return false;
		}

		List<String> expectedHeaders = Arrays.asList("asset name", "category", "brand", "model", "serial number",
				"purchase date", "purchase cost", "warranty expiry", "location", "notes", "active");

		List<String> actualHeaders = new ArrayList<>();

		for (Cell cell : headerRow) {

			actualHeaders.add(getStringCellValue(cell).toLowerCase());
		}

		return expectedHeaders.equals(actualHeaders);
	}

	private String getStringCellValue(Cell cell) {

		if (cell == null) {

			return "";
		}

		return dataFormatter.formatCellValue(cell).trim();
	}

	private boolean isRowEmpty(Row row) {

		for (Cell cell : row) {

			if (cell != null && cell.getCellType() != CellType.BLANK && !getStringCellValue(cell).isEmpty()) {

				return false;
			}
		}

		return true;
	}

	private LocalDate parseDate(Cell cell) {

		if (cell == null) {

			return null;
		}

		try {

			if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {

				return cell.getLocalDateTimeCellValue().toLocalDate();

			} else if (cell.getCellType() == CellType.STRING) {

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

				return LocalDate.parse(cell.getStringCellValue(), formatter);
			}

		} catch (Exception e) {

			System.err.println("Date parsing error : " + getStringCellValue(cell));
		}

		return null;
	}

	private Double getDoubleCellValue(Cell cell) {

		if (cell == null) {

			return null;
		}

		try {

			if (cell.getCellType() == CellType.NUMERIC) {

				return cell.getNumericCellValue();

			} else if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().trim().isEmpty()) {

				return Double.parseDouble(cell.getStringCellValue().trim());
			}

		} catch (Exception e) {

			System.err.println("Double parsing error : " + getStringCellValue(cell));
		}

		return null;
	}

	private Boolean getBooleanCellValue(Cell cell) {

		if (cell == null) {

			return false;
		}

		try {

			if (cell.getCellType() == CellType.BOOLEAN) {

				return cell.getBooleanCellValue();

			} else if (cell.getCellType() == CellType.STRING) {

				return Boolean.parseBoolean(cell.getStringCellValue().trim());
			}

		} catch (Exception e) {

			System.err.println("Boolean parsing error : " + getStringCellValue(cell));
		}

		return false;
	}

	public int getTotalRows() {

		return totalRows;
	}

	public int getSuccessfulUploads() {

		return successfulUploads;
	}

	@Override
	public List<AssetMasterVO> getAssetMasterReportDetails(Long orgId, String category, String branchCode,
			String fromDate, String toDate) {
		return assetMasterRepo.getAssetMasterReportDetails(orgId, category, branchCode, fromDate, toDate);
	}

	@Override
	public List<AssetAllocationVO> getAssetAllocationReportDetails(Long orgId, String employeeCode, String branchCode,
			String fromDate, String toDate) {
		return assetAllocationRepo.getAssetAllocationReportDetails(orgId, employeeCode, branchCode, fromDate, toDate);
	}

}
