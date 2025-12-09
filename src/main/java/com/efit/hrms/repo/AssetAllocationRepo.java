package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.AssetAllocationVO;

@Repository
public interface AssetAllocationRepo extends JpaRepository<AssetAllocationVO, Long> {

	@Query(nativeQuery = true, value = "SELECT am.assetname, am.assetcode\r\n" + "FROM assetmaster am\r\n"
			+ "WHERE am.orgid = 1000000001\r\n" + "  AND am.branchcode = 'WDSBLR'\r\n" + "  AND NOT EXISTS (\r\n"
			+ "        SELECT 1\r\n" + "        FROM assetallocation aa\r\n"
			+ "        WHERE aa.assetcode = am.assetcode\r\n" + "          AND aa.orgid = am.orgid\r\n"
			+ "          AND aa.branchcode = am.branchcode\r\n" + "      );\r\n" + "")
	List<Object[]> getAssetNameCodeByOrgId(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "select * from assetallocation where orgid=?1 and branchcode=?2")
	List<AssetAllocationVO> getAssetAllocationByOrgId(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "select * from assetallocation where assetallocationid=?1")
	AssetAllocationVO getAssetAllocationById(Long id);

	@Query(nativeQuery = true, value = "SELECT \r\n" + "    totalasset,\r\n" + "    allocatedasset,\r\n"
			+ "    (totalasset - allocatedasset) AS availableasset\r\n" + "FROM (\r\n" + "    SELECT \r\n"
			+ "        (SELECT COUNT(*) \r\n" + "         FROM assetmaster \r\n"
			+ "         WHERE orgid = ?1 AND branchcode = ?2 and active=1) AS totalasset,\r\n"
			+ "        (SELECT COUNT(*) \r\n" + "         FROM assetallocation \r\n"
			+ "         WHERE orgid = ?1 AND branchcode = ?2 and active=1) AS allocatedasset\r\n"
			+ ") AS asset_summary")
	List<Object[]> getAssetCountByOrgId(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "SELECT \r\n" + "    a.assetname,\r\n" + "       a.category,\r\n"
			+ "       CASE \r\n" + "        WHEN b.assetcode IS NOT NULL THEN 'ASSIGNED'\r\n"
			+ "        ELSE 'AVAILABLE'\r\n" + "    END AS status,\r\n" + "      b.employeename,\r\n"
			+ "    a.location\r\n" + "  \r\n" + " \r\n" + "FROM assetmaster a\r\n" + "LEFT JOIN assetallocation b \r\n"
			+ "    ON a.assetname = b.assetname \r\n" + "    AND a.assetcode = b.assetcode\r\n"
			+ "    AND b.orgid = a.orgid \r\n" + "    AND b.branchcode = a.branchcode\r\n" + "WHERE a.orgid = ?1\r\n"
			+ "  AND a.branchcode = ?2")
	List<Object[]> getAssetDashboardByOrgId(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "SELECT \r\n" + "    e.employee,\r\n" + "    e.employeecode,\r\n"
			+ "    e.email,\r\n" + "    e.branch,\r\n" + "    e.branchcode,\r\n" + "    e.department,\r\n"
			+ "    e.designation,\r\n" + "    a.assetname,\r\n" + "    a.assetcode,\r\n" + "    a.assetcondition,\r\n"
			+ "    a.allocationdate,\r\n" + "    a.expectedreturndate\r\n" + "FROM employee e\r\n"
			+ "LEFT JOIN assetallocation a\r\n" + "    ON e.employeecode = a.employeecode\r\n"
			+ "   AND e.orgid = a.orgid\r\n" + "   AND e.branchcode = a.branchcode\r\n" + "WHERE e.orgid = ?1\r\n"
			+ "  AND e.branchcode = ?2\r\n" + "  AND e.employeecode = ?3\r\n" + "ORDER BY a.allocationdate DESC \r\n"
			+ "")
	List<Object[]> getAssetAllocationReportByOrgId(Long orgId, String branchCode, String employeeCode);

	@Query(nativeQuery = true, value = "SELECT \r\n"
			+ "    a.assetcode,\r\n"
			+ "    a.assetname,\r\n"
			+ "    a.serialnumber,\r\n"
			+ "    a.category,\r\n"
			+ "    a.brand,\r\n"
			+ "    a.model,\r\n"
			+ "    SUM(a.qty) AS qty\r\n"
			+ "FROM assetstock a\r\n"
			+ "WHERE \r\n"
			+ "    a.orgid =?1 \r\n"
			+ "     and a.branchcode=?2\r\n"
			+ "    AND (\r\n"
			+ "            a.assetcode NOT IN (\r\n"
			+ "                SELECT DISTINCT assetcode \r\n"
			+ "                FROM assetstock \r\n"
			+ "                WHERE sourcescreen = 'ASSET ALLOCATION'\r\n"
			+ "            )\r\n"
			+ "         OR\r\n"
			+ "            a.assetcode IN (\r\n"
			+ "                SELECT DISTINCT assetcode \r\n"
			+ "                FROM assetstock \r\n"
			+ "                WHERE sourcescreen = 'ASSET RETURN'\r\n"
			+ "            )\r\n"
			+ "        )\r\n"
			+ "GROUP BY \r\n"
			+ "    a.assetcode,\r\n"
			+ "    a.assetname,\r\n"
			+ "    a.serialnumber,\r\n"
			+ "    a.category,\r\n"
			+ "    a.brand,\r\n"
			+ "    a.model\r\n"
			+ "HAVING SUM(a.qty) > 0")
	List<Object[]> getAssetAllocationDetails(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "select a.assetcode,a.assetname,a.serialnumber,a.category,a.brand,a.model,a1.employeecode,a1.employeename,sum(a.qty) as qty from assetstock a left join \r\n"
			+ "assetallocation a1 on a.assetcode=a1.assetcode\r\n" + "where a.sourcescreen='ASSET ALLOCATION' \r\n"
			+ "and  a.orgid =?1\r\n"
			+ "    AND a.branchcode =?2 and a1.employeecode=?3 and a.assetcode not in (SELECT DISTINCT assetcode \r\n"
			+ "		       FROM assetstock \r\n" + "   WHERE sourcescreen = 'ASSET RETURN')	\r\n" + "group by\r\n"
			+ " a.assetcode,a.assetname,a.serialnumber,a.category,a.brand,a.model,a1.employeecode,a1.employeename")
	List<Object[]> getAssetAllocationListAll(Long orgId, String branchCode, String employeeCode);

}
