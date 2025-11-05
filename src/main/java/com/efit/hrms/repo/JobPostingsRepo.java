package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.JobPostingsVO;

@Repository
public interface JobPostingsRepo extends JpaRepository<JobPostingsVO, Long>{

	@Query(nativeQuery = true, value = "select * from jobpostings where orgid=?1 and branchcode=?2")
	List<JobPostingsVO> getJobPostingsByOrgId(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "select * from jobpostings where jobpostingsid=?1")
	JobPostingsVO getJobPostingsById(Long id);

}
