package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.AssetAllocationVO;

@Repository
public interface AssetAllocationRepo extends JpaRepository<AssetAllocationVO, Long>{

	@Query(nativeQuery = true, value = "select assetname,assetcode from assetMaster where orgid=?1 and branchcode=?2")
	List<Object[]> getAssetNameCodeByOrgId(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "select * from assetallocation where orgid=?1 and branchcode=?2")
	List<AssetAllocationVO> getAssetAllocationByOrgId(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "select * from assetallocation where assetallocationid=?1")
	AssetAllocationVO getAssetAllocationById(Long id);

	@Query(nativeQuery = true, value = "SELECT \r\n"
			+ "    totalasset,\r\n"
			+ "    allocatedasset,\r\n"
			+ "    (totalasset - allocatedasset) AS availableasset\r\n"
			+ "FROM (\r\n"
			+ "    SELECT \r\n"
			+ "        (SELECT COUNT(*) \r\n"
			+ "         FROM assetmaster \r\n"
			+ "         WHERE orgid = ?1 AND branchcode = ?2 and active=1) AS totalasset,\r\n"
			+ "        (SELECT COUNT(*) \r\n"
			+ "         FROM assetallocation \r\n"
			+ "         WHERE orgid = ?1 AND branchcode = ?2 and active=1) AS allocatedasset\r\n"
			+ ") AS asset_summary")
	List<Object[]> getAssetCountByOrgId(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "SELECT \r\n"
			+ "    a.assetname,\r\n"
			+ "       a.category,\r\n"
			+ "       CASE \r\n"
			+ "        WHEN b.assetcode IS NOT NULL THEN 'ASSIGNED'\r\n"
			+ "        ELSE 'AVAILABLE'\r\n"
			+ "    END AS status,\r\n"
			+ "      b.employeename,\r\n"
			+ "    a.location\r\n"
			+ "  \r\n"
			+ " \r\n"
			+ "FROM assetmaster a\r\n"
			+ "LEFT JOIN assetallocation b \r\n"
			+ "    ON a.assetname = b.assetname \r\n"
			+ "    AND a.assetcode = b.assetcode\r\n"
			+ "    AND b.orgid = a.orgid \r\n"
			+ "    AND b.branchcode = a.branchcode\r\n"
			+ "WHERE a.orgid = ?1\r\n"
			+ "  AND a.branchcode = ?2")
	List<Object[]> getAssetDashboardByOrgId(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "SELECT employeename,assetname,assetcode,assetcondition,allocationdate,expectedreturndate FROM assetallocation WHERE employeecode = ?3\r\n"
			+ "  AND orgid = ?1 AND branchcode = ?2 ORDER BY allocationdate DESC")
	List<Object[]> getAssetAllocationReportByOrgId(Long orgId, String branchCode, String employeeCode);

}
