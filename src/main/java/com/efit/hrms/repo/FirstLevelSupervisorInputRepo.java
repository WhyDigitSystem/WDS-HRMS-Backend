package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.FirstLevelSupervisorInputVO;

@Repository
public interface FirstLevelSupervisorInputRepo extends JpaRepository<FirstLevelSupervisorInputVO,Long>{

	@Query(nativeQuery = true,value = "select * from firstlevelsupervisorinput where orgid=?1 ")
	List<FirstLevelSupervisorInputVO> getFirstLevelSupervisorInputByOrgId(Long orgId);

}
