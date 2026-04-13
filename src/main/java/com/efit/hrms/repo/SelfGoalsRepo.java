package com.efit.hrms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.SelfGoalsVO;

@Repository
public interface SelfGoalsRepo extends JpaRepository<SelfGoalsVO, Long> {

	@Query(value = "select * from selfgoals where orgid=?1", nativeQuery = true)
	List<SelfGoalsVO> getSelfGoals(Long orgId);

	@Query(nativeQuery = true, value = "select s.appraisalid,s.code,s.name,s.supervisorcode,s.supervisorname,d.area,d.goals,d.keyperformanceindicator \r\n"
			+ "from selfgoals s join selfgoalsdetails d on s.selfgoalsid=d.selfgoalsid where s.orgid=?1 and s.code=?2\r\n"
			+ "")
	Set<Object[]> getAppraisee(Long orgId, String employeeCode);

	@Query(value = "select * from selfgoals where orgid=?1 and code=?2", nativeQuery = true)
	List<SelfGoalsVO> getSelfGoalsByOrgIdAndEmpCode(Long orgId, String empCode);

	@Query(value = "SELECT * FROM selfgoals " +
		       "WHERE orgid = ?1 " +
		       "AND code = ?2 " +
		       "AND (?3 IS NULL OR finyear = ?3)", 
		       nativeQuery = true)	
	List<SelfGoalsVO> getSelfGoalsByOrgIdAndEmpCodeAndFinyear(Long orgId, String empCode, Long finYear);

	@Query(value = "SELECT *\r\n"
			+ "FROM selfgoals s\r\n"
			+ "JOIN selfgoalsdetails d \r\n"
			+ "    ON s.selfgoalsid = d.selfgoalsid\r\n"
			+ "    AND d.status = 'APPROVED'\r\n"
			+ "WHERE s.orgid = ?1 \r\n"
			+ "  AND s.code = ?2 \r\n"
			+ "  AND (?3 IS NULL OR s.finyear = ?3);", 
		       nativeQuery = true)	
	List<SelfGoalsVO> getSelfGoalsForPerformanceGoals(Long orgId, String empCode, Long finYear);

}
