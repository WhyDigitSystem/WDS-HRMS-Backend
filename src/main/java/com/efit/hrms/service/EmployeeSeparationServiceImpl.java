package com.efit.hrms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efit.hrms.dto.ClearanceDetailsDTO;
import com.efit.hrms.dto.ClearanceManagementDTO;
import com.efit.hrms.dto.DepartmentHeadDTO;
import com.efit.hrms.dto.ExitInterviewDTO;
import com.efit.hrms.dto.ExitInterviewDepartmentDTO;
import com.efit.hrms.dto.InitiateSeparationDTO;
import com.efit.hrms.dto.QuestionDTO;
import com.efit.hrms.dto.ReportingHeadDTO;
import com.efit.hrms.entity.ClearanceDetailsVO;
import com.efit.hrms.entity.ClearanceManagementVO;
import com.efit.hrms.entity.DepartmentHeadVO;
import com.efit.hrms.entity.EmployeeVO;
import com.efit.hrms.entity.ExitInterviewDepartmentVO;
import com.efit.hrms.entity.ExitInterviewVO;
import com.efit.hrms.entity.InitiateSeparationVO;
import com.efit.hrms.entity.NotificationVO;
import com.efit.hrms.entity.QuestionVO;
import com.efit.hrms.entity.ReportingHeadVO;
import com.efit.hrms.entity.UserVO;
import com.efit.hrms.exception.ApplicationException;
import com.efit.hrms.repo.AssetAllocationRepo;
import com.efit.hrms.repo.AssetStockRepo;
import com.efit.hrms.repo.ClearanceDetailsRepo;
import com.efit.hrms.repo.ClearanceManagementRepo;
import com.efit.hrms.repo.DepartmentHeadRepo;
import com.efit.hrms.repo.EmployeeRepo;
import com.efit.hrms.repo.ExitInterviewDepartmentRepo;
import com.efit.hrms.repo.InitiateSeparationRepo;
import com.efit.hrms.repo.NotificationRepo;
import com.efit.hrms.repo.QuestionRepo;
import com.efit.hrms.repo.ReportingHeadRepo;
import com.efit.hrms.repo.UserRepo;

@Service
public class EmployeeSeparationServiceImpl implements EmployeeSeparationService {

	private final EmployeeRepo employeeRepo;

	public static final Logger LOGGER = LoggerFactory.getLogger(EmployeeSeparationServiceImpl.class);

	@Autowired
	InitiateSeparationRepo initiateSeparationRepo;

	@Autowired
	ClearanceManagementRepo clearanceManagementRepo;
	
	@Autowired
	SeparationMailService separationMailService;
	
	@Autowired
	ExitInterviewDepartmentRepo exitInterviewDepartmentRepo; 
	
	@Autowired
	QuestionRepo questionRepo;
	
	@Autowired
	DepartmentHeadRepo departmentHeadRepo;
	
	@Autowired
	ClearanceDetailsRepo clearanceDetailsRepo;
	
	@Autowired
	ReportingHeadRepo reportingHeadRepo;
	
	@Autowired
	AssetAllocationRepo assetAllocationRepo;
	
	@Autowired
	AssetStockRepo assetStockRepo;
	
	@Autowired
	NotificationRepo notificationRepo;
	
	@Autowired
	UserRepo userRepo;

	@Value("${spring.mail.from}")
	private String fromEmail;
	
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

		// 3️⃣ Handle Exit Interview Child Table
		if (initiateSeparationVO.getExitInterviewVO() == null) {
		    initiateSeparationVO.setExitInterviewVO(new ArrayList<>());
		} else {
		    initiateSeparationVO.getExitInterviewVO().clear();
		}

