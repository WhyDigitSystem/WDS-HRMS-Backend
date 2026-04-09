package com.efit.hrms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface PayslipSettlementServie {

	List<Map<String, Object>> getpayslipSettlementDetails(Long orgId, String empCode, String branchCode);

	List<Map<String, Object>> getSeparationEmployeeForSettlement(Long orgId, String branchCode);

}
