package com.efit.hrms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface NewDashBoardService {

	List<Map<String, Object>> getMonthlyAttendanceForDashBoard(String employeeCode, Long orgId,
			String department, String branch, String type, String contractor);

	List<Map<String, Object>> getLeaveTakenReport(Long orgId, String employeecode);

	List<Map<String, Object>> getLateLoginReportforDashBoard(Long orgId, String branchcode, String employeecode);

	List<Map<String, Object>> pendingApprovalForDashBoard(Long orgId, String employeeCode);

	List<Map<String, Object>> getAttendanceDashboard(Long orgId, String employeecode);

}