		if (initiateSeparationDTO.getExitInterviewDTO() != null
		        && !initiateSeparationDTO.getExitInterviewDTO().isEmpty()) {

		    for (ExitInterviewDTO childDTO : initiateSeparationDTO.getExitInterviewDTO()) {

		        ExitInterviewVO childVO = new ExitInterviewVO();
		        childVO.setQuestions(childDTO.getQuestions());
		        childVO.setAnswer(childDTO.getAnswer());

		        // 🔥 IMPORTANT (set parent)
		        childVO.setInitiateSeparationVO(initiateSeparationVO);

		        initiateSeparationVO.getExitInterviewVO().add(childVO);
		    }
		}
		
		// 4️⃣ Save parent (Hibernate will cascade to children automatically)
		InitiateSeparationVO savedVO = initiateSeparationRepo.save(initiateSeparationVO);
		
		if(initiateSeparationDTO.getId() == null ) {
			createUpdateNotificationSeparation(initiateSeparationVO);
		}

		// 1️⃣ First Time HR Initiates Separation → Send Mail to Employee
		if (initiateSeparationDTO.getClearanceManagementDTO() == null
		        || initiateSeparationDTO.getClearanceManagementDTO().isEmpty()) {

		    String employeeEmail =
		            employeeRepo.getEmployeeEmail(initiateSeparationDTO.getEmployeeCode());

		    if (employeeEmail != null) {

		        separationMailService.sendSeparationMail(
		                employeeEmail,
		                initiateSeparationDTO.getEmployeeName()
		        );
		        savedVO.setEmployeeEmail(employeeEmail);
		        initiateSeparationRepo.save(savedVO);
		    }

		}
//
//		// 2️⃣ Employee Filled Clearance → Send Mail to HR
//		else {
//			String hrCode =
//					initiateSeparationRepo.getCreatedBy(initiateSeparationDTO.getEmployeeCode());
//
//		    String hrEmail =
//		            employeeRepo.getEmployeeEmail(hrCode);
//
//		    if (hrEmail != null) {
//
//		        separationMailService.sendClearanceCompletedMail(
//		                hrEmail,
//		                initiateSeparationDTO.getEmployeeName()
//		        );
//		    }
//		}
//		
		// 3️⃣ Exit interview completed → mail reporting persons
//		if (savedVO.getReportingPersonEmail() != null) {
//
//		    String[] emailArray = Arrays.stream(savedVO.getReportingPersonEmail().split(","))
//		            .map(String::trim)
//		            .filter(e -> e != null && !e.isEmpty())
//		            .toArray(String[]::new);
//
//		    if (emailArray.length > 0) {
//
//		        separationMailService.sendInterviewCompletedMail(
//		                emailArray,
//		                savedVO.getEmployeeName(),
//		                savedVO.getInterviewDate(),
//		                savedVO.getId()
//		        );
//		    }
//		}
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

//		EmployeeVO emplpoyeeVO = employeeRepo.findByEmployeeNameAndEmployeeCode(dto.getEmployeeName(),
//				dto.getEmployeeCode());
//
//		if (emplpoyeeVO == null) {
//			throw new ApplicationContextException("employeeDetails is null");
//		} else {
//			emplpoyeeVO.setActive(false);
//			employeeRepo.save(emplpoyeeVO);
//		}

		vo.setReportingManager(dto.getReportingManager());
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
		vo.setStatus("PENDING");
//        EmployeeVO employeeVO = employeeRepo.findByEmployeeCode(vo.getEmployeeCode());
//         employeeVO.setActive(false);
//         employeeRepo.save(employeeVO);
		vo.setReportingPerson(
		        dto.getReportingPerson() != null
		        ? String.join(",", dto.getReportingPerson())
		        : null
		);

		vo.setReportingPersonCode(
		        dto.getReportingPersonCode() != null
		        ? String.join(",", dto.getReportingPersonCode())
		        : null
		);

		vo.setReportingPersonEmail(
		        dto.getReportingPersonEmail() != null
		        ? String.join(",", dto.getReportingPersonEmail())
		        : null
		);

