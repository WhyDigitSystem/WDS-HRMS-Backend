package com.efit.hrms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efit.hrms.entity.DocTypeMappingDetailsVO;

public interface DocTypeMappingDetailsRepo extends JpaRepository<DocTypeMappingDetailsVO, Long> {

//	DocTypeMappingDetailsVO findByBranchAndScreenCode(String branch, String screenCode);

	DocTypeMappingDetailsVO findByBranchCodeAndScreenCode(String branchCode, String screenCode);

	DocTypeMappingDetailsVO findByOrgIdAndScreenCode(Long orgId, String screenCode);


}