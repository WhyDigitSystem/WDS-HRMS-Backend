package com.efit.hrms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.AssetImageVO;

@Repository
public interface AssetImageRepo extends JpaRepository<AssetImageVO, Long>{

	@Modifying
	@Query("DELETE FROM AssetImageVO ai WHERE ai.assetMaster.id = :assetMasterId")
	void deleteByAssetMasterId(@Param("assetMasterId") Long assetMasterId);

}
