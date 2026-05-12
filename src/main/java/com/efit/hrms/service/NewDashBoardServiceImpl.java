package com.efit.hrms.service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efit.hrms.repo.AttendanceProcessRepo;

@Service
public class NewDashBoardServiceImpl implements NewDashBoardService{

	public static final Logger LOGGER = LoggerFactory.getLogger(NewDashBoardServiceImpl.class);
	
	@Autowired
	AttendanceProcessRepo attendanceProcessRepo;
	
	@Override
	public List<Map<String, Object>> getLeaveCountForDashBoard(String employeeCode, Long orgId,
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

	

}
