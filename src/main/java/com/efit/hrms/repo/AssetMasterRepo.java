package com.efit.hrms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.AssetMasterVO;

@Repository
public interface AssetMasterRepo extends JpaRepository<AssetMasterVO, Long> {

	@Query(nativeQuery = true, value = "select * from assetmaster where assetmasterid=?1")
	AssetMasterVO getAssetMasterById(Long id);

	@Query(nativeQuery = true, value = "select * from assetmaster where orgid=?1 and branchCode=?2")
	List<AssetMasterVO> getAssetMasterByOrgId(Long orgId, String branchCode);

	AssetMasterVO findByAssetNameAndAssetCode(String assetName, String assetCode);
	
	@Query(nativeQuery = true, value = "select * from assetmaster where orgid =?1 and (category=?2 or 'ALL'=?2) and branchcode = ?3 and (?4 is null or STR_TO_DATE(createdon,'%d-%m-%Y %h:%i:%s %p') >=?4)\r\n"
			+ "and (?5 is null or STR_TO_DATE(createdon,'%d-%m-%Y %h:%i:%s %p') <= ?5 )")
	List<AssetMasterVO> getAssetMasterReportDetails(Long orgId, String category, String branchCode, String fromDate, String toDate);

//	@Query(nativeQuery = true, value = "select  AS docid from documenttypemappingdetails where orgid=?1 and  screencode=?2")
//	String getAssetDocId(Long orgId, String screenCode);
//
//	String generateDocId(Long orgId, String screenCode);

}
