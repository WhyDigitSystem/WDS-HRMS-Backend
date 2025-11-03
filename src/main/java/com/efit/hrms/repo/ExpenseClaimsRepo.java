package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.ExpenseClaimsVO;

@Repository
public interface ExpenseClaimsRepo extends JpaRepository<ExpenseClaimsVO, Long>{

	@Query(nativeQuery = true, value = "select * from expenseclaims where orgid=?1 and branchcode=?2")
	List<ExpenseClaimsVO> getExpenseClaimsByOrgId(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "select * from expenseclaims where expenseclaimsid=?1")
	ExpenseClaimsVO getExpenseClaimsById(Long id);

}


