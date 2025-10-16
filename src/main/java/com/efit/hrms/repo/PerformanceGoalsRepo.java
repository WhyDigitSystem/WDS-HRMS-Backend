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

	@Query(nativeQuery = true, value = "select p1.performancegoalsdetailsid,p1.perspective,p1.objectivedesc,p1.perassigned,p1.measurement\r\n"
			+ ",p1.qtrtarget,p1.performance,p1.comments,p1.selfrating,p1.appraiserrating,p1.performanceself\r\n"
			+ ",p1.apprjustification,p.branch,p.pmonth,p.appraisalyear,p.empname,p.empcode,p.department From performancegoals p, performancegoalsdetails p1  where p.performancegoalsid=p1.performancegoalsid \r\n"
			+ " and p.orgid=?1 and p.pmonth=?2 and (p.branch=?3 or 'ALL'=?3)  and p.appraisalyear=?4")
	Set<Object[]> getPerformanceGoalsDetailsReport(Long orgId,String pmonth,String branch,String appraisalYear);

	@Query(nativeQuery = true, value = "select * from performancegoals where performancegoalsid=?1")
	PerformanceGoalsVO findPerformancegoals(Long id);

	@Query(nativeQuery = true, value = "select reportingperson,reportingpersoncode from employee where lower(employeecode)=lower(?1)")
	Set<Object[]> getReportingUserName(String userName);

	@Query(nativeQuery = true, value = "select performancegoalsid,appraisalyear,empcode,empname,reportingto,reportingname,approve1,pmonth From performancegoals where performancegoalsid = ?1")
	Set<Object[]> getPerformanceGoalsVOListById(Long id);

	@Query(nativeQuery = true, value = "select * from performancegoals where performancegoalsid=?1")
	PerformanceGoalsVO getPerformanceGoalsById(Long id);

}
