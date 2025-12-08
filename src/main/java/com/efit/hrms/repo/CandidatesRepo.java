package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.CandidatesVO;

@Repository
public interface CandidatesRepo extends JpaRepository<CandidatesVO, Long>{

	@Query(nativeQuery = true, value = "select * from candidates where candidatesid=?1  ")
	CandidatesVO getCandidatesById(Long id);

	@Query(nativeQuery = true, value = "select * from candidates where orgid=?1 and branchcode=?2 and active=1")
	List<CandidatesVO> getCandidatesByOrgId(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "select * from candidates where orgid=?1 and branchcode=?2 and active=1 AND interviewdate >= CURRENT_DATE")
	List<CandidatesVO> getSchedulerCandidatesByOrgId(Long orgId, String branchCode);

}
