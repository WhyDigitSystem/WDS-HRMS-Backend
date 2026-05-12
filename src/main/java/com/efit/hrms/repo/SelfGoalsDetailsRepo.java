package com.efit.hrms.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.SelfGoalsDetailsVO;
import com.efit.hrms.entity.SelfGoalsVO;

@Repository
public interface SelfGoalsDetailsRepo extends JpaRepository<SelfGoalsDetailsVO, Long>{

	List<SelfGoalsDetailsVO> findBySelfGoalsVO(SelfGoalsVO selfGoalsVO);

	@Modifying
	@Transactional
	@Query(value = "UPDATE selfgoalsdetails " +
	               "SET status = ?2 " +
	               "WHERE selfgoalsdetailsid IN (?1)", 
	       nativeQuery = true)
	int updateStatusBulk(List<Long> detailIds, String status);
}
