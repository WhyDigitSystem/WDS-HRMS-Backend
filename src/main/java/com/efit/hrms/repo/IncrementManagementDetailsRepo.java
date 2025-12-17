package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.IncrementManagementDetailsVO;
import com.efit.hrms.entity.IncrementManagementVO;

@Repository
public interface IncrementManagementDetailsRepo extends JpaRepository<IncrementManagementDetailsVO, Long>{


	List<IncrementManagementDetailsVO> findByIncrementManagementVO(IncrementManagementVO entity);

}
