package com.efit.hrms.repo;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.CreateOfferVO;

@Repository
public interface CreateOfferRepo extends JpaRepository<CreateOfferVO, Long>{

	@Query(nativeQuery = true, value = "select * from createoffer where createofferid=?1 ")
	CreateOfferVO getCreateOfferById(Long id);

	@Query(nativeQuery = true, value = "select * from createoffer where orgid=?1 and branchCode=?2 ")
	List<CreateOfferVO> getCreateOfferByOrgId(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "  SELECT *\r\n"
			+ "    FROM createoffer\r\n"
			+ "    WHERE orgid = ?1\r\n"
			+ "      AND LOWER(TRIM(branchcode)) = LOWER(TRIM(?2))\r\n"
			+ "      AND (?3 = 'ALL' OR LOWER(TRIM(approvestatus)) = LOWER(TRIM(?3)))\r\n"
			+ "      AND (?4 = 'ALL' OR LOWER(TRIM(department)) = LOWER(TRIM(?4)))")
		List<CreateOfferVO> getCreateOfferByOrgIdAndDepartment(
		    Long orgId, String branchCode, String status, String department
		);


	@Query(nativeQuery = true, value = "select * from createoffer where orgid=?1 and branchCode=?2 and approvestatus='PENDING' ")
	List<CreateOfferVO> getPendingCreateOfferByOrgId(Long orgId, String branchCode);

//	@Query(value = "SELECT " +
//	        "a.createofferid, a.active, a.additionalbenefits, a.branch, a.branchcode, a.candidatename, " +
//	        "a.createdon, a.modifiedon, a.createdby, a.department, a.finyear, a.joiningdate, a.noticeperiod, " +
//	        "a.orgid, a.position, a.probationperiod, a.reportingemail, a.reportingperson, a.reportingcode, " +
//	        "a.screencode, a.screenname, a.specialtermscondition, a.templatetype, a.modifiedby, a.worklocation, " +
//	        "a.workhours, a.approveby, a.approveon, a.approvestatus, " +
//	        "  c.address, " +
//	        " c.companycode, c.companyname, " +
//	        " c.companylogo " +
//	        "FROM createoffer a " +
//	        "JOIN company c ON c.companyid = a.orgid " +
//	        "WHERE a.orgid = ?1 AND a.branchcode = ?2 AND a.approvestatus = 'PENDING'",
//	        nativeQuery = true)
//	List<Object[]> getApprovedCreateOfferByCompany(Long orgId, String branchCode, String candidateName);
	
	
	@Query(value = "SELECT " +
	        "a.createofferid, a.active, a.additionalbenefits, a.branch, a.branchcode, a.candidatename, " +
	        "a.createdon, a.modifiedon, a.createdby, a.department, a.finyear, a.joiningdate, a.noticeperiod, " +
	        "a.orgid, a.position, a.probationperiod, a.reportingemail, a.reportingperson, a.reportingcode, " +
	        "a.screencode, a.screenname, a.specialtermscondition, a.templatetype, a.modifiedby, a.worklocation, " +
	        "a.workhours, a.approveby, a.approveon, a.approvestatus, a.candidateid," +
	        "c.address, c.companycode, c.companyname, c.companylogo " +
	        "FROM createoffer a " +
	        "JOIN company c ON c.companyid = a.orgid " +
	        "WHERE a.orgid = ?1 AND a.branchcode = ?2 AND a.approvestatus = 'APPROVED' AND a.candidatename = ?3",
	        nativeQuery = true)
	List<Object[]> getApprovedCreateOfferByCompany(Long orgId, String branchCode, String candidateName);

	CreateOfferVO findByOrgIdAndIdAndCandidateName(Long orgId, Long id, String employeeCode);

	@Query(nativeQuery = true, value = " SELECT \r\n"
			+ "    p.pending_count AS offer_pending,\r\n"
			+ "    a.approved_count AS offer_approved,\r\n"
			+ "    r.rejected_count AS offer_rejected,\r\n"
			+ "    (p.pending_count + a.approved_count + r.rejected_count) AS offer_total_count\r\n"
			+ "FROM\r\n"
			+ "    (SELECT COUNT(*) AS pending_count\r\n"
			+ "     FROM createoffer\r\n"
			+ "     WHERE orgid = ?1\r\n"
			+ "       AND branchcode = ?2\r\n"
			+ "       AND approvestatus = 'PENDING') p,\r\n"
			+ "       \r\n"
			+ "    (SELECT COUNT(*) AS approved_count\r\n"
			+ "     FROM createoffer\r\n"
			+ "     WHERE orgid = ?1\r\n"
			+ "       AND branchcode = ?2\r\n"
			+ "       AND approvestatus = 'APPROVED') a,\r\n"
			+ "       \r\n"
			+ "    (SELECT COUNT(*) AS rejected_count\r\n"
			+ "     FROM createoffer\r\n"
			+ "     WHERE orgid = ?1\r\n"
			+ "       AND branchcode = ?2\r\n"
			+ "       AND approvestatus = 'REJECTED') r")
	List<Object[]> getCreateOfferCountByOrgId(Long orgId, String branchCode);
	
	
	



}
