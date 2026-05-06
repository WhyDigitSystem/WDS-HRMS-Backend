package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.AssetReturnVO;

@Repository
public interface AssetReturnRepo extends JpaRepository<AssetReturnVO, Long> {

	@Query(nativeQuery = true, value = "select * from assetreturn where assetreturnid=?1")
	AssetReturnVO getAssetReturnById(Long id);

	@Query(nativeQuery = true, value = "select * from assetreturn where orgid=?1 and branchCode=?2")
	List<AssetReturnVO> getAssetReturnByOrgId(Long orgId, String branchCode);

}
