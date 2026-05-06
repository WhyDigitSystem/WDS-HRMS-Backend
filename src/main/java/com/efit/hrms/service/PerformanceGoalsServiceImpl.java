package com.efit.hrms.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efit.hrms.dto.PerformanceGoalsDTO;
import com.efit.hrms.dto.PerformanceGoalsDetailsDTO;
import com.efit.hrms.entity.PerformanceGoalsDetailsVO;
import com.efit.hrms.entity.PerformanceGoalsVO;
import com.efit.hrms.exception.ApplicationException;
import com.efit.hrms.repo.PerformanceGoalsDetailsRepo;
import com.efit.hrms.repo.PerformanceGoalsRepo;

@Service
public class PerformanceGoalsServiceImpl implements PerformanceGoalsService {

	public static final Logger LOGGER = LoggerFactory.getLogger(PerformanceGoalsServiceImpl.class);

	@Autowired
	PerformanceGoalsRepo performanceGoalsRepo;

	@Autowired
	PerformanceGoalsDetailsRepo performanceGoalsDetailsRepo;

//	@Override
//	public Map<String, Object> createUpdatePreGoals(PreGoalsDtlDTO preGoalsDtlDTO)
//			throws IOException, ApplicationException {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public Map<String, Object> createUpdatePerformanceGoals(PerformanceGoalsDTO performanceGoalsDTO)
			throws ApplicationException {

		String message;

		PerformanceGoalsVO performanceGoalsVO = null;

		if (ObjectUtils.isEmpty(performanceGoalsDTO.getId())) {


			 // 🔥 Get latest record
	        Optional<PerformanceGoalsVO> lastRecord =
	                performanceGoalsRepo
	                        .findTopByEmpCodeOrderByCreatedUpdatedDateCreatedonDesc(
	                                performanceGoalsDTO.getEmpCode());

	        if (lastRecord.isPresent()) {

	        	String createdOnStr =
	        			 lastRecord.get()
	        			           .getCreatedUpdatedDate()
	        			           .getCreatedon();

	        			DateTimeFormatter formatter =
	        			 DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");

	        			LocalDateTime lastCreated =
	        			 LocalDateTime.parse(createdOnStr, formatter);

	            LocalDateTime nextAllowedDate =
	                    lastCreated.plusMonths(6);

	            if (LocalDateTime.now().isBefore(nextAllowedDate)) {

	                throw new ApplicationException(
	                        "Performance Goals can be created only once in 6 months. "
	                      + "Next allowed after : " + nextAllowedDate.toLocalDate());
	            }
	        }

			
			performanceGoalsVO = new PerformanceGoalsVO();

			performanceGoalsVO.setCreatedBy(performanceGoalsDTO.getCreatedBy());
			performanceGoalsVO.setModifiedBy(performanceGoalsDTO.getCreatedBy());

			message = "PerformanceGoals Creation SuccessFully";

		} else {

			performanceGoalsVO = performanceGoalsRepo.findById(performanceGoalsDTO.getId()).orElseThrow(
					() -> new ApplicationException("PreGoals  not found with id: " + performanceGoalsDTO.getId()));

			performanceGoalsVO.setModifiedBy(performanceGoalsDTO.getCreatedBy());

			message = "PerformanceGoals Updation SuccessFully";

		}

		performanceGoalsVO = getPerformanceGoalsVOFormPerformanceGoalsDTO(performanceGoalsVO, performanceGoalsDTO);
		performanceGoalsRepo.save(performanceGoalsVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("performanceGoalsVO", performanceGoalsVO);
		return response;
	}

	private PerformanceGoalsVO getPerformanceGoalsVOFormPerformanceGoalsDTO(PerformanceGoalsVO performanceGoalsVO,
			@Valid PerformanceGoalsDTO performanceGoalsDTO) {

		performanceGoalsVO.setAppraisalYear(performanceGoalsDTO.getApprisalYear());
		performanceGoalsVO.setEmpCode(performanceGoalsDTO.getEmpCode());
		performanceGoalsVO.setEmpName(performanceGoalsDTO.getEmpName());
		performanceGoalsVO.setReportingto(performanceGoalsDTO.getReportingto());
		performanceGoalsVO.setReportingname(performanceGoalsDTO.getReportingname());
		performanceGoalsVO.setPmonth(performanceGoalsDTO.getPmonth());
		performanceGoalsVO.setOrgId(performanceGoalsDTO.getOrgId());
		performanceGoalsVO.setBranch(performanceGoalsDTO.getBranch());
		performanceGoalsVO.setBranchCode(performanceGoalsDTO.getBranchCode());
		performanceGoalsVO.setFinYear(performanceGoalsDTO.getFinYear());
		performanceGoalsVO.setDepartment(performanceGoalsDTO.getDepartment());
		performanceGoalsVO.setDesignation(performanceGoalsDTO.getDesignation());
		
		if (ObjectUtils.isNotEmpty(performanceGoalsDTO.getId())) {
			List<PerformanceGoalsDetailsVO> goalsDetailsVOs = performanceGoalsDetailsRepo
					.findByPerformanceGoalsVO(performanceGoalsVO);
			performanceGoalsDetailsRepo.deleteAll(goalsDetailsVOs);

		}

		List<PerformanceGoalsDetailsVO> preGoalsDetailsVOs = new ArrayList<>();
		for (PerformanceGoalsDetailsDTO performanceGoalsDetailsDTO : performanceGoalsDTO
				.getPerformanceGoalsDetailsDTO()) {
			PerformanceGoalsDetailsVO performanceGoalsDetailsVO = new PerformanceGoalsDetailsVO();

			performanceGoalsDetailsVO.setPerspective(performanceGoalsDetailsDTO.getPerspective());
			performanceGoalsDetailsVO.setObjectivedesc(performanceGoalsDetailsDTO.getObjectivedesc());
			performanceGoalsDetailsVO.setMeasurement(performanceGoalsDetailsDTO.getMeasurement());
			performanceGoalsDetailsVO.setQtrtarget(performanceGoalsDetailsDTO.getQtrtarget());
			performanceGoalsDetailsVO.setPerformance(performanceGoalsDetailsDTO.getPerformance());
			performanceGoalsDetailsVO.setComments(performanceGoalsDetailsDTO.getComments());
			performanceGoalsDetailsVO.setSelfrating(performanceGoalsDetailsDTO.getSelfrating());
		performanceGoalsDetailsVO.setAppraiserrating(performanceGoalsDetailsDTO.getAppraiserrating());
//			performanceGoalsDetailsVO.setAppraiserrating(performanceGoalsDetailsDTO.getAppraiserrating());
			performanceGoalsDetailsVO.setPerformanceself(performanceGoalsDetailsDTO.getPerformanceself());
			performanceGoalsDetailsVO.setApprjustification(performanceGoalsDetailsDTO.getApprjustification());
			performanceGoalsDetailsVO.setPerassigned(performanceGoalsDetailsDTO.getPerassigned());

			performanceGoalsDetailsVO.setPerformanceGoalsVO(performanceGoalsVO);
			preGoalsDetailsVOs.add(performanceGoalsDetailsVO);

		}

		performanceGoalsVO.setPerformanceGoalsDtlVO(preGoalsDetailsVOs);

		return performanceGoalsVO;
	}

	@Override
	public Map<String, Object> approveUpdatePreGoals(@Valid PerformanceGoalsDTO performanceGoalsDTO, String userName,
			String approve) throws ApplicationException {

		String message;

		PerformanceGoalsVO performanceGoalsVO = null;

		if (ObjectUtils.isEmpty(performanceGoalsDTO.getId())) {

			performanceGoalsVO = new PerformanceGoalsVO();

			performanceGoalsVO.setCreatedBy(performanceGoalsDTO.getCreatedBy());

			message = "PreGoals Creation SuccessFully";

		} else {

			performanceGoalsVO = performanceGoalsRepo.findById(performanceGoalsDTO.getId()).orElseThrow(
					() -> new ApplicationException("PreGoals  not found with id: " + performanceGoalsDTO.getId()));

			performanceGoalsVO.setModifiedBy(performanceGoalsDTO.getCreatedBy());

			message = "PerformanceGoals Updation SuccessFully";

		}

		performanceGoalsVO = getPerformanceGoalsVOFormPerformanceGoalsDTO(performanceGoalsVO, performanceGoalsDTO,
				userName, approve);
		performanceGoalsRepo.save(performanceGoalsVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("performanceGoalsVO", performanceGoalsVO);
		return response;
	}

	private PerformanceGoalsVO getPerformanceGoalsVOFormPerformanceGoalsDTO(PerformanceGoalsVO performanceGoalsVO,
			@Valid PerformanceGoalsDTO performanceGoalsDTO, String userName, String approve) {

		performanceGoalsVO.setApprove1(approve);

		performanceGoalsVO.setApprove1name(userName);
		performanceGoalsVO.setApprove1on(LocalDateTime.now());
		performanceGoalsVO.setEmpCode(performanceGoalsDTO.getEmpCode());
		performanceGoalsVO.setEmpName(performanceGoalsDTO.getEmpName());
		performanceGoalsVO.setPmonth(performanceGoalsDTO.getPmonth());

		if (ObjectUtils.isNotEmpty(performanceGoalsDTO.getId())) {
			List<PerformanceGoalsDetailsVO> goalsDetailsVOs = performanceGoalsDetailsRepo
					.findByPerformanceGoalsVO(performanceGoalsVO);
			performanceGoalsDetailsRepo.deleteAll(goalsDetailsVOs);

		}

		List<PerformanceGoalsDetailsVO> performanceGoalsDetailsVOs = new ArrayList<>();
		for (PerformanceGoalsDetailsDTO performanceGoalsDetailsDTO : performanceGoalsDTO
				.getPerformanceGoalsDetailsDTO()) {
			PerformanceGoalsDetailsVO performanceGoalsDetailsVO = new PerformanceGoalsDetailsVO();

			performanceGoalsDetailsVO.setPerspective(performanceGoalsDetailsDTO.getPerspective());
			performanceGoalsDetailsVO.setObjectivedesc(performanceGoalsDetailsDTO.getObjectivedesc());
			performanceGoalsDetailsVO.setObjectivedesc(performanceGoalsDetailsDTO.getObjectivedesc());
			performanceGoalsDetailsVO.setMeasurement(performanceGoalsDetailsDTO.getMeasurement());
			performanceGoalsDetailsVO.setQtrtarget(performanceGoalsDetailsDTO.getQtrtarget());
			performanceGoalsDetailsVO.setPerformance(performanceGoalsDetailsDTO.getPerformance());
			performanceGoalsDetailsVO.setComments(performanceGoalsDetailsDTO.getComments());
			performanceGoalsDetailsVO.setSelfrating(performanceGoalsDetailsDTO.getSelfrating());
			performanceGoalsDetailsVO.setAppraiserrating(performanceGoalsDetailsDTO.getAppraiserrating());
			performanceGoalsDetailsVO.setPerformanceself(performanceGoalsDetailsDTO.getPerformanceself());
			performanceGoalsDetailsVO.setApprjustification(performanceGoalsDetailsDTO.getApprjustification());
			performanceGoalsDetailsVO.setPerformanceGoalsVO(performanceGoalsVO);
			performanceGoalsDetailsVO.setPerassigned(performanceGoalsDetailsDTO.getPerassigned());
			performanceGoalsDetailsVOs.add(performanceGoalsDetailsVO);

		}

		performanceGoalsVO.setPerformanceGoalsDtlVO(performanceGoalsDetailsVOs);

		return performanceGoalsVO;
	}

	@Override
	public PerformanceGoalsVO getPerformanceGoalsById(Long id) {
		
		return performanceGoalsRepo.getPerformanceGoalsById(id);
	}

	@Override
	public List<PerformanceGoalsVO> getPerformanceGoalsByOrgId(Long orgId) {

		return performanceGoalsRepo.getPerformanceGoalsByOrgId(orgId);
	}

	public List<Map<String, Object>> getPerformanceGoalsVOListById(Long id) {
		Set<Object[]> details = performanceGoalsRepo.getPerformanceGoalsVOListById(id);
		return getPerformanceGoalsVOListById(details);
	}

	private List<Map<String, Object>> getPerformanceGoalsVOListById(Set<Object[]> details) {
		List<Map<String, Object>> report = new ArrayList<>();
		for (Object[] det : details) {
			DecimalFormat df = new DecimalFormat("0.00");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Map<String, Object> dtl = new HashMap<>();
			dtl.put("gst_performancegoalsid", det[0]);
			dtl.put("appraisalYear", det[1] != null ? det[1].toString() : "");
			dtl.put("empcode", det[2] != null ? det[2].toString() : "");
			dtl.put("empname", det[3] != null ? det[3].toString() : "");
			dtl.put("reportingto", det[4] != null ? det[4].toString() : "");
			dtl.put("reportingname", det[5] != null ? det[5].toString() : "");
			dtl.put("approve1", det[6] != null ? det[6].toString() : "");
			dtl.put("pmonth", det[7] != null ? det[7].toString() : "");
			report.add(dtl);
		}
		return report;
	}

	@Override
	public List<PerformanceGoalsVO> getAllPerformanceGoalsVO() {
		return performanceGoalsRepo.findAll();
	}

	@Override
	public void saveExpenseImages(List<MultipartFile> file, Long expenseId) throws IOException, ApplicationException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Map<String, Object>> getReportingUserName(String username) {
		Set<Object[]> details = performanceGoalsRepo.getReportingUserName(username);
		return getReportingUserName(details);
	}

	private List<Map<String, Object>> getReportingUserName(Set<Object[]> details) {
		List<Map<String, Object>> report = new ArrayList<>();
		for (Object[] det : details) {
			DecimalFormat df = new DecimalFormat("0.00");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Map<String, Object> dtl = new HashMap<>();

			dtl.put("reportingto", det[0] != null ? det[0].toString() : "");
			dtl.put("reportingcode", det[1] != null ? det[1].toString() : "");

			report.add(dtl);
		}
		return report;
	}

	@Override
	public List<Map<String, Object>> getPerformanceGoalsbyreportingto(String reportingto) {
		Set<Object[]> details = performanceGoalsRepo.getPerformanceGoalsbyreportingto(reportingto);
		return getPerformanceGoalsbyreportingto(details);
	}

	private List<Map<String, Object>> getPerformanceGoalsbyreportingto(Set<Object[]> details) {
		List<Map<String, Object>> report = new ArrayList<>();
		for (Object[] det : details) {
			DecimalFormat df = new DecimalFormat("0.00");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Map<String, Object> dtl = new HashMap<>();
			dtl.put("gst_performancegoalsid", det[0]!=null? det[0].toString() : "");
			dtl.put("appraisalYear", det[1] != null ? det[1].toString() : "");
			dtl.put("empcode", det[2] != null ? det[2].toString() : "");
			dtl.put("empname", det[3] != null ? det[3].toString() : "");
			dtl.put("reportingto", det[4] != null ? det[4].toString() : "");
			dtl.put("reportingname", det[5] != null ? det[5].toString() : "");
			dtl.put("pmonth", det[6] != null ? det[6].toString() : "");

			report.add(dtl);
		}
		return report;
	}

	@Override
	public List<Map<String, Object>> getPerformanceGoalsbyUserName(String userName) {
		Set<Object[]> details = performanceGoalsRepo.getPerformanceGoalsbyUserName(userName);
		return getPerformanceGoalsbyUserName(details);
	}

	private List<Map<String, Object>> getPerformanceGoalsbyUserName(Set<Object[]> details) {
		List<Map<String, Object>> report = new ArrayList<>();
		for (Object[] det : details) {
			DecimalFormat df = new DecimalFormat("0.00");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Map<String, Object> dtl = new HashMap<>();
			dtl.put("gst_performancegoalsid", det[0]);
			dtl.put("appraisalYear", det[1] != null ? det[1].toString() : "");
			dtl.put("empcode", det[2] != null ? det[2].toString() : "");
			dtl.put("empname", det[3] != null ? det[3].toString() : "");
			dtl.put("approve1", det[4] != null ? det[4].toString() : "");
			dtl.put("approve1name", det[5] != null ? det[5].toString() : "");
			dtl.put("approve1on", det[6] != null ? det[6].toString() : "");
			dtl.put("pmonth", det[7] != null ? det[7].toString() : "");

			report.add(dtl);
		}
		return report;
	}

	@Override
	public List<PerformanceGoalsVO> getPerformanceGoalsDetailsReport(Long orgId,String pmonth,String branch,String appraisalYear) {
	
	return  performanceGoalsRepo.getPerformanceGoalsDetailsReport(orgId, pmonth, branch, appraisalYear);
	}

//	private List<Map<String, Object>> getPerformanceGoalsDetailsReport(Set<Object[]> details) {
//		List<Map<String, Object>> report = new ArrayList<>();
//		for (Object[] det : details) {
//			DecimalFormat df = new DecimalFormat("0.00");
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//			Map<String, Object> dtl = new HashMap<>();
//			dtl.put("gst_performancegoalsid", det[0]);
//			dtl.put("perspective", det[1] != null ? det[1].toString() : "");
//			dtl.put("objectivedesc", det[2] != null ? det[2].toString() : "");
//			dtl.put("perassigned", det[3] != null ? det[3].toString() : "");
//			dtl.put("measurement", det[4] != null ? det[4].toString() : "");
//
//			dtl.put("qtrtarget", det[5] != null ? det[5].toString() : "");
//			dtl.put("performance", det[6] != null ? det[6].toString() : "");
//			dtl.put("comments", det[7] != null ? det[7].toString() : "");
//			dtl.put("selfrating", det[8] != null ? new BigDecimal(det[8].toString()) : BigDecimal.ZERO);
//			dtl.put("appraiserrating", det[9] != null ? new BigDecimal(det[9].toString()) : BigDecimal.ZERO);
//			dtl.put("performanceself", det[10] != null ? det[10].toString() : "");
//			dtl.put("apprjustification", det[11] != null ? det[11].toString() : "");
//			dtl.put("branch", det[12] != null ? det[12].toString() : "");
//			dtl.put("pmonth", det[13] != null ? det[13].toString() : "");
//			dtl.put("finYear", det[14] != null ? det[14].toString() : "");
//			dtl.put("employeeName", det[15] != null ? det[15].toString() : "");
//			dtl.put("employeeCode", det[16] != null ? det[16].toString() : "");
//			dtl.put("department", det[17] != null ? det[17].toString() : "");
//
//			report.add(dtl);
//		}
//		return report;
//	}

	@Override
	public PerformanceGoalsVO updatePerformanceGoalsApprovedDetails(Long id, String approve1, String approve1name) {

		PerformanceGoalsVO performanceGoalsVO = performanceGoalsRepo.findPerformancegoals(id);

		if (performanceGoalsVO == null) {
			throw new RuntimeException("PerformanceGoals Records not found This Id");
		}

		performanceGoalsVO.setApprove1(approve1);
		performanceGoalsVO.setApprove1name(approve1name);
		performanceGoalsVO.setApprove1on(LocalDateTime.now());

		return performanceGoalsRepo.save(performanceGoalsVO);

	}


	@Override
	public List<PerformanceGoalsVO> getPerformanceGoalsByOrgIdAndReportingPerson(Long orgId,String reportingPerson) {
	
	return  performanceGoalsRepo.getPerformanceGoalsByOrgIdAndReportingPerson( orgId, reportingPerson);
	}
	
	@Override
	public List<Map<String, Object>> getSupervisorRatings(
	        Long orgId, String empCode, String appraisalYear) {

	    List<Object[]> rows = performanceGoalsRepo.getSupervisorRatings(orgId, empCode, appraisalYear);

	    if (rows.isEmpty()) {
	        throw new RuntimeException("No Supervisor Ratings found for given inputs");
	    }

	    List<Map<String, Object>> list = new ArrayList<>();

	    for (Object[] row : rows) {

	        Map<String, Object> map = new HashMap<>();

	        map.put("detailsId", ((Number) row[0]).longValue());
	        map.put("goals", (String) row[1]);

	        // 🔥 safe conversion
	        map.put("supervisorRating",
	                row[2] != null ? row[2].toString() : null);

	        map.put("score",
	                row[3] != null ? Integer.parseInt(row[3].toString()) : null);
	        list.add(map);
	    }

	    return list;
	}
	
	@Override
	public List<PerformanceGoalsVO> getPerformanceGoalsByOrgIdAndEmployeeCode(Long orgId,String employeeCode) {
	
	return  performanceGoalsRepo.getPerformanceGoalsByOrgIdAndEmployeeCode( orgId, employeeCode);
	}
	
	@Override
	public List<PerformanceGoalsVO> getDashBoardDetails(Long orgId,String pmonth,String appraisalYear,String employeeCode) {
	
	return  performanceGoalsRepo.getDashBoardDetails(orgId, pmonth, appraisalYear,employeeCode);
	}

}