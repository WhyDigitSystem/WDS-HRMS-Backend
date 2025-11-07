package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.ExpenseClaimsVO;

@Repository
public interface ExpenseClaimsRepo extends JpaRepository<ExpenseClaimsVO, Long>{

	@Query(nativeQuery = true, value = "select * from expenseclaims where orgid=?1 and branchcode=?2 and employeecode=?3")
	List<ExpenseClaimsVO> getExpenseClaimsByOrgId(Long orgId, String branchCode, String employeeCode);

	@Query(nativeQuery = true, value = "select * from expenseclaims where expenseclaimsid=?1")
	ExpenseClaimsVO getExpenseClaimsById(Long id);

	ExpenseClaimsVO findByOrgIdAndIdAndEmployeeCode(Long orgId, Long id, String employeeCode);

	@Query(nativeQuery = true,value = "select * from expenseclaims a where a.orgid=?1 and reportingPersonCode=?2 and branchcode=?3 and approvestatus='PENDING' ")
	List<ExpenseClaimsVO> getExpenseClaimsForDashBoard(Long orgId, String reportingPersonCode, String branchCode);

	@Query(nativeQuery = true,value = "SELECT \r\n"
			+ "    a.expenseclaimsid AS id,\r\n"
			+ "    a.screenname AS type,\r\n"
			+ "    a.expensetitle AS title,\r\n"
			+ "    a.employeename AS employeename,\r\n"
			+ "    a.employeecode AS employeeCode,\r\n"
			+ "    a.amount AS amount,\r\n"
			+ "    a.createdon AS submitted,\r\n"
			+ "    a.approvestatus AS status,\r\n"
			+ "    des.expenselimit AS expenselimit\r\n"
			+ "FROM expenseclaims a\r\n"
			+ "JOIN travelrequests b \r\n"
			+ "    ON a.employeecode = b.employeecode\r\n"
			+ "JOIN employee e \r\n"
			+ "    ON a.employeecode = e.employeecode\r\n"
			+ "JOIN designation des \r\n"
			+ "    ON des.designationname= e.designation\r\n"
			+ "   AND des.orgid = a.orgid\r\n"
			+ "WHERE a.orgid = ?1\r\n"
			+ "  AND a.branchcode = ?2\r\n"
			+ "  AND (a.employeecode = ?3 OR a.reportingpersoncode = ?3 )\r\n"
			+ "\r\n"
			+ "UNION\r\n"
			+ "\r\n"
			+ "SELECT \r\n"
			+ "    c.travelrequestsid AS id,\r\n"
			+ "    c.screenname AS type,\r\n"
			+ "    c.traveltitle AS title,\r\n"
			+ "    c.employeename AS employeename,\r\n"
			+ "    c.employeecode AS employeeCode,\r\n"
			+ "    c.estimatedcost AS amount,\r\n"
			+ "    c.createdon AS submitted,\r\n"
			+ "    c.approvestatus AS status,\r\n"
			+ "    des.expenselimit AS expenselimit\r\n"
			+ "FROM travelrequests c\r\n"
			+ "JOIN expenseclaims d \r\n"
			+ "    ON c.employeecode = d.employeecode\r\n"
			+ "JOIN employee e \r\n"
			+ "    ON c.employeecode = e.employeecode\r\n"
			+ "JOIN designation des \r\n"
			+ "    ON des.designationname = e.designation\r\n"
			+ "   AND des.orgid = c.orgid\r\n"
			+ "WHERE c.orgid = ?1\r\n"
			+ "  AND c.branchcode = ?2\r\n"
			+ "  AND (c.employeecode = ?3 OR c.reportingpersoncode = ?3 )")
	List<Object[]> getApprovalExpenseAndTravelByOrgId(Long orgId, String branchCode, String employeeCode);

