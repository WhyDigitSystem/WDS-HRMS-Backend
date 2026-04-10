package com.efit.hrms.repo;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.InitiateSeparationVO;

@Repository
public interface InitiateSeparationRepo  extends JpaRepository<InitiateSeparationVO, Long>{

	@Query(nativeQuery = true,value="select * from initiateseparation where initiateseparationid=?1")
	InitiateSeparationVO getInitiateSeparationById(Long id);

	@Query(nativeQuery = true,value="select * from initiateseparation where orgid=?1 and branchcode=?2 and active=1 ")
	List<InitiateSeparationVO> getInitiateSeparationByOrgId(Long orgId, String branchCode);

	@Query(nativeQuery = true,value="  SELECT * \r\n"
			+ "		    FROM initiateseparation \r\n"
			+ "		    WHERE orgid = ?1 \r\n"
			+ "		      AND branchcode = ?2 \r\n"
			+ "		      AND (department = ?3 OR ?3 = 'ALL')\r\n"
			+ "		      AND (separationtype = ?4 OR ?4 = 'ALL')\r\n"
			+ "		      AND ( ?5 = 'ALL' OR employeecode = ?5)\r\n"
			+ "		      AND active = 1 ")
	List<InitiateSeparationVO> getInitiateSeparationByDepartment(Long orgId, String branchCode,String department, String type, String empCode);

	@Query(nativeQuery = true, value = "select sum(totalcount) totalcount ,sum(pendingcount)pendingcount,sum(approvedcount)approvedcount from (\r\n"
			+ "select count(*) totalcount,0 pendingcount,0 approvedcount   from initiateseparation  where orgid=?1 and branchcode=?2\r\n"
			+ "union \r\n"
			+ "select 0 totalcount,count(*) pendingcount,0 approvedcount  from initiateseparation  where orgid=?1 and branchcode=?2  and  status='PENDING' \r\n"
			+ "union \r\n"
			+ "select 0 totalcount,0 pendingcount,count(*) approvedcount  from initiateseparation  where orgid=?1 and branchcode=?2  and  status='APPROVED' \r\n"
			+ ") a")
	List<Object[]> getInitiateSeparationCountByOrgId(Long orgId, String branchCode);

	@Query("SELECT i.createdBy FROM InitiateSeparationVO i where employeecode=?1 order by initiateseparationid desc")
	String getCreatedBy(String employeeCode);

	@Query(nativeQuery = true,value="select * from initiateseparation where orgid=?1 and branchcode=?2  AND ( ?3 = 'ALL' OR employeecode = ?3) and active=1 ")
	List<InitiateSeparationVO> getInitiateSeparationByOrgIdforclearance(Long orgId, String branchCode, String empCode);

	@Query(nativeQuery = true,value="SELECT \r\n"
			+ "    c.clearanceitem, \r\n"
			+ "    h.department,\r\n"
			+ "    h.departmentcode \r\n"
			+ "FROM clearancemanagement c\r\n"
			+ "JOIN initiateseparation i \r\n"
			+ "    ON i.initiateseparationid = c.initiateseparationid\r\n"
			+ "JOIN clearancedetails d \r\n"
			+ "    ON c.clearanceitem = d.clearancename\r\n"
			+ "JOIN departmenthead h \r\n"
			+ "    ON h.departmentheadid = d.departmentheadid\r\n"
			+ "WHERE i.employeecode =?1 and h.orgid=?2 and h.branchcode=?3 and i.active=1 ")
	List<Object[]> getCleranceDetailsByEmployeeCode(String employeeCode, Long orgId, String branchCode);

	@Query(nativeQuery = true,value="select * from initiateseparation where orgid=?1 and branchCode=?2 and active=1 AND CURDATE() >= lastworkingdate and status='APPROVED' ")
	List<Map<String, Object>> getSeparationEmployeeForSettlement(Long orgId, String branchCode);


}
