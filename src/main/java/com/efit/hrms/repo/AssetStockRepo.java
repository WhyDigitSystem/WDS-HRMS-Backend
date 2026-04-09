package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.AssetStockVO;

@Repository
public interface AssetStockRepo extends JpaRepository<AssetStockVO, Long> {

	@Query(nativeQuery = true, value = "SELECT \r\n"
			+ "    locationcode AS emp_code,\r\n"
			+ "    location AS emp_name,\r\n"
			+ "    SUM(qty) AS balance_qty,\r\n"
			+ "    \r\n"
			+ "    CASE \r\n"
			+ "        WHEN SUM(qty) = 0 THEN 'COMPLETED'\r\n"
			+ "        WHEN SUM(qty) > 0 THEN 'PENDING'\r\n"
			+ "    END AS status\r\n"
			+ "FROM assetstock\r\n"
			+ "WHERE locationcode = ?1 and orgid=?2 and branchCode=?3 \r\n"
			+ "GROUP BY locationcode, location")
	List<Object[]> getStatusForClearance(String employeeCode, Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "SELECT DISTINCT\r\n"
			+ "    aa.employeecode,\r\n"
			+ "    aa.employeename,\r\n"
			+ "    aa.assetname\r\n"
			+ "FROM assetallocation aa\r\n"
			+ "JOIN departmenthead dh \r\n"
			+ "    ON dh.branchcode = aa.branchcode\r\n"
			+ "    AND dh.orgid = aa.orgid\r\n"
			+ "JOIN clearancedetails cd \r\n"
			+ "    ON cd.departmentheadid = dh.departmentheadid\r\n"
			+ "    AND LOWER(cd.clearancename) = LOWER(aa.assetname)\r\n"
			+ "WHERE aa.employeecode =?1 and aa.branchcode=?3 and aa.orgid=?2 and dh.department =?4 ")
	List<Object[]> getAssetAllocationDetailsForClearance(String employeeCode, Long orgId, String branchCode,
			String department);

	@Query(nativeQuery = true, value = "  SELECT DISTINCT\r\n"
			+ "    ar.employeecode,\r\n"
			+ "    ar.employeename,\r\n"
			+ "    ar.assetname\r\n"
			+ "FROM assetreturn ar\r\n"
			+ "JOIN departmenthead dh \r\n"
			+ "    ON dh.branchcode = ar.branchcode\r\n"
			+ "    AND dh.orgid = ar.orgid\r\n"
			+ "\r\n"
			+ "JOIN clearancedetails cd \r\n"
			+ "    ON cd.departmentheadid = dh.departmentheadid\r\n"
			+ "    AND LOWER(cd.clearancename) = LOWER(ar.assetname)\r\n"
			+ "\r\n"
			+ "WHERE  ar.employeecode = ?1 and ar.orgid=?2 and ar.branchcode=?3 and dh.department =?4 ")
	List<Object[]> getAssetReturnForClearance(String employeeCode, Long orgId, String branchCode,
			String department);


}
