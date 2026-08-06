package com.efit.hrms.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efit.hrms.entity.DocTypeMappingVO;

public interface DocTypeMappingRepo extends JpaRepository<DocTypeMappingVO, Long> {

	

	
	@Query(nativeQuery = true, value = "select ?1 branch,?2 branchCode, doccode,screencode,screenname from documenttype  where screencode not in(\r\n"
			+ "select screencode from documenttypemappingdetails where branch=?1  group by screencode)")
	Set<Object[]> getPendingDocTypeMappingDetails(String branch, String branchCode);
	
}
