package com.efit.hrms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efit.hrms.entity.LeaveRequestVO;

public interface LeaveRequestRepo extends JpaRepository<LeaveRequestVO, Long> {

	@Query(nativeQuery = true, value = "select * from  leaveRequest where orgid=?1  and employeecode=?2 ")
	List<LeaveRequestVO> getLeaveRequestByOrgId(Long orgId, String branchCode);
	
	@Query(nativeQuery = true, value = "select * from leaveRequest where leaverequestid=?1")
	LeaveRequestVO getLeaveRequestById(Long id);

//	@Query(value ="SELECT \r\n"
//			+ "    ROW_NUMBER() OVER () AS id, \r\n"
//			+ "    lt.leavetype, \r\n"
//			+ "    lt.leavecode, \r\n"
//			+ "    SUM(COALESCE(lb.totalleave, 0)) - SUM(COALESCE(pl.totalDays, 0)) AS totalleave\r\n"
//			+ "FROM leavetype lt\r\n"
//			+ "LEFT JOIN (\r\n"
//			+ "   \r\n"
//			+ "    SELECT leavetype, employeecode, SUM(totalleave) AS totalleave\r\n"
//			+ "    FROM leavebalance\r\n"
//			+ "    WHERE orgid = ?1\r\n"
//			+ "    AND employeecode = ?2\r\n"
//			+ "    GROUP BY leavetype, employeecode\r\n"
//			+ ") lb ON lt.leavetype = lb.leavetype \r\n"
//			+ "LEFT JOIN (\r\n"
//			+ "    -- Pre-aggregated total pending leave days\r\n"
//			+ "    SELECT leavetype, employeecode, SUM(totalDays) AS totalDays  \r\n"
//			+ "    FROM leaverequest  \r\n"
//			+ "    WHERE orgid = ?1\r\n"
//			+ "    AND employeecode = ?2   \r\n"
//			+ "    AND approvestatus = 'PENDING'  \r\n"
//			+ "    GROUP BY leavetype, employeecode  \r\n"
//			+ ") pl ON lt.leavetype = pl.leavetype \r\n"
//			+ "   AND pl.employeecode = ?2\r\n"
//			+ "WHERE lt.leaveapplicable = 'ALL'  \r\n"
//			+ "   OR lt.leaveapplicable = (SELECT b.gender FROM employee b WHERE b.orgid = ?1 AND b.employeecode = ?2)\r\n"
//			+ "GROUP BY lt.leavetype, lt.leavecode",nativeQuery =true)
//	Set<Object[]> getAllLeaveTypeFromLeaveMaster(Long orgId,String employeeCode);

	
	
	@Query(value ="SELECT \r\n"
			+ "    ROW_NUMBER() OVER () AS id,  \r\n"
			+ "    lt.leavetype,  \r\n"
			+ "    lt.leavecode, "
			+ "      MAX(el.EffectiveFrom) AS EffectiveFrom,  \r\n"
			+ " \r\n"
			+ "    CASE  \r\n"
			+ "        WHEN lt.leavetype = 'LOSS OF PAY' THEN 0  \r\n"
			+ "        ELSE SUM(COALESCE(lb.totalleave, 0)) - SUM(COALESCE(pl.totalDays, 0))  \r\n"
			+ "    END AS totalleave  \r\n"
			+ "FROM leavetype lt  \r\n"
			+ "LEFT JOIN (  \r\n"
			+ "    SELECT leavetype, employeecode, orgid, SUM(totalleave) AS totalleave  \r\n"
			+ "    FROM leavebalance  \r\n"
			+ "    WHERE orgid = ?1  \r\n"
			+ "    AND employeecode = ?2  \r\n"
			+ "    GROUP BY leavetype, employeecode, orgid  \r\n"
			+ ") lb ON lt.leavetype = lb.leavetype  \r\n"
			+ "   AND lb.orgid = lt.orgid  \r\n"
			+ "\r\n"
			+ "LEFT JOIN (  \r\n"
			+ "    -- Pre-aggregated total pending leave days  \r\n"
			+ "    SELECT leavetype, employeecode, orgid, SUM(totalDays) AS totalDays  \r\n"
			+ "    FROM leaverequest  \r\n"
			+ "    WHERE orgid = ?1  \r\n"
			+ "    AND employeecode = ?2  \r\n"
			+ "    AND approvestatus = 'PENDING'  \r\n"
			+ "    GROUP BY leavetype, employeecode, orgid  \r\n"
			+ ") pl ON lt.leavetype = pl.leavetype  \r\n"
			+ "   AND pl.employeecode = ?2  \r\n"
			+ "   AND pl.orgid = lt.orgid  "
			+ "LEFT JOIN employee e  \r\n"
			+ "   ON e.orgid = ?1  \r\n"
			+ "   AND e.employeecode = ?2  \r\n"
			+ "\r\n"
			+ "LEFT JOIN employeeleave el  \r\n"
			+ "   ON el.employeeid = e.employeeid  \r\n"
			+ "   AND el.leavetype = lt.leavetype  \r\n"
			+ "WHERE lt.orgid = ?1  \r\n"
			+ "  AND (lt.leaveapplicable = 'ALL'  \r\n"
			+ "       OR lt.leaveapplicable = (SELECT b.gender FROM employee b WHERE b.orgid = ?1 AND b.employeecode = ?2))  \r\n"
			+ "\r\n"
			+ "GROUP BY lt.leavetype, lt.leavecode\r\n"
			+ "",nativeQuery =true)
	Set<Object[]> getAllLeaveTypeFromLeaveMaster(Long orgId,String employeeCode);


//	LeaveRequestVO findByOrgIdAndIdAndEmployeeCode(Long orgId, Long id, String employeeCode);

