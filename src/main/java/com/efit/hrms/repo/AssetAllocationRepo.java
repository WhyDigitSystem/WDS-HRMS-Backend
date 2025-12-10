package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.AssetAllocationVO;

@Repository
public interface AssetAllocationRepo extends JpaRepository<AssetAllocationVO, Long> {

	@Query(nativeQuery = true, value = "SELECT am.assetname, am.assetcode\r\n" + "FROM assetmaster am\r\n"
			+ "WHERE am.orgid =?1 \r\n" + "  AND am.branchcode =?2 \r\n" + "  AND NOT EXISTS (\r\n"
			+ "        SELECT 1\r\n" + "        FROM assetallocation aa\r\n"
			+ "        WHERE aa.assetcode = am.assetcode\r\n" + "          AND aa.orgid = am.orgid\r\n"
			+ "          AND aa.branchcode = am.branchcode\r\n" + "      );\r\n" + "")
	List<Object[]> getAssetNameCodeByOrgId(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "select * from assetallocation where orgid=?1 and branchcode=?2")
	List<AssetAllocationVO> getAssetAllocationByOrgId(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "select * from assetallocation where assetallocationid=?1")
	AssetAllocationVO getAssetAllocationById(Long id);

	@Query(nativeQuery = true, value = "WITH total_assets AS (\r\n"
			+ "    SELECT COUNT(*) AS totalcount\r\n"
			+ "    FROM assetstock\r\n"
			+ "    WHERE orgid =?1\r\n"
			+ "      AND branchcode =?2\r\n"
			+ "      AND assetstatus = 'A'\r\n"
			+ "      AND sourcescreencode = 'AM'\r\n"
			+ "),\r\n"
			+ "\r\n"
			+ "latest_status AS (\r\n"
			+ "    SELECT\r\n"
			+ "        assetcode,\r\n"
			+ "        serialnumber,\r\n"
			+ "        sourcescreencode,\r\n"
			+ "        astatus,\r\n"
			+ "        ROW_NUMBER() OVER (\r\n"
			+ "            PARTITION BY assetcode, serialnumber\r\n"
			+ "            ORDER BY assetstockid DESC\r\n"
			+ "        ) AS rn\r\n"
			+ "    FROM assetstock\r\n"
			+ "    WHERE orgid = ?1\r\n"
			+ "      AND branchcode = ?2\r\n"
			+ "      AND sourcescreencode IN ('AL','AR')\r\n"
			+ "),\r\n"
			+ "\r\n"
			+ "allocated_assets AS (\r\n"
			+ "    SELECT COUNT(*) AS allocated\r\n"
			+ "    FROM latest_status\r\n"
			+ "    WHERE rn = 1\r\n"
			+ "      AND sourcescreencode = 'AL'\r\n"
			+ "      AND astatus = 'A'   -- active allocation\r\n"
			+ ")\r\n"
			+ "\r\n"
			+ "SELECT\r\n"
			+ "    t.totalcount,\r\n"
			+ "    a.allocated,\r\n"
			+ "    (t.totalcount - a.allocated) AS avalible\r\n"
			+ "FROM total_assets t, allocated_assets a")
	List<Object[]> getAssetCountByOrgId(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "WITH base_assets AS (\r\n"
			+ "    SELECT \r\n"
			+ "        assetcode,\r\n"
			+ "        assetname,\r\n"
			+ "        serialnumber,\r\n"
			+ "        model,\r\n"
			+ "        location AS base_location,\r\n"
			+ "        locationcode AS base_locationcode,\r\n"
			+ "        category\r\n"
			+ "    FROM assetstock\r\n"
			+ "    WHERE orgid = ?1\r\n"
			+ "      AND branchcode =?2\r\n"
			+ "      AND sourcescreencode = 'AM'\r\n"
			+ "),\r\n"
			+ "\r\n"
			+ "latest_action AS (\r\n"
			+ "    SELECT\r\n"
			+ "        assetstockid,\r\n"
			+ "        assetcode,\r\n"
			+ "        sourcescreencode,\r\n"
			+ "        astatus,\r\n"
			+ "        location,\r\n"
			+ "        locationcode,\r\n"
			+ "        category,\r\n"
			+ "        ROW_NUMBER() OVER (\r\n"
			+ "            PARTITION BY assetcode \r\n"
			+ "            ORDER BY assetstockid DESC\r\n"
			+ "        ) AS rn\r\n"
			+ "    FROM assetstock\r\n"
			+ "    WHERE orgid =?1\r\n"
			+ "      AND branchcode =?2\r\n"
			+ "      AND sourcescreencode IN ('AL','AR')\r\n"
			+ ")\r\n"
			+ "\r\n"
			+ "SELECT\r\n"
			+ "    b.assetcode,\r\n"
			+ "    b.assetname,\r\n"
			+ "    b.serialnumber,\r\n"
			+ "    b.model,\r\n"
			+ "    b.category,  \r\n"
			+ "\r\n"
			+ "    CASE \r\n"
			+ "        WHEN la.sourcescreencode = 'AL' AND la.astatus = 'A'\r\n"
			+ "            THEN 'Assigned'\r\n"
			+ "        ELSE 'Un Assigned'\r\n"
			+ "    END AS allocation_status,\r\n"
			+ "\r\n"
			+ "    COALESCE(la.location, b.base_location) AS location,\r\n"
			+ "    COALESCE(la.locationcode, b.base_locationcode) AS locationcode\r\n"
			+ "\r\n"
			+ "FROM base_assets b\r\n"
			+ "LEFT JOIN latest_action la\r\n"
			+ "    ON b.assetcode = la.assetcode\r\n"
			+ "   AND la.rn = 1")
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

