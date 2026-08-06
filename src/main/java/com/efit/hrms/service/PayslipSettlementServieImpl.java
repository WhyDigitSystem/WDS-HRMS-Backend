package com.efit.hrms.service;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efit.hrms.repo.AttendanceSummaryRepo;
import com.efit.hrms.repo.InitiateSeparationRepo;

@Service
public class PayslipSettlementServieImpl implements PayslipSettlementServie{
	
	@Autowired
	AttendanceSummaryRepo attendanceSummaryRepo;
	
	@Autowired
	InitiateSeparationRepo separationRepo;

	public static final Logger LOGGER = LoggerFactory.getLogger(PayslipSettlementServieImpl.class);

	@Override
	public List<Map<String, Object>> getpayslipSettlementDetails(Long orgId, String empCode, String branchCode) {
	    Set<Object[]> details = attendanceSummaryRepo.getpayslipSettlementDetails(orgId, empCode, branchCode);
	    return mapPayslipSettlement(details);
	}

	private List<Map<String, Object>> mapPayslipSettlement(Set<Object[]> details) {

	    List<Map<String, Object>> report = new ArrayList<>();

	    DecimalFormat df = new DecimalFormat("0.00");
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	    for (Object[] det : details) {

	        Map<String, Object> dtl = new HashMap<>();

	        dtl.put("employeecode", det[0] != null ? det[0].toString() : "");
	        dtl.put("employee", det[1] != null ? det[1].toString() : "");

	        dtl.put("resignation",
	                det[2] instanceof Date ? dateFormat.format((Date) det[2]) : "");

	        dtl.put("lastworkingdate",
	                det[3] instanceof Date ? dateFormat.format((Date) det[3]) : "");

	        dtl.put("finalWorkingDate",
	                det[4] instanceof Date ? dateFormat.format((Date) det[4]) : "");

	        dtl.put("month", det[5] != null ? det[5].toString() : "");
	        dtl.put("totalDays", det[6] != null ? det[6].toString() : "");

	        dtl.put("payableDays",
	                det[7] != null ? df.format(Double.parseDouble(det[7].toString())) : "0");

	        dtl.put("sumofearning",
	                det[8] != null ? df.format(Double.parseDouble(det[8].toString())) : "0");

	        dtl.put("sumofdetection",
	                det[9] != null ? df.format(Double.parseDouble(det[9].toString())) : "0");

	        dtl.put("perDaySalary",
	                det[10] != null ? df.format(Double.parseDouble(det[10].toString())) : "0");

	        dtl.put("grossSalary",
	                det[11] != null ? df.format(Double.parseDouble(det[11].toString())) : "0");

	        dtl.put("netSalary",
	                det[12] != null ? df.format(Double.parseDouble(det[12].toString())) : "0");

	        report.add(dtl);
	    }

	    return report;
	}
	
	@Override
	public List<Map<String, Object>> getSeparationEmployeeForSettlement(Long orgId,String branchCode) {
		return separationRepo.getSeparationEmployeeForSettlement(orgId,branchCode);
	}
}