	@Query(
		    value = "select * from leaverequest where orgid=?1 and leaverequestid=?2 and employeecode=?3",
		    nativeQuery = true
		)
	LeaveRequestVO findByOrgIdAndIdAndEmployeeCode(
	        Long orgId,
	        Long id,
	        String employeeCode
	);
	
	@Query(nativeQuery = true, value = "select a.employeename,a.employeecode,a.leavetype,a.fromdate,a.todate,a.totaldays,a.notes,a.leaverequestid,b.email,a.screenname from leaveRequest a INNER JOIN \r\n"
			+ "    employee b ON a.employeecode = b.employeecode where a.orgid=?1 and a.notifycode=?2 and a.branchcode=?3 and approvestatus='PENDING'")
	Set<Object[]> getLeaveRequestForDashBoard(Long orgId, String reportingPersonCode, String branchCode);
	
	@Query(nativeQuery = true, value = "select a.employeename,a.employeecode,a.leavetype,a.fromdate,a.todate,a.totaldays,a.notes,a.leaverequestid,b.email,a.screenname from leaveRequest a INNER JOIN \r\n"
			+ "    employee b ON a.employeecode = b.employeecode where a.orgid=?1 and a.notifycode=?2 and a.branchcode=?3 and approvestatus='APPROVED'")
	Set<Object[]> getAllApprovedLeaveForTeam(Long orgId, String reportingPersonCode, String branchCode);

	List<LeaveRequestVO> findByOrgIdAndNotifyCode(Long orgId, String notifyCode);

	@Query(value =
			"WITH RECURSIVE dates AS ( " +
			"    SELECT DATE(?1) AS attendance_date " +
			"    UNION ALL " +
			"    SELECT DATE_ADD(attendance_date, INTERVAL 1 DAY) " +
			"    FROM dates " +
			"    WHERE attendance_date < DATE(?2) " +
			") " +

			"SELECT " +
			"    e.employeecode, " +
			"    e.employee, " +
			"    e.department, " +
			"    e.designation, " +
			"    d.attendance_date, " +
			"    l.leavecode, " +
			"    l.leavetype, " +
			"    l.totaldays, " +
			"    l.notes, " +
			"    l.reason, " +
			"    l.approvestatus, " +
			"    l.approveon, " +
			"    l.approveby, " +
			"    approver.employee AS approvedByName " +

			"FROM employee e " +

			"CROSS JOIN dates d " +

			"INNER JOIN leaverequest l\r\n"
			+ "ON l.employeecode = e.employeecode\r\n"
			+ "AND d.attendance_date BETWEEN l.fromdate AND l.todate\r\n"
			+ "AND l.cancel = 0\r\n"
			+ "AND l.approvestatus IN ('PENDING', 'APPROVED', 'REJECTED')"+

			"LEFT JOIN employee approver " +
			"ON approver.employeecode = l.approveby " +
			"AND approver.orgid = l.orgid " +

			"WHERE e.orgid = ?3 " +
			"AND e.branchcode = ?4 " +
			"AND e.active = 1 " +
			"AND (?5 IS NULL OR ?5 = '' OR ?5 = 'ALL'  OR e.employeecode = ?5) " +

			"ORDER BY e.employeecode, d.attendance_date",
			nativeQuery = true)
			List<Object[]> getLeaveEscalationReport(
			        String fromDate,
			        String toDate,
			        Long orgId,
			        String branchCode,
			        String employeeCode);
}