	@Query(nativeQuery = true, value = "WITH base AS (\r\n"
			+ "    SELECT \r\n"
			+ "        assetcode,\r\n"
			+ "        assetname,\r\n"
			+ "        serialnumber,\r\n"
			+ "        category,\r\n"
			+ "        brand,\r\n"
			+ "        model,\r\n"
			+ "        qty\r\n"
			+ "    FROM assetstock\r\n"
			+ "    WHERE orgid = ?1\r\n"
			+ "      AND branchcode = ?2\r\n"
			+ "      AND sourcescreencode = 'AM'\r\n"
			+ "),\r\n"
			+ "\r\n"
			+ "latest_action AS (\r\n"
			+ "    SELECT\r\n"
			+ "        assetcode,\r\n"
			+ "        serialnumber,\r\n"
			+ "        sourcescreencode,\r\n"
			+ "        ROW_NUMBER() OVER (\r\n"
			+ "            PARTITION BY assetcode, serialnumber\r\n"
			+ "            ORDER BY assetstockid DESC\r\n"
			+ "        ) AS rn\r\n"
			+ "    FROM assetstock\r\n"
			+ "    WHERE orgid = ?1\r\n"
			+ "      AND branchcode = ?2\r\n"
			+ "      AND sourcescreencode IN ('AL','AR')\r\n"
			+ ")\r\n"
			+ "\r\n"
			+ "SELECT \r\n"
			+ "    b.assetcode,\r\n"
			+ "    b.assetname,\r\n"
			+ "    b.serialnumber,\r\n"
			+ "    b.category,\r\n"
			+ "    b.brand,\r\n"
			+ "    b.model,\r\n"
			+ "    b.qty\r\n"
			+ "FROM base b\r\n"
			+ "LEFT JOIN latest_action la\r\n"
			+ "       ON la.assetcode = b.assetcode\r\n"
			+ "      AND la.serialnumber = b.serialnumber\r\n"
			+ "      AND la.rn = 1\r\n"
			+ "WHERE \r\n"
			+ "       la.sourcescreencode IS NULL   -- never allocated\r\n"
			+ "    OR la.sourcescreencode = 'AR'   -- latest is returned\r\n"
			+ "")
	List<Object[]> getAssetAllocationDetails(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "select assetcode,assetname,serialnumber,category,brand,model,location,locationcode,sum(qty) from assetstock   where \r\n"
			+ "astatus='A' and assetstatus='A' and orgid=?1 and branchcode=?2 and   \r\n"
			+ " locationcode=?3 group by assetcode,assetname,serialnumber,category,brand,model,location,locationcode having sum(qty)>0")
	List<Object[]> getAssetAllocationListAll(Long orgId, String branchCode, String employeeCode);

}
