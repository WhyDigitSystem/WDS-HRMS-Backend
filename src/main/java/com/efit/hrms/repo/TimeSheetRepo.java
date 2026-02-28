package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.TimeSheetVO;

@Repository
public interface TimeSheetRepo extends JpaRepository<TimeSheetVO, Long>{

	@Query(value = "SELECT * FROM timesheet e WHERE e.orgid=?1 and e.employeecode=?2 and date=?3",nativeQuery = true )
	List<TimeSheetVO> getTimeSheetByOrgId(Long orgId, String empCode, String date);

	@Query(value = "SELECT * FROM timesheet  WHERE timesheetid = ?1 ", nativeQuery = true)
	TimeSheetVO getTimeSheetById(Long id);

	@Query(value = "SELECT * FROM timesheet e WHERE e.orgid = ?1 AND e.employeecode = ?2 AND e.branchCode = ?3 AND e.date BETWEEN ?4 AND ?5", nativeQuery = true)
	List<TimeSheetVO> getTimeSheetDescByOrgId(Long orgId, String empCode, String branchCode, String fromDate, String toDate);	
	
	
//	@Query(value = "WITH RECURSIVE all_dates AS ( " +
//	        "    SELECT DATE(CONCAT(?2,'-',LPAD(?1,2,'0'),'-01')) AS work_date " +
//	        "    UNION ALL " +
//	        "    SELECT DATE_ADD(work_date, INTERVAL 1 DAY) " +
//	        "    FROM all_dates " +
//	        "    WHERE work_date < LAST_DAY(CONCAT(?2,'-',LPAD(?1,2,'0'),'-01')) " +
//	        "), " +
//	        "employee_list AS ( " +
//	        "    SELECT e.employeecode, e.employee, e.department, e.branchcode " +
//	        "    FROM employee e " +
//	        "    WHERE e.orgid = ?3 " +
//	        "      AND (?4 = 'ALL' OR e.branchcode = ?4) " +
//	        "      AND (?5 = 'ALL' OR e.department = ?5) " +
//	        "      AND (?6 = 'ALL' OR e.employeecode = ?6) " +
//	        "      AND e.active = 1 AND  e.taskflag = 1 " +
//	        ") " +
//	        "SELECT " +
//	        "    CONCAT(e.employeecode,'-',e.employee) AS empcode_name,\r\n"
//	        + "    e.employee AS employeename,\r\n"
//	        + "    e.employeecode AS employeecode, " +
//	        "    JSON_ARRAYAGG( " +
//	        "        JSON_OBJECT( " +
//	        "            'date', d.work_date, " +
//	        "            'status', " +
//	        "    CASE " +
//	        "        WHEN t.timesheetid IS NOT NULL THEN UPPER('Timesheet') " +
//	        "        WHEN al.approvalleavesid IS NOT NULL THEN UPPER(al.leavetype) " +
//	        "        WHEN h.holidaysid IS NOT NULL THEN UPPER(h.festival) " +
//	        "        WHEN ww.companyweekoffid IS NOT NULL THEN UPPER(DAYNAME(d.work_date)) " +
//	        "        ELSE UPPER('NO ENTRIES FOUND') " +
//	        "    END, " +
//	        "            'timesheetid', t.timesheetid, " +
//	        "            'totalhours', FORMAT(COALESCE(t.totalhours,0), 2), "+
//	        "            'branch', t.branch, " +
//	        "            'branchCode', t.branchcode, " +
//	        "            'timeSheetDetailsVO', " +
//	        "            ( " +
//	        "                SELECT JSON_ARRAYAGG( " +
//	        "                    JSON_OBJECT( " +
//	        "                        'id', td.timesheetdetailsid, " +
//	        "                        'projectName', td.projectname, " +
//	        "                        'fromTime', td.fromtime, " +
//	        "                        'toTime', td.totime, " +
//	        "                        'description', td.description, " +
//	        "                        'project', td.project, " +
//	        "                        'wip', td.wip, " +
//	        "                        'status', td.status, " +
//	        "                        'remarks', td.remarks " +
//	        "                    ) " +
//	        "                ) " +
//	        "                FROM timesheetdetails td " +
//	        "                WHERE td.timesheetid = t.timesheetid " +
//	        "            ) " +
//	        "        ) " +
//	        "    ) AS timesheets " +
//	        "FROM employee_list e " +
//	        "CROSS JOIN all_dates d " +
//	        "LEFT JOIN timesheet t " +
//	        "       ON t.employeecode = e.employeecode " +
//	        "      AND t.date = d.work_date " +
//	        "      AND t.orgid = ?3 " +
//	        "LEFT JOIN approvalleaves al " +
//	        "       ON al.employeecode = e.employeecode " +
//	        "      AND al.orgid = ?3 " +
//	        "      AND al.approvestatus = 'APPROVED' " +
//	        "      AND al.leavedate = d.work_date " +
//	        "LEFT JOIN holidays h " +
//	        "       ON h.orgid = ?3 " +
//	        "      AND h.holidaydate = d.work_date " +
//	        "LEFT JOIN companyweekoff w " +
//	        "       ON w.companyid = ?3 " +
//	        "      AND FIND_IN_SET(DAYNAME(d.work_date), w.weekoffdays) > 0 " +
//	        "LEFT JOIN weekoffoccurrences ww " +
//	        "       ON ww.companyweekoffid = w.companyweekoffid " +
//	        "      AND (ww.weeknumber = -1 OR ww.weeknumber = FLOOR((DAY(d.work_date)-1)/7)+1) " +
//	        "GROUP BY e.employeecode, e.employee " +
//	        "ORDER BY e.employeecode",
//	nativeQuery = true)
//	List<Object[]> getAllTimeSheetDescByOrgId(String month, String year, Long orgId, String branchCode, String department, String employeeCode);

	
	
	
	@Query(value = "WITH RECURSIVE all_dates AS (\r\n"
			+ "    SELECT DATE(?1) AS work_date\r\n"
			+ "    UNION ALL\r\n"
			+ "    SELECT DATE_ADD(work_date, INTERVAL 1 DAY)\r\n"
			+ "    FROM all_dates\r\n"
			+ "    WHERE work_date < DATE(?2)\r\n"
			+ "),\r\n"
			+ "employee_list AS (\r\n"
			+ "    SELECT e.employeecode, e.employee, e.department, e.branchcode\r\n"
			+ "    FROM employee e\r\n"
			+ "    WHERE e.orgid = ?3\r\n"
			+ "      AND (?4 = 'ALL' OR e.branchcode = ?4)\r\n"
			+ "      AND (?5 = 'ALL' OR e.department = ?5)\r\n"
			+ "      AND (?6 = 'ALL' OR e.employeecode = ?6)\r\n"
			+ "      AND e.active = 1 AND e.taskflag = 1\r\n"
			+ ")\r\n"
			+ "SELECT\r\n"
			+ "    CONCAT(e.employeecode,'-',e.employee) AS empcode_name,\r\n"
			+ "    e.employee AS employeename,\r\n"
			+ "    e.employeecode AS employeecode,\r\n"
			+ "    JSON_ARRAYAGG(\r\n"
			+ "        JSON_OBJECT(\r\n"
			+ "            'date', d.work_date,\r\n"
			+ "            'status',\r\n"
			+ "                                 CASE\r\n"
			+ "	                WHEN t.timesheetid IS NOT NULL THEN UPPER('Timesheet') \r\n"
			+ "	               WHEN al.approvalleavesid IS NOT NULL THEN UPPER(al.leavetype) \r\n"
			+ "	               WHEN h.holidaysid IS NOT NULL THEN UPPER(h.festival) \r\n"
			+ "	              WHEN ww.companyweekoffid IS NOT NULL THEN UPPER(DAYNAME(d.work_date)) \r\n"
			+ "	             ELSE UPPER('NO ENTRIES FOUND') \r\n"
			+ "	           END,\r\n"
			+ "\r\n"
			+ "            'timesheetid', t.timesheetid,\r\n"
			+ "            'totalhours', FORMAT(COALESCE(t.totalhours,0), 2),\r\n"
			+ "            'branch', t.branch,\r\n"
			+ "            'branchCode', t.branchcode,\r\n"
			+ "            'timeSheetDetailsVO',\r\n"
			+ "                (\r\n"
			+ "                    SELECT JSON_ARRAYAGG(subdetail)\r\n"
			+ "                    FROM (\r\n"
			+ "                        -- Tasks\r\n"
			+ "                        SELECT JSON_OBJECT(\r\n"
			+ "                            'id', td.timesheetdetailsid,\r\n"
			+ "                            'projectName', td.projectname,\r\n"
			+ "                            'fromTime', td.fromtime,\r\n"
			+ "                            'toTime', td.totime,\r\n"
			+ "                            'description', td.description,\r\n"
			+ "                            'project', td.project,\r\n"
			+ "                            'wip', td.wip,\r\n"
			+ "                            'status', td.status,\r\n"
			+ "                            'remarks', td.remarks\r\n"
			+ "                        ) AS subdetail\r\n"
			+ "                        FROM timesheetdetails td\r\n"
			+ "                        WHERE td.timesheetid = t.timesheetid\r\n"
			+ "\r\n"
			+ "                        UNION ALL\r\n"
			+ "\r\n"
			+ "                        -- Half-day leave\r\n"
			+ "                        SELECT JSON_OBJECT(\r\n"
			+ "                            'id', NULL,\r\n"
			+ "                            'projectName', NULL,\r\n"
			+ "                            'fromTime', NULL,\r\n"
			+ "                            'toTime', NULL,\r\n"
			+ "                            'description',\r\n"
			+ "                                CASE\r\n"
			+ "                                    WHEN al.totalleave = 0.50 THEN CONCAT(UPPER(al.leavetype), ' (HALF DAY)')\r\n"
			+ "                                    ELSE NULL\r\n"
			+ "                                END,\r\n"
			+ "                            'project', NULL,\r\n"
			+ "                            'wip', NULL,\r\n"
			+ "                            'status', 'LEAVE',\r\n"
			+ "                            'remarks', NULL\r\n"
			+ "                        ) AS subdetail\r\n"
			+ "                        FROM approvalleaves al\r\n"
			+ "                        WHERE al.employeecode = e.employeecode\r\n"
			+ "                          AND al.leavedate = d.work_date\r\n"
			+ "                          AND al.approvestatus = 'APPROVED'\r\n"
			+ "                          AND al.totalleave = 0.50\r\n"
			+ "                    ) AS combined\r\n"
			+ "                )\r\n"
			+ "        )\r\n"
			+ "    ) AS timesheets\r\n"
			+ "FROM employee_list e\r\n"
			+ "CROSS JOIN all_dates d\r\n"
			+ "LEFT JOIN timesheet t\r\n"
			+ "       ON t.employeecode = e.employeecode\r\n"
			+ "      AND t.date = d.work_date\r\n"
			+ "      AND t.orgid = ?3\r\n"
			+ "LEFT JOIN approvalleaves al\r\n"
			+ "       ON al.employeecode = e.employeecode\r\n"
			+ "      AND al.orgid = ?3\r\n"
			+ "      AND al.approvestatus = 'APPROVED'\r\n"
			+ "      AND al.leavedate = d.work_date\r\n"
			+ "LEFT JOIN holidays h\r\n"
			+ "       ON h.orgid = ?3\r\n"
			+ "      AND h.holidaydate = d.work_date\r\n"
			+ "LEFT JOIN companyweekoff w\r\n"
			+ "       ON w.companyid = ?3\r\n"
			+ "      AND FIND_IN_SET(DAYNAME(d.work_date), w.weekoffdays) > 0\r\n"
			+ "LEFT JOIN weekoffoccurrences ww\r\n"
			+ "       ON ww.companyweekoffid = w.companyweekoffid\r\n"
			+ "      AND (ww.weeknumber = -1 OR ww.weeknumber = FLOOR((DAY(d.work_date)-1)/7)+1)\r\n"
			+ "GROUP BY e.employeecode, e.employee\r\n"
			+ "ORDER BY e.employeecode;\r\n"
			+ "\r\n"
			+ "\r\n"
			+ " \r\n"
			+ "",
	nativeQuery = true)
	List<Object[]> getAllTimeSheetDescByOrgId(
		    String fromDate,
		    String toDate,
		    Long orgId,
		    String branchCode,
		    String department,
		    String employeeCode);
	
}
