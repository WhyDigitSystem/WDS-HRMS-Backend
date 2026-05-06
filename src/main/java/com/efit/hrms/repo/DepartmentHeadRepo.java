package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.DepartmentHeadVO;

@Repository
public interface DepartmentHeadRepo extends JpaRepository<DepartmentHeadVO, Long>{

	@Query(nativeQuery = true,value="select * from departmenthead where departmentheadid=?1 and active=1")
	DepartmentHeadVO getDepartmentHeadById(Long id);

	@Query(nativeQuery = true,value="select * from departmenthead where orgid=?1 and branchcode=?2 and active=1")
	List<DepartmentHeadVO> getDepartmentHeadByOrgId(Long orgId, String branchCode);

}
