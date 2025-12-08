package com.efit.hrms.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efit.hrms.dto.PerformanceGoalsDTO;
import com.efit.hrms.entity.PerformanceGoalsVO;
import com.efit.hrms.exception.ApplicationException;

@Service
public interface PerformanceGoalsService {

	// OMap<String, Object> createUpdatePreGoals(PreGoalsDtlDTO preGoalsDtlDTO)
	// throws IOException, ApplicationException;

	void saveExpenseImages(List<MultipartFile> file, Long expenseId) throws IOException, ApplicationException;

	List<PerformanceGoalsVO> getPerformanceGoalsByOrgId(Long orgId);

//	PerformanceGoalsVO getPerformanceGoalsVOById(Long id);

	List<Map<String, Object>> getPerformanceGoalsVOListById(Long id);

	List<Map<String, Object>> getPerformanceGoalsbyreportingto(String reportingto);

	List<Map<String, Object>> getPerformanceGoalsbyUserName(String userName);

	List<Map<String, Object>> getReportingUserName(String userName);

	List<PerformanceGoalsVO> getAllPerformanceGoalsVO();

	Map<String, Object> createUpdatePerformanceGoals(PerformanceGoalsDTO performanceGoalsDTO)
			throws IOException, ApplicationException;

	Map<String, Object> approveUpdatePreGoals(PerformanceGoalsDTO performanceGoalsDTO, String userName, String approve)
			throws IOException, ApplicationException;

	PerformanceGoalsVO updatePerformanceGoalsApprovedDetails(Long id, String approve1, String approve1name);

	PerformanceGoalsVO getPerformanceGoalsById(Long id);

	List<PerformanceGoalsVO> getPerformanceGoalsDetailsReport(Long orgId, String pmonth, String branch,
			String appraisalYear);

	List<PerformanceGoalsVO> getPerformanceGoalsByOrgIdAndReportingPerson(Long orgId, String reportingPerson);

	List<PerformanceGoalsVO> getPerformanceGoalsByOrgIdAndEmployeeCode(Long orgId, String employeeCode);

	List<PerformanceGoalsVO> getDashBoardDetails(Long orgId, String pmonth,  String appraisalYear,
			String employeeCode);

}
