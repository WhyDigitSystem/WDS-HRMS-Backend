package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.ExitInterviewDepartmentVO;

@Repository
public interface ExitInterviewDepartmentRepo extends JpaRepository<ExitInterviewDepartmentVO, Long>{

	@Query(nativeQuery = true,value="select * from exitinterviewdepartment where exitinterviewdepartmentid=?1 and active=1")
	ExitInterviewDepartmentVO getExitInterviewDepartmentById(Long id);

	@Query(nativeQuery = true,value="select * from exitinterviewdepartment where orgid=?1 and branchcode=?2 and active=1")
	List<ExitInterviewDepartmentVO> getExitInterviewDepartmentVOByOrgId(Long orgId, String branchCode);

	@Query(nativeQuery = true,value="SELECT *\r\n"
			+ "FROM exitinterviewdepartment\r\n"
			+ "WHERE orgid = ?1 \r\n"
			+ "AND branchcode = ?2 \r\n"
			+ "AND designation IN (?3 , 'GENERAL') and active=1")
	List<ExitInterviewDepartmentVO> getExitInterviewBasedOnDesignation(Long orgId, String branchCode,
			String designation);

}
