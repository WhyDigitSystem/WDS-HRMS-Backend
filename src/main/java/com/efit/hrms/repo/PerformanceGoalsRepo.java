package com.efit.hrms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.PerformanceGoalsVO;

@Repository
public interface PerformanceGoalsRepo extends JpaRepository<PerformanceGoalsVO, Long> {

	@Query(value = "select a from PerformanceGoalsVO a where a.id=?1")
	PerformanceGoalsVO findByPerformanceGoalsId(Long id);

	@Query("SELECT e FROM PerformanceGoalsVO e  where e.orgId=?1")
	List<PerformanceGoalsVO> getPerformanceGoalsByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select performancegoalsid,appraisalyear,empcode,empname,approve1,approve1name,approve1on,pmonth From performancegoals where lower( empcode ) = lower( ?1 )")
	Set<Object[]> getPerformanceGoalsbyUserName(String userName);

	@Query(nativeQuery = true, value = "select performancegoalsid,appraisalyear,empcode,empname,reportingto,reportingname,pmonth From performancegoals where lower(reportingto) = lower( ?1 )")
	Set<Object[]> getPerformanceGoalsbyreportingto(String reportingto);

	@Query(nativeQuery = true, value = "SELECT * FROM \r\n"
			+ "    performancegoals p where\r\n"
			+ "     p.orgid = ?1\r\n"
			+ "    AND p.pmonth = ?2\r\n"
			+ "    AND (p.branch = ?3 OR 'ALL' = ?3)\r\n"
			+ "    AND p.appraisalyear = ?4")
	List<PerformanceGoalsVO> getPerformanceGoalsDetailsReport(Long orgId,String pmonth,String branch,String appraisalYear);

	@Query(nativeQuery = true, value = "select * from performancegoals where performancegoalsid=?1")
	PerformanceGoalsVO findPerformancegoals(Long id);

	@Query(nativeQuery = true, value = "select reportingperson,reportingpersoncode from employee where lower(employeecode)=lower(?1)")
	Set<Object[]> getReportingUserName(String userName);

	@Query(nativeQuery = true, value = "select performancegoalsid,appraisalyear,empcode,empname,reportingto,reportingname,approve1,pmonth From performancegoals where performancegoalsid = ?1")
	Set<Object[]> getPerformanceGoalsVOListById(Long id);

	@Query(nativeQuery = true, value = "select * from performancegoals where performancegoalsid=?1")
	PerformanceGoalsVO getPerformanceGoalsById(Long id);
	
	@Query(value="SELECT * FROM performancegoals e where e.orgid=?1 and e.reportingto=?2",nativeQuery = true)
	List<PerformanceGoalsVO> getPerformanceGoalsByOrgIdAndReportingPerson(Long orgId,String reportingPerson);
	
	@Query(value="SELECT * FROM performancegoals e where e.orgid=?1 and e.empcode=?2",nativeQuery = true)
	List<PerformanceGoalsVO> getPerformanceGoalsByOrgIdAndEmployeeCode(Long orgId,String employeeCode);
	
	@Query(nativeQuery = true, value = "select  * from performancegoals  where orgid = ?1 and  pmonth = ?2 and     appraisalyear = ?3 and empcode=?4")
	List<PerformanceGoalsVO> getDashBoardDetails(Long orgId,String pmonth,String appraisalYear,String employeeCode);

}