	@Query(nativeQuery = true,value = "SELECT \r\n"
			+ "    -- Expense Summary\r\n"
			+ "    p.pending_count AS expense_pending,\r\n"
			+ "    a.approved_count AS expense_approved,\r\n"
			+ "    d.rejected_count AS expense_rejected,\r\n"
			+ "    (p.pending_count + a.approved_count + d.rejected_count) AS expense_total_count,\r\n"
			+ "    a.approved_amount AS expense_total_amount,\r\n"
			+ "\r\n"
			+ "    -- Travel Summary\r\n"
			+ "    t.pending_count AS travel_pending,\r\n"
			+ "    t1.approved_count AS travel_approved,\r\n"
			+ "    t2.rejected_count AS travel_rejected,\r\n"
			+ "    (t.pending_count + t1.approved_count + t2.rejected_count) AS travel_total_count,\r\n"
			+ "    t1.approved_amount AS travel_total_amount\r\n"
			+ "FROM \r\n"
			+ "    -- Expense Subqueries\r\n"
			+ "    (SELECT \r\n"
			+ "         COUNT(*) AS pending_count,\r\n"
			+ "         COALESCE(SUM(amount), 0) AS pending_amount\r\n"
			+ "     FROM expenseclaims \r\n"
			+ "     WHERE ( employeecode = ?3 OR reportingpersoncode=?3 )\r\n"
			+ "       AND orgid = ?1\r\n"
			+ "       AND branchcode = ?2\r\n"
			+ "       AND approvestatus = 'PENDING'\r\n"
			+ "       AND MONTH(expensedate) = ?4\r\n"
			+ "       AND YEAR(expensedate) = ?5) p,\r\n"
			+ "       \r\n"
			+ "    (SELECT \r\n"
			+ "         COUNT(*) AS approved_count,\r\n"
			+ "         COALESCE(SUM(amount), 0) AS approved_amount\r\n"
			+ "     FROM expenseclaims \r\n"
			+ "     WHERE ( employeecode = ?3 OR reportingpersoncode=?3 )\r\n"
			+ "       AND orgid = ?1\r\n"
			+ "       AND branchcode = ?2\r\n"
			+ "       AND approvestatus = 'APPROVED'\r\n"
			+ "       AND MONTH(expensedate) = ?4\r\n"
			+ "       AND YEAR(expensedate) = ?5) a,\r\n"
			+ "       \r\n"
			+ "    (SELECT \r\n"
			+ "         COUNT(*) AS rejected_count,\r\n"
			+ "         COALESCE(SUM(amount), 0) AS rejected_amount\r\n"
			+ "     FROM expenseclaims \r\n"
			+ "     WHERE ( employeecode = ?3 OR reportingpersoncode=?3 )\r\n"
			+ "       AND orgid = ?1\r\n"
			+ "       AND branchcode = ?2\r\n"
			+ "       AND approvestatus = 'REJECTED'\r\n"
			+ "       AND MONTH(expensedate) = ?4\r\n"
			+ "       AND YEAR(expensedate) = ?5) d,\r\n"
			+ "       \r\n"
			+ "    -- Travel Subqueries\r\n"
			+ "    (SELECT \r\n"
			+ "         COUNT(*) AS pending_count,\r\n"
			+ "         COALESCE(SUM(estimatedcost), 0) AS pending_amount\r\n"
			+ "     FROM travelrequests \r\n"
			+ "     WHERE ( employeecode = ?3 OR reportingpersoncode=?3 )\r\n"
			+ "       AND orgid = ?1\r\n"
			+ "       AND branchcode = ?2\r\n"
			+ "       AND approvestatus = 'PENDING'\r\n"
			+ "       AND MONTH(departuredate) = ?4\r\n"
			+ "       AND YEAR(departuredate) = ?5) t,\r\n"
			+ "       \r\n"
			+ "    (SELECT \r\n"
			+ "         COUNT(*) AS approved_count,\r\n"
			+ "         COALESCE(SUM(estimatedcost), 0) AS approved_amount\r\n"
			+ "     FROM travelrequests \r\n"
			+ "     WHERE ( employeecode = ?3 OR reportingpersoncode=?3 )\r\n"
			+ "       AND orgid = ?1\r\n"
			+ "       AND branchcode = ?2\r\n"
			+ "       AND approvestatus = 'APPROVED'\r\n"
			+ "       AND MONTH(departuredate) = ?4\r\n"
			+ "       AND YEAR(departuredate) = ?5) t1,\r\n"
			+ "       \r\n"
			+ "    (SELECT \r\n"
			+ "         COUNT(*) AS rejected_count,\r\n"
			+ "         COALESCE(SUM(estimatedcost), 0) AS rejected_amount\r\n"
			+ "     FROM travelrequests \r\n"
			+ "     WHERE ( employeecode = ?3 OR reportingpersoncode=?3 )\r\n"
			+ "       AND orgid = ?1\r\n"
			+ "       AND branchcode = ?2\r\n"
			+ "       AND approvestatus = 'REJECTED'\r\n"
			+ "       AND MONTH(departuredate) = ?4\r\n"
			+ "       AND YEAR(departuredate) = ?5) t2;\r\n"
			+ "")
	List<Object[]> getExpenseCountByOrgId(Long orgId, String branchCode, String employeeCode, Long month, Long year);

