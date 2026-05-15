package com.efit.hrms.service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efit.hrms.common.CommonConstant;
import com.efit.hrms.common.UserConstants;
import com.efit.hrms.repo.AttendanceProcessRepo;

@Service
public class NewDashBoardServiceImpl implements NewDashBoardService{

	public static final Logger LOGGER = LoggerFactory.getLogger(NewDashBoardServiceImpl.class);
	
	@Autowired
	AttendanceProcessRepo attendanceProcessRepo;
	
	@Override
	public List<Map<String, Object>> getMonthlyAttendanceForDashBoard(String employeeCode, Long orgId,
			String department, String branch, String type, String contractor) {

		Set<Object[]> result = attendanceProcessRepo.getLeaveCountForDashBoard(employeeCode, orgId,
				department, branch, type, contractor);
		return getLeaveCountForDashBoard(result);
	}

	private List<Map<String, Object>> getLeaveCountForDashBoard(Set<Object[]> result) {
		List<Map<String, Object>> detailsList = new ArrayList<>();
		  if (result == null || result.isEmpty()) {

		        String currentMonth = LocalDate.now()
		                .getMonth()
		                .getDisplayName(TextStyle.FULL, Locale.ENGLISH);

		        throw new RuntimeException(
		                "NO DATA FOUND IN " + currentMonth.toUpperCase() + " MONTH.");
		    }
		for (Object[] record : result) {
			Map<String, Object> map = new HashMap<>();
			map.put("employeeName", record[0] != null ? record[0].toString() : "");
			map.put("employeeCode", record[1] != null ? record[1].toString() : "");
			map.put("month", record[2] != null ? record[2].toString() : "0");
			map.put("year", record[3] != null ? record[3].toString() : "0");
			map.put("totalDays", record[4] != null ? record[4].toString() : "0");
			map.put("salaryDays", record[5] != null ? record[5].toString() : "0");

			detailsList.add(map);
		}
		return detailsList;
	}

	//takenleave api
	
	 @Override
	    public List<Map<String, Object>> getLeaveTakenReport(
	            Long orgId,
	            String employeecode
	    ) {

	        List<Map<String, Object>> rawList =
	        		attendanceProcessRepo.getLeaveTakenReport(
	                        orgId,
	                        employeecode
	                );

	        List<Map<String, Object>> orderedList =
	                new ArrayList<>();

	        for (Map<String, Object> row : rawList) {

	            Map<String, Object> orderedMap =
	                    new LinkedHashMap<>();

	            orderedMap.put(
	                    "employeecode",
	                    row.get("employeecode")
	            );

	            orderedMap.put(
	                    "employee",
	                    row.get("employee")
	            );

	            orderedMap.put(
	                    "CL",
	                    row.get("CL")
	            );

	            orderedMap.put(
	                    "COMP-OFF",
	                    row.get("COMP-OFF")
	            );

	            orderedMap.put(
	                    "LOP",
	                    row.get("LOP")
	            );

	            orderedList.add(orderedMap);
	        }

	        return orderedList;
	    }

	 
	 //latelogin api
	 
	 @Override
	 public List<Map<String, Object>> getLateLoginReportforDashBoard(
	         Long orgId,
	         String branchcode,
	         String employeecode
	 ) {

	     List<Map<String, Object>> rawList =
	    		 attendanceProcessRepo.getLateLoginReportforDashBoard(
	                     orgId,
	                     branchcode,
	                     employeecode
	             );

	     List<Map<String, Object>> orderedList =
	             new ArrayList<>();

	     for (Map<String, Object> row : rawList) {

	         Map<String, Object> orderedMap =
	                 new LinkedHashMap<>();

	         orderedMap.put(
	                 "employeecode",
	                 row.get("employeecode")
	         );

	         orderedMap.put(
	                 "employee",
	                 row.get("empname")
	         );

	         orderedMap.put(
	                 "lateCheckinCount",
	                 row.get("late_checkin_count")
	         );

	         orderedMap.put(
	                 "lateDates",
	                 row.get("late_dates")
	         );

	         orderedList.add(orderedMap);
	     }

	     return orderedList;
	 }
	 
	 
	 @Override
	 public List<Map<String, Object>> pendingApprovalForDashBoard(
	         Long orgId,String employeeCode) {

	     String methodName = "pendingApprovalForDashBoard()";

	     LOGGER.debug(
	             CommonConstant.STARTING_METHOD,
	             methodName);

	     List<Map<String, Object>> approvalList =
	             new ArrayList<>();

	     try {

	         approvalList =
	        		 attendanceProcessRepo
	                         .getpendingApprovalForDashBoard(orgId,employeeCode);

	     } catch (Exception e) {

	         LOGGER.error(
	                 UserConstants.ERROR_MSG_METHOD_NAME,
	                 methodName,
	                 e.getMessage());

	         e.printStackTrace();
	     }

	     LOGGER.debug(
	             CommonConstant.ENDING_METHOD,
	             methodName);

	     return approvalList;
	 }
	 
	 //working hours api
	 
	 @Override
	 public List<Map<String, Object>> getAttendanceDashboard(
	         Long orgId,
	         String employeecode) {

	     List<Map<String, Object>> rawList =
	             attendanceProcessRepo.getAttendanceDashboard(
	                     orgId,
	                     employeecode
	             );

	     List<Map<String, Object>> orderedList =
	             new ArrayList<>();

	     for (Map<String, Object> row : rawList) {

	         Map<String, Object> orderedMap =
	                 new LinkedHashMap<>();

	         orderedMap.put(
	                 "employeecode",
	                 row.get("employeecode")
	         );

	         orderedMap.put(
	                 "employee",
	                 row.get("employee")
	         );

	         orderedMap.put(
	                 "breaktime",
	                 row.get("breaktime")
	         );

	         orderedMap.put(
	                 "office_hours",
	                 row.get("office_hours")
	         );

	         orderedMap.put(
	                 "present_days",
	                 row.get("present_days")
	         );

	         orderedMap.put(
	                 "casual_leave",
	                 row.get("casual_leave")
	         );

	         orderedMap.put(
	                 "lop_leave",
	                 row.get("lop_leave")
	         );

	         orderedMap.put(
	                 "compoff_used",
	                 row.get("compoff_used")
	         );

	         orderedMap.put(
	                 "compoff_earned",
	                 row.get("compoff_earned")
	         );

	         orderedMap.put(
	                 "permission_hours",
	                 row.get("permission_hours")
	         );

	         orderedMap.put(
	                 "actual_worked_hours",
	                 row.get("actual_worked_hours")
	         );

	         orderedMap.put(
	                 "expected_working_hours",
	                 row.get("expected_working_hours")
	         );

	         orderedMap.put(
	                 "missing_hours",
	                 row.get("missing_hours")
	         );

	         orderedMap.put(
	                 "extra_hours",
	                 row.get("extra_hours")
	         );

	         orderedMap.put(
	                 "current_month_extra_hours",
	                 row.get("current_month_extra_hours")
	         );

	         orderedMap.put(
	                 "final_balance_hours",
	                 row.get("final_balance_hours")
	         );

	         orderedList.add(orderedMap);
	     }

	     return orderedList;
	 }
	 
}
