package com.efit.hrms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.AssetImageVO;

@Repository
public interface AssetImageRepo extends JpaRepository<AssetImageVO, Long>{

}
