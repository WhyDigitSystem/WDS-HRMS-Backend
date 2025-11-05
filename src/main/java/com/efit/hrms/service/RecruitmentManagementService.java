package com.efit.hrms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efit.hrms.dto.CandidatesDTO;
import com.efit.hrms.dto.JobPostingsDTO;
import com.efit.hrms.entity.JobPostingsVO;
import com.efit.hrms.exception.ApplicationException;

@Service
public interface RecruitmentManagementService {

	Map<String, Object> createUpdateJobPostings(JobPostingsDTO jobPostingsDTO) throws ApplicationException;

	List<JobPostingsVO> getJobPostingsByOrgId(Long orgId, String branchCode);

	JobPostingsVO getJobPostingsById(Long id);

	Map<String, Object> createUpdateCandidates(CandidatesDTO candidatesDTO) throws ApplicationException;

}
