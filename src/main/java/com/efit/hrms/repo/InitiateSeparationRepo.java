package com.efit.hrms.repo;

import java.util.List;

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
			+ "		      AND active = 1 ")
	List<InitiateSeparationVO> getInitiateSeparationByDepartment(Long orgId, String branchCode,String department, String type);

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
	List<Object[]> getInitiateSeparationCountByOrgId(Long orgId, String branchCode);

}
