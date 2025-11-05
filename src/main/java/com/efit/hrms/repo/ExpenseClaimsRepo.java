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
			+ "    p.pending_count As pending,\r\n"
			+ "    a.approved_count As approved,\r\n"
			+ "    d.rejected_count As rejected,\r\n"
			+ "    (p.pending_count + a.approved_count+d.rejected_count) AS totalcount,\r\n"
			+ "    (p.pending_amount + a.approved_amount) AS totalamount\r\n"
			+ "FROM \r\n"
			+ "    (SELECT \r\n"
			+ "         COUNT(*) AS pending_count,\r\n"
			+ "         COALESCE(SUM(amount), 0) AS pending_amount\r\n"
			+ "     FROM expenseclaims \r\n"
			+ "     WHERE employeecode = ?3 \r\n"
			+ "       AND orgid = ?1 \r\n"
			+ "       AND branchcode = ?2\r\n"
			+ "       AND approvestatus = 'PENDING') p,\r\n"
			+ "    (SELECT \r\n"
			+ "         COUNT(*) AS approved_count,\r\n"
			+ "         COALESCE(SUM(amount), 0) AS approved_amount\r\n"
			+ "     FROM expenseclaims \r\n"
			+ "     WHERE employeecode = ?3 \r\n"
			+ "       AND orgid = ?1 \r\n"
			+ "       AND branchcode = ?2\r\n"
			+ "       AND approvestatus = 'APPROVED') a,\r\n"
			+ "        (SELECT \r\n"
			+ "         COUNT(*) AS rejected_count,\r\n"
			+ "		COALESCE(SUM(amount), 0) AS rejected_amount\r\n"
			+ "         FROM expenseclaims \r\n"
			+ "     WHERE employeecode = ?3 \r\n"
			+ "       AND orgid = ?1 \r\n"
			+ "       AND branchcode = ?2 \r\n"
			+ "       AND approvestatus = 'REJECTED') d")
	List<Object[]> getExpenseCountByOrgId(Long orgId, String branchCode, String employeeCode);

}