		vo.setInterviewDate(dto.getInterviewDate());
		vo.setExperienceRating(dto.getExperienceRating());
		vo.setExitInterviewFeedback(dto.getExitInterviewFeedback());

	}

private void createUpdateNotificationSeparation(InitiateSeparationVO initiateSeparationVO) {
		
	String msg = "Your Separation Process Started " 
	           + initiateSeparationVO.getEmployeeCode() 
	           + ". Please follow the HR instruction.";

	        NotificationVO n = new NotificationVO();
	        UserVO user = userRepo.findByEmployeeCodeAndOrgId(initiateSeparationVO.getEmployeeCode(),initiateSeparationVO.getOrgId());
	        n.setUserid(user.getId()); // ✅ CORRECT
	        n.setMessage(msg);
	        n.setCreatedBy(initiateSeparationVO.getCreatedBy());
	        n.setUpdatedBy(initiateSeparationVO.getCreatedBy());
	        n.setOrgId(initiateSeparationVO.getOrgId());
	        n.setNotificationType("Separation Created");

	        notificationRepo.save(n);
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
	public List<Map<String, Object>> getGeneralManagerByOrgId(Long orgId) {
		List<Object[]> results = employeeRepo.getGeneralManagerByOrgId(orgId);
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] row : results) {
			Map<String, Object> map = new HashMap<>();
			map.put("employeeName", row[0]);
			map.put("employeeCode", row[1]);
			map.put("designation", row[2]);

			list.add(map);
		}

		return list;
	}
	
	
	@Override
	public List<InitiateSeparationVO> getInitiateSeparationByDepartment(Long orgId, String branchCode,
			String department, String type,String empCode) {
		// TODO Auto-generated method stub
		return initiateSeparationRepo.getInitiateSeparationByDepartment(orgId, branchCode, department, type,empCode);
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
	
	@Override
	public List<InitiateSeparationVO> getInitiateSeparationByOrgIdforclearance(Long orgId, String branchCode,String empCode) {
		// TODO Auto-generated method stub
		return initiateSeparationRepo.getInitiateSeparationByOrgIdforclearance(orgId, branchCode,empCode);
	}
	
	   @Override
	   public String updateSeparationStatus(Long id, String status) {

		    InitiateSeparationVO vo =
		            initiateSeparationRepo.findById(id)
		            .orElseThrow(() -> new RuntimeException("Separation not found"));

		    if("APPROVED".equals(vo.getStatus())) {
		        return "This separation request is already approved.";
		    }

		    if("REJECTED".equals(vo.getStatus())) {
		        return "This separation request is already rejected.";
		    }

		    vo.setStatus(status);
            EmployeeVO employeeVO = employeeRepo.findByEmployeeCode(vo.getEmployeeCode());
            employeeVO.setActive(false);
            employeeRepo.save(employeeVO);
            initiateSeparationRepo.save(vo);

		    if("APPROVED".equals(status)) {
		        return "Separation approved successfully.";
		    } else {
		        return "Separation rejected successfully.";
		    }
		}
	   
	   @Override
		@Transactional
		public Map<String, Object> createUpdateExitInterviewQuestion(ExitInterviewDepartmentDTO exitInterviewDepartmentDTO)
				throws ApplicationException {

			String methodName = "createUpdateExitInterviewQuestion()";
			LOGGER.debug("Starting method: {}", methodName);

			Map<String, Object> response = new LinkedHashMap<>();
			ExitInterviewDepartmentVO exitInterviewDepartmentVO;
			String message;

			// 1️⃣ Create or Update
			if (exitInterviewDepartmentDTO.getId() != null && exitInterviewDepartmentDTO.getId() > 0) {
				exitInterviewDepartmentVO = exitInterviewDepartmentRepo.findById(exitInterviewDepartmentDTO.getId())
						.orElseThrow(() -> new ApplicationException(
								"Error: ExitInterviewDepartment ID " + exitInterviewDepartmentDTO.getId() + " not found!"));
				exitInterviewDepartmentVO.setUpdatedBy(exitInterviewDepartmentDTO.getCreatedBy());

				message = "ExitInterviewDepartment Updated Successfully";
			} else {
				exitInterviewDepartmentVO = new ExitInterviewDepartmentVO();
				exitInterviewDepartmentVO.setCreatedBy(exitInterviewDepartmentDTO.getCreatedBy());
				exitInterviewDepartmentVO.setUpdatedBy(exitInterviewDepartmentDTO.getCreatedBy());
				message = "ExitInterviewDepartment Created Successfully";
			}

			// 2️⃣ Map fields
			mapExitInterviewDepartmentDTOtoExitInterviewDepartmentEntity(exitInterviewDepartmentDTO, exitInterviewDepartmentVO);

			// 3️⃣ Handle Child Table (Safely for Hibernate)
			if (exitInterviewDepartmentVO.getQuestionVO() == null) {
				exitInterviewDepartmentVO.setQuestionVO(new ArrayList<>());
			} else {
//				exitInterviewDepartmentVO.getQuestionVO().clear();
				List<QuestionVO> questionVO = questionRepo.findByExitInterviewDepartmentVO(exitInterviewDepartmentVO);
				questionRepo.deleteAll(questionVO);

			}

			if (exitInterviewDepartmentDTO.getQuestionDTO() != null
					&& !exitInterviewDepartmentDTO.getQuestionDTO().isEmpty()) {

				for (QuestionDTO questionDTO : exitInterviewDepartmentDTO.getQuestionDTO()) {
					QuestionVO questionVO = new QuestionVO();
					questionVO.setQuestion(questionDTO.getQuestion());
					questionVO.setExitInterviewDepartmentVO(exitInterviewDepartmentVO);
					exitInterviewDepartmentVO.getQuestionVO().add(questionVO);
				}
			}

			// 4️⃣ Save parent (Hibernate will cascade to children automatically)
			ExitInterviewDepartmentVO savedVO = exitInterviewDepartmentRepo.save(exitInterviewDepartmentVO);

			
			response.put("statusFlag", "Ok");
			response.put("status", true);

			Map<String, Object> paramObjectsMap = new LinkedHashMap<>();
			paramObjectsMap.put("message", message);
			paramObjectsMap.put("exitInterviewDepartmentVO", savedVO);
			response.put("paramObjectsMap", paramObjectsMap);

			LOGGER.debug("Ending method: {}", methodName);
			return response;
		}

		private void mapExitInterviewDepartmentDTOtoExitInterviewDepartmentEntity(ExitInterviewDepartmentDTO dto, ExitInterviewDepartmentVO exitInterviewDepartmentVO) {
			exitInterviewDepartmentVO.setDesignation(dto.getDesignation());
			exitInterviewDepartmentVO.setDesignationCode(dto.getDesignationCode());
			exitInterviewDepartmentVO.setBranchCode(dto.getBranchCode());
			exitInterviewDepartmentVO.setBranch(dto.getBranch());
			exitInterviewDepartmentVO.setOrgId(dto.getOrgId());
			exitInterviewDepartmentVO.setActive(dto.isActive());

		}
		
		@Override
		public ExitInterviewDepartmentVO getExitInterviewDepartmentById(Long id) {
			return exitInterviewDepartmentRepo.getExitInterviewDepartmentById(id);
		}

		@Override
		public List<ExitInterviewDepartmentVO> getExitInterviewDepartmentVOByOrgId(Long orgId, String branchCode) {
			// TODO Auto-generated method stub
			return exitInterviewDepartmentRepo.getExitInterviewDepartmentVOByOrgId(orgId, branchCode);
		}
		
		@Override
		public List<ExitInterviewDepartmentVO> getExitInterviewBasedOnDesignation(Long orgId, String branchCode,String designation) {
			// TODO Auto-generated method stub
			return exitInterviewDepartmentRepo.getExitInterviewBasedOnDesignation(orgId, branchCode,designation);
		}
		
		
		@Override
		@Transactional
		public List<ExitInterviewDepartmentVO> uploadExitInterviewExcel(
		        MultipartFile file,
		        String branch,
		        String branchCode,
		        Long orgId,
		        String createdBy) throws Exception {

		    List<ExitInterviewDepartmentVO> result = new ArrayList<>();

		    Workbook workbook = new XSSFWorkbook(file.getInputStream());
		    Sheet sheet = workbook.getSheetAt(0);

		    // ✅ Row 2 → Parent
		    Row parentRow = sheet.getRow(1);

		    ExitInterviewDepartmentVO entity = new ExitInterviewDepartmentVO();

		    entity.setDesignation(getCellValue(parentRow.getCell(0)));
		    entity.setDesignationCode(getCellValue(parentRow.getCell(1)));

		    // ✅ Set from API params
		    entity.setBranch(branch);
		    entity.setBranchCode(branchCode);
		    entity.setOrgId(orgId);
		    entity.setCreatedBy(createdBy);
		    entity.setUpdatedBy(createdBy);
		    entity.setActive(true);
		    entity.setCancel(false);

		    // ✅ Child questions
		    List<QuestionVO> questions = new ArrayList<>();

		    for (int i = 3; i <= sheet.getLastRowNum(); i++) {

		        Row row = sheet.getRow(i);
		        if (row == null) continue;

		        String value = getCellValue(row.getCell(0));

		        if (value == null || value.trim().isEmpty()) continue;

		        if (value.equalsIgnoreCase("questions")) continue;

		        QuestionVO q = new QuestionVO();
		        q.setQuestion(value.trim());
		        q.setExitInterviewDepartmentVO(entity); // 🔥 important

		        questions.add(q);
		    }

		    entity.setQuestionVO(questions);

		    // ✅ SAVE
		    ExitInterviewDepartmentVO saved = exitInterviewDepartmentRepo.save(entity);

		    result.add(saved);

		    workbook.close();

		    return result;
		}
		
		private String getCellValue(Cell cell) {

		    if (cell == null) return "";

		    switch (cell.getCellType()) {
		        case STRING:
		            return cell.getStringCellValue();

		        case NUMERIC:
		            return String.valueOf((long) cell.getNumericCellValue());

		        case BOOLEAN:
		            return String.valueOf(cell.getBooleanCellValue());

		        default:
		            return "";
		    }
		}
		
		
		 @Override
			@Transactional
			public Map<String, Object> createUpdateDepartmentHeadDTO(DepartmentHeadDTO departmentHeadDTO)
					throws ApplicationException {

				String methodName = "createUpdateDepartmentHeadDTO()";
				LOGGER.debug("Starting method: {}", methodName);

				Map<String, Object> response = new LinkedHashMap<>();
				DepartmentHeadVO departmentHeadVO;
				String message;

				// 1️⃣ Create or Update
				if (departmentHeadDTO.getId() != null && departmentHeadDTO.getId() > 0) {
					departmentHeadVO = departmentHeadRepo.findById(departmentHeadDTO.getId())
							.orElseThrow(() -> new ApplicationException(
									"Error: departmentHead ID " + departmentHeadDTO.getId() + " not found!"));
					departmentHeadVO.setUpdatedBy(departmentHeadDTO.getCreatedBy());
					List<ClearanceDetailsVO> clearanceDetailsVO = clearanceDetailsRepo.findByDepartmentHeadVO(departmentHeadVO);
					clearanceDetailsRepo.deleteAll(clearanceDetailsVO);
					List<ReportingHeadVO> reportingHeadVO = reportingHeadRepo.findByDepartmentHeadVO(departmentHeadVO);
					reportingHeadRepo.deleteAll(reportingHeadVO);

					message = "DepartmentHead Updated Successfully";
				} else {
					departmentHeadVO = new DepartmentHeadVO();
					departmentHeadVO.setCreatedBy(departmentHeadDTO.getCreatedBy());
					departmentHeadVO.setUpdatedBy(departmentHeadDTO.getCreatedBy());
					message = "DepartmentHead Created Successfully";
				}

				// 2️⃣ Map fields
				mapDepartmentHeadDTOtoDepartmentHeadEntity(departmentHeadDTO, departmentHeadVO);

				// 3️⃣ Handle Child Table (Safely for Hibernate)
//				if (departmentHeadVO.getClearanceDetailsVO() == null) {
//					departmentHeadVO.setClearanceDetailsVO(new ArrayList<>());
//				} else {
					

//				}
				
//				if (departmentHeadVO.getReportingHeadVO() == null) {
//					departmentHeadVO.setReportingHeadVO(new ArrayList<>());
//				} else {
//					exitInterviewDepartmentVO.getQuestionVO().clear();
					

//				}
				
				if (departmentHeadDTO.getClearanceDetailsDTO() != null
						&& !departmentHeadDTO.getClearanceDetailsDTO().isEmpty()) {

					for (ClearanceDetailsDTO clearanceDetailsDTO : departmentHeadDTO.getClearanceDetailsDTO()) {
						ClearanceDetailsVO clearanceDetailsVO = new ClearanceDetailsVO();
						clearanceDetailsVO.setClearanceName(clearanceDetailsDTO.getClearanceName());
						clearanceDetailsVO.setDepartmentHeadVO(departmentHeadVO);
						departmentHeadVO.getClearanceDetailsVO().add(clearanceDetailsVO);
					}
				}

				if (departmentHeadDTO.getReportingHeadDTO() != null
						&& !departmentHeadDTO.getReportingHeadDTO().isEmpty()) {

					for (ReportingHeadDTO reportingHeadDTO : departmentHeadDTO.getReportingHeadDTO()) {
						ReportingHeadVO reportingHeadVO = new ReportingHeadVO();
						reportingHeadVO.setEmployee(reportingHeadDTO.getEmployee());
						reportingHeadVO.setEmployeeCode(reportingHeadDTO.getEmployeeCode());
						reportingHeadVO.setEmployeeEmail(reportingHeadDTO.getEmployeeEmail());
						reportingHeadVO.setDepartmentHeadVO(departmentHeadVO);
						departmentHeadVO.getReportingHeadVO().add(reportingHeadVO);
					}
				}

				// 4️⃣ Save parent (Hibernate will cascade to children automatically)
				DepartmentHeadVO savedVO = departmentHeadRepo.save(departmentHeadVO);

				
				response.put("statusFlag", "Ok");
				response.put("status", true);

				Map<String, Object> paramObjectsMap = new LinkedHashMap<>();
				paramObjectsMap.put("message", message);
				paramObjectsMap.put("departmentHeadVO", savedVO);
				response.put("paramObjectsMap", paramObjectsMap);

				LOGGER.debug("Ending method: {}", methodName);
				return response;
			}

			private void mapDepartmentHeadDTOtoDepartmentHeadEntity(DepartmentHeadDTO dto, DepartmentHeadVO vo) {
				vo.setDepartment(dto.getDepartment());
				vo.setDepartmentCode(dto.getDepartmentCode());
				vo.setBranchCode(dto.getBranchCode());
				vo.setBranch(dto.getBranch());
				vo.setOrgId(dto.getOrgId());

			}
		
			@Override
			public List<Map<String, Object>> getEmployeeforDepartmentHeadByOrgId(Long orgId,String department,String branchCode) {
				List<Object[]> results = employeeRepo.getEmployeeforDepartmentHeadByOrgId(orgId,department,branchCode);
				List<Map<String, Object>> list = new ArrayList<>();

				for (Object[] row : results) {
					Map<String, Object> map = new HashMap<>();
					map.put("employeeName", row[0]);
					map.put("employeeCode", row[1]);
					map.put("email", row[2]);

					list.add(map);
				}

				return list;
			}
			
			
			@Override
			public DepartmentHeadVO getDepartmentHeadById(Long id) {
				return departmentHeadRepo.getDepartmentHeadById(id);
			}

			@Override
			public List<DepartmentHeadVO> getDepartmentHeadByOrgId(Long orgId, String branchCode) {
				// TODO Auto-generated method stub
				return departmentHeadRepo.getDepartmentHeadByOrgId(orgId, branchCode);
			}
			
			@Override
			public List<Map<String, Object>> getCleranceDetailsByEmployeeCode(String employeeCode,Long orgId, String branchCode) {
				List<Object[]> results = initiateSeparationRepo.getCleranceDetailsByEmployeeCode(employeeCode,orgId, branchCode);
				List<Map<String, Object>> list = new ArrayList<>();

				for (Object[] row : results) {
					Map<String, Object> map = new HashMap<>();
					map.put("clearanceItem", row[0]);
					map.put("department", row[1]);
					map.put("departmentCode", row[2]);

					list.add(map);
				}

				return list;
			}
			
			@Override
			public List<Map<String, Object>> getAccessoriesByEmployeeCode(String employeeCode,Long orgId,String department, String branchCode) {
				List<Object[]> results = assetAllocationRepo.getAccessoriesByEmployeeCode(employeeCode,orgId,department, branchCode);
				List<Map<String, Object>> list = new ArrayList<>();

				for (Object[] row : results) {
					Map<String, Object> map = new HashMap<>();
					map.put("employeeName", row[0]);
					map.put("employeeCode", row[1]);
					map.put("assetName", row[2]);
					map.put("assetcode", row[3]);
					map.put("serialnumber", row[4]);

					list.add(map);
				}

				return list;
			}
			
			@Override
			public List<Map<String, Object>> getStatusForClearance(String employeeCode,Long orgId, String branchCode) {
				List<Object[]> results = assetStockRepo.getStatusForClearance(employeeCode,orgId, branchCode);
				List<Map<String, Object>> list = new ArrayList<>();

				for (Object[] row : results) {
					Map<String, Object> map = new HashMap<>();
					map.put("employeeCode", row[0]);
					map.put("employeeName", row[1]);
					map.put("qty", row[2]);
					map.put("status", row[3]);


					list.add(map);
				}

				return list;
			}
			
			
			@Override
			public List<Map<String, Object>> getAssetAllocationDetailsForClearance(String employeeCode,Long orgId, String branchCode,String department) {
				List<Object[]> results = assetStockRepo.getAssetAllocationDetailsForClearance(employeeCode,orgId, branchCode,department);
				List<Map<String, Object>> list = new ArrayList<>();

				for (Object[] row : results) {
					Map<String, Object> map = new HashMap<>();
					map.put("employeeCode", row[0]);
					map.put("employeeName", row[1]);
					map.put("assetName", row[2]);

					list.add(map);
				}

				return list;
			}
			
			@Override
			public List<Map<String, Object>> getAssetReturnForClearance(String employeeCode,Long orgId, String branchCode,String department) {
				List<Object[]> results = assetStockRepo.getAssetReturnForClearance(employeeCode,orgId, branchCode,department);
				List<Map<String, Object>> list = new ArrayList<>();

				for (Object[] row : results) {
					Map<String, Object> map = new HashMap<>();
					map.put("employeeCode", row[0]);
					map.put("employeeName", row[1]);
					map.put("assetName", row[2]);

					list.add(map);
				}

				return list;
			}
			
			@Override
			public List<Map<String, Object>> getSeparationEmployeeByOrgId(Long orgId) {
				return employeeRepo.getSeparationEmployeeByOrgId(orgId);
			}

			
}
