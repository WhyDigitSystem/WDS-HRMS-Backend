package com.efit.hrms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface NewDashBoardService {

	List<Map<String, Object>> getLeaveCountForDashBoard(String employeeCode, Long orgId,
			String department, String branch, String type, String contractor);

}