	@Query(value = "SELECT category, "
	        + "SUM(CASE WHEN MONTH(date_field) = 1 THEN amount ELSE 0 END) AS Jan, "
	        + "SUM(CASE WHEN MONTH(date_field) = 2 THEN amount ELSE 0 END) AS Feb, "
	        + "SUM(CASE WHEN MONTH(date_field) = 3 THEN amount ELSE 0 END) AS Mar, "
	        + "SUM(CASE WHEN MONTH(date_field) = 4 THEN amount ELSE 0 END) AS Apr, "
	        + "SUM(CASE WHEN MONTH(date_field) = 5 THEN amount ELSE 0 END) AS May, "
	        + "SUM(CASE WHEN MONTH(date_field) = 6 THEN amount ELSE 0 END) AS Jun, "
	        + "SUM(CASE WHEN MONTH(date_field) = 7 THEN amount ELSE 0 END) AS Jul, "
	        + "SUM(CASE WHEN MONTH(date_field) = 8 THEN amount ELSE 0 END) AS Aug, "
	        + "SUM(CASE WHEN MONTH(date_field) = 9 THEN amount ELSE 0 END) AS Sep, "
	        + "SUM(CASE WHEN MONTH(date_field) = 10 THEN amount ELSE 0 END) AS Oct, "
	        + "SUM(CASE WHEN MONTH(date_field) = 11 THEN amount ELSE 0 END) AS Nov, "
	        + "SUM(CASE WHEN MONTH(date_field) = 12 THEN amount ELSE 0 END) AS `Dec` "
	        + "FROM ( "
	        + "SELECT 'Travel' AS category, t.estimatedcost AS amount, t.departuredate AS date_field "
	        + "FROM travelrequests t "
	        + "WHERE t.orgid = ?1 "
	        + "AND t.branchcode = ?2 "
	        + "AND (t.employeecode = ?3 OR t.reportingpersoncode = ?3) "
	        + "AND t.approvestatus = 'APPROVED' "
	        + "AND YEAR(t.departuredate) = ?4 "
	        + "AND MONTH(t.departuredate) = ?5 "
	        + "UNION ALL "
	        + "SELECT e.category AS category, e.amount AS amount, e.expensedate AS date_field "
	        + "FROM expenseclaims e "
	        + "WHERE e.orgid = ?1 "
	        + "AND e.branchcode = ?2 "
	        + "AND (e.employeecode = ?3 OR e.reportingpersoncode = ?3) "
	        + "AND e.approvestatus = 'APPROVED' "
	        + "AND YEAR(e.expensedate) = ?4 "
	        + "AND MONTH(e.expensedate) = ?5 "
	        + ") x "
	        + "GROUP BY category ORDER BY category",
	        nativeQuery = true)
	List<Object[]> getExpenseGraphByOrgId(Long orgId, String branchCode, String employeeCode, Long year, Long month);



}


