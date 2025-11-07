package com.efit.hrms.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efit.hrms.dto.CandidatesDTO;
import com.efit.hrms.dto.CreateOfferDTO;
import com.efit.hrms.dto.JobPostingsDTO;
import com.efit.hrms.dto.OfferLetterDTO;
import com.efit.hrms.entity.CandidatesVO;
import com.efit.hrms.entity.CreateOfferVO;
import com.efit.hrms.entity.JobPostingsVO;
import com.efit.hrms.entity.OfferLetterVO;
import com.efit.hrms.exception.ApplicationException;

@Service
public interface RecruitmentManagementService {

	Map<String, Object> createUpdateJobPostings(JobPostingsDTO jobPostingsDTO) throws ApplicationException;

	List<JobPostingsVO> getJobPostingsByOrgId(Long orgId, String branchCode);

	JobPostingsVO getJobPostingsById(Long id);

	Map<String, Object> createUpdateCandidates(CandidatesDTO candidatesDTO) throws ApplicationException;

	CandidatesVO getCandidatesById(Long id);

	List<CandidatesVO> getCandidatesByOrgId(Long orgId, String branchCode);

	List<CandidatesVO> getSchedulerCandidatesByOrgId(Long orgId, String branchCode);


	Map<String, Object> createUpdateOfferLetter(OfferLetterDTO offerLetterDTO) throws ApplicationException;

	OfferLetterVO getOfferLetterById(Long id);

	List<OfferLetterVO> getOfferLetterByOrgId(Long orgId, String branchCode);

	Map<String, Object> createUpdateCreateOffer(CreateOfferDTO createOfferDTO) throws ApplicationException;

	CreateOfferVO getCreateOfferById(Long id);

	List<CreateOfferVO> getCreateOfferByOrgId(Long orgId, String branchCode);

	List<CreateOfferVO> getCreateOfferByOrgIdAndDepartment(Long orgId, String branchCode, String status,
			String department);

	List<CreateOfferVO> getPendingCreateOfferByOrgId(Long orgId, String branchCode);


//	List<Map<String, Object>> getApprovedCreateOfferByCompany(Long orgId, String branchCode);

	List<Map<String, Object>> getApprovedCreateOfferByCompany(Long orgId, String branchCode, String candidateName);

	Map<String, Object> createApprovalCreateOffer(Long orgId, Long id, String candidateName, String action,
			String actionBy, String notifyCode, String notify, String screenName, String email) throws Exception;

	List<Map<String, Object>> getCreateOfferCountByOrgId(Long orgId, String branchCode);



	
	
}
