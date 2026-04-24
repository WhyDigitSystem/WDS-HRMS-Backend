package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.FirstLevelSupervisorInputDetailsVO;
import com.efit.hrms.entity.FirstLevelSupervisorInputVO;

@Repository
public interface FirstLevelSupervisorInputDetailsRepo extends JpaRepository<FirstLevelSupervisorInputDetailsVO, Long>{

	List<FirstLevelSupervisorInputDetailsVO> findByFirstLevelSupervisorInputVO(FirstLevelSupervisorInputVO vo);

}
