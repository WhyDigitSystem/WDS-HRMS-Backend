package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.IncrementManagementVO;
import com.efit.hrms.entity.SalaryStructureVO;

@Repository
public interface IncrementManagementRepo extends JpaRepository<IncrementManagementVO, Long>{

	IncrementManagementVO findByOrgIdAndIdAndEmployeeCode(Long orgId, Long id, String employeeCode);

	@Query(nativeQuery = true,value = "select * from incrementmanagement a where a.orgid=?1 and reportingPersonCode=?2 and branchcode=?3 and approvestatus='PENDING' ")
	List<IncrementManagementVO> getIncrementManagementForDashBoard(Long orgId, String reportingPersonCode,
			String branchCode);

	@Query("SELECT s FROM SalaryStructureVO s " +
		       "WHERE s.orgId = :orgId " +
		       "AND s.employeeCode = :employeeCode " +
		       "AND s.effectiveFrom = (" +
		       "  SELECT MAX(s2.effectiveFrom) " +
		       "  FROM SalaryStructureVO s2 " +
		       "  WHERE s2.orgId = s.orgId " +
		       "  AND s2.employeeCode = s.employeeCode " +
		       "  AND s2.effectiveFrom <= CURRENT_DATE" +
		       ")")
		List<SalaryStructureVO> getLatestSalaryStructureByOrgId(@Param("orgId") Long orgId,
		                                                        @Param("employeeCode") String employeeCode);

	@Query(nativeQuery = true,value = "SELECT \r\n"
			+ "    ROW_NUMBER() OVER (ORDER BY s.effectivefrom DESC) AS id,\r\n"
			+ "    YEAR(s.effectivefrom) AS year,\r\n"
			+ "    DATE_FORMAT(s.effectivefrom, '%Y-%m-%d') AS new_salary_effectivedate,\r\n"
			+ "    COALESCE(LAG(s.sumofearning) OVER (ORDER BY s.effectivefrom), 0) AS previous_salary,\r\n"
			+ "    COALESCE(s.sumofearning, 0) AS new_salary,\r\n"
			+ "    (\r\n"
			+ "        SELECT im.totalctcpercentage \r\n"
			+ "        FROM incrementmanagement im\r\n"
			+ "        WHERE im.orgid = s.orgid\r\n"
			+ "          AND im.employeecode = s.employeecode\r\n"
			+ "          AND im.effectivefrom = s.effectivefrom\r\n"
			+ "        LIMIT 1\r\n"
			+ "    ) AS totalctcpercentage,\r\n"
			+ "     (\r\n"
			+ "        SELECT im.approveby \r\n"
			+ "        FROM incrementmanagement im\r\n"
			+ "        WHERE im.orgid = s.orgid\r\n"
			+ "          AND im.employeecode = s.employeecode\r\n"
			+ "          AND im.effectivefrom = s.effectivefrom\r\n"
			+ "        LIMIT 1\r\n"
			+ "    ) AS approvedby,\r\n"
			+ "    (\r\n"
			+ "        SELECT im.approvestatus \r\n"
			+ "        FROM incrementmanagement im\r\n"
			+ "        WHERE im.orgid = s.orgid\r\n"
			+ "          AND im.employeecode = s.employeecode\r\n"
			+ "          AND im.effectivefrom = s.effectivefrom\r\n"
			+ "        LIMIT 1\r\n"
			+ "    ) AS status\r\n"
			+ "FROM salarystructure s\r\n"
			+ "WHERE s.orgid = ?1\r\n"
			+ "  AND s.employeecode = ?2\r\n"
			+ "  AND s.cancel = 0\r\n"
			+ "ORDER BY s.effectivefrom DESC ")
	List<Object[]> getSalaryHistoryforIncrement(Long orgId, String employeeCode);


}
