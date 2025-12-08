package com.efit.hrms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.AssetStockVO;

@Repository
public interface AssetStockRepo extends JpaRepository<AssetStockVO, Long> {

}
