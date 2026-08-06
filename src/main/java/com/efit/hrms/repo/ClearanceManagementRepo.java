package com.efit.hrms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.ClearanceManagementVO;
import com.efit.hrms.entity.InitiateSeparationVO;

@Repository
public interface ClearanceManagementRepo extends JpaRepository<ClearanceManagementVO, Long>{

	void deleteByInitiateSeparationVO(InitiateSeparationVO vo);

}
