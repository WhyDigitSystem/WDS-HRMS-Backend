package com.efit.hrms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efit.hrms.dto.CandidatesDTO;
import com.efit.hrms.dto.JobPostingsDTO;
import com.efit.hrms.dto.OfferLetterDTO;
import com.efit.hrms.entity.CandidatesVO;
import com.efit.hrms.entity.JobPostingsVO;
import com.efit.hrms.entity.OfferLetterVO;
import com.efit.hrms.exception.ApplicationException;
import com.efit.hrms.repo.CandidatesRepo;
import com.efit.hrms.repo.JobPostingsRepo;
import com.efit.hrms.repo.OfferLetterRepo;

@Service
public class RecruitmentManagementServiceImpl implements RecruitmentManagementService {

	public static final Logger LOGGER = LoggerFactory.getLogger(RecruitmentManagementServiceImpl.class);

	@Autowired
	JobPostingsRepo jobPostingsRepo;
	
	@Autowired
	CandidatesRepo candidatesRepo;
	
	@Autowired
	OfferLetterRepo offerLetterRepo;

	@Override
	public Map<String, Object> createUpdateJobPostings(JobPostingsDTO jobPostingsDTO) throws ApplicationException {

		JobPostingsVO jobPostingsVO = new JobPostingsVO();
		String message;

		if (ObjectUtils.isNotEmpty(jobPostingsDTO.getId())) {
			jobPostingsVO = jobPostingsRepo.findById(jobPostingsDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid JobPostings details"));

			jobPostingsVO.setUpdatedBy(jobPostingsDTO.getCreatedBy());

			message = "JobPostings Updated Successfully";
		} else {

			jobPostingsVO.setCreatedBy(jobPostingsDTO.getCreatedBy());
			jobPostingsVO.setUpdatedBy(jobPostingsDTO.getCreatedBy());
			message = "ExpenseClaims Created Successfully";
		}

		createUpdateJobPostingsVOByJobPostingsDTO(jobPostingsVO, jobPostingsDTO);
		jobPostingsRepo.save(jobPostingsVO);
		Map<String, Object> response = new HashMap<>();
		response.put("jobPostingsVO", jobPostingsVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateJobPostingsVOByJobPostingsDTO(JobPostingsVO jobPostingsVO, JobPostingsDTO jobPostingsDTO) {

	    jobPostingsVO.setJobTitle(jobPostingsDTO.getJobTitle());
	    jobPostingsVO.setDepartment(jobPostingsDTO.getDepartment());
	    jobPostingsVO.setLocation(jobPostingsDTO.getLocation());
	    jobPostingsVO.setOrgId(jobPostingsDTO.getOrgId());
	    jobPostingsVO.setBranch(jobPostingsDTO.getBranch());
	    jobPostingsVO.setBranchCode(jobPostingsDTO.getBranchCode());
	    jobPostingsVO.setActive(jobPostingsDTO.isActive());

	}

	

	@Override
	public List<JobPostingsVO> getJobPostingsByOrgId(Long orgId, String branchCode) {
		// TODO Auto-generated method stub
		return jobPostingsRepo.getJobPostingsByOrgId(orgId,branchCode);
	}
	
	@Override
	public JobPostingsVO getJobPostingsById(Long id) {
		return jobPostingsRepo.getJobPostingsById(id);
	}
	
	

	@Override
	public Map<String, Object> createUpdateCandidates(CandidatesDTO candidatesDTO) throws ApplicationException {

		CandidatesVO candidatesVO = new CandidatesVO();
		String message;

		if (ObjectUtils.isNotEmpty(candidatesDTO.getId())) {
			candidatesVO = candidatesRepo.findById(candidatesDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Candidates details"));

			candidatesVO.setUpdatedBy(candidatesDTO.getCreatedBy());

			message = "Candidates Updated Successfully";
		} else {

			candidatesVO.setCreatedBy(candidatesDTO.getCreatedBy());
			candidatesVO.setUpdatedBy(candidatesDTO.getCreatedBy());
			message = "Candidates Created Successfully";
		}

		createUpdateCandidatesVOFromCandidatesDTO(candidatesVO, candidatesDTO);
		candidatesRepo.save(candidatesVO);
		Map<String, Object> response = new HashMap<>();
		response.put("candidatesVO", candidatesVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateCandidatesVOFromCandidatesDTO(CandidatesVO candidatesVO, CandidatesDTO candidatesDTO) {

	    candidatesVO.setCandidatesName(candidatesDTO.getCandidatesName());
	    candidatesVO.setEmail(candidatesDTO.getEmail());
	    candidatesVO.setPositionApplied(candidatesDTO.getPositionApplied());
	    candidatesVO.setResumeScore(candidatesDTO.getResumeScore());
	    candidatesVO.setInterviewDate(candidatesDTO.getInterviewDate());
	    candidatesVO.setInterviewTime(candidatesDTO.getInterviewTime());
	    candidatesVO.setInterviewer(candidatesDTO.getInterviewer());
	    candidatesVO.setRating(candidatesDTO.getRating());
	    candidatesVO.setFeedBack(candidatesDTO.getFeedBack());
	    candidatesVO.setOrgId(candidatesDTO.getOrgId());
	    candidatesVO.setBranchCode(candidatesDTO.getBranchCode());
	    candidatesVO.setBranch(candidatesDTO.getBranch());
	    candidatesVO.setActive(candidatesDTO.isActive());

	}
	
	@Override
	public List<CandidatesVO> getCandidatesByOrgId(Long orgId, String branchCode) {
		// TODO Auto-generated method stub
		return candidatesRepo.getCandidatesByOrgId(orgId,branchCode);
	}
	
	@Override
	public CandidatesVO getCandidatesById(Long id) {
		return candidatesRepo.getCandidatesById(id);
	}
	
	@Override
	public List<CandidatesVO> getSchedulerCandidatesByOrgId(Long orgId, String branchCode) {
		// TODO Auto-generated method stub
		return candidatesRepo.getSchedulerCandidatesByOrgId(orgId,branchCode);
	}
	

	
	@Override
	public Map<String, Object> createUpdateOfferLetter(OfferLetterDTO offerLeterDTO) throws ApplicationException {

		OfferLetterVO offerLetterVO = new OfferLetterVO();
		String message;

		if (ObjectUtils.isNotEmpty(offerLeterDTO.getId())) {
			offerLetterVO = offerLetterRepo.findById(offerLeterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid JobPostings details"));

			offerLetterVO.setUpdatedBy(offerLeterDTO.getCreatedBy());

			message = "OfferLetter Updated Successfully";
		} else {

			offerLetterVO.setCreatedBy(offerLeterDTO.getCreatedBy());
			offerLetterVO.setUpdatedBy(offerLeterDTO.getCreatedBy());
			message = "OfferLeter Created Successfully";
		}

		createUpdateofferLetterVOFromOfferLeterDTO(offerLetterVO, offerLeterDTO);
		offerLetterRepo.save(offerLetterVO);
		Map<String, Object> response = new HashMap<>();
		response.put("offerLetterVO", offerLetterVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateofferLetterVOFromOfferLeterDTO(OfferLetterVO offerLetterVO, OfferLetterDTO offerLeterDTO) {

	    offerLetterVO.setCandidatesName(offerLeterDTO.getCandidatesName());
	    offerLetterVO.setEmail(offerLeterDTO.getEmail());
	    offerLetterVO.setPosition(offerLeterDTO.getPosition());
	    offerLetterVO.setDepartment(offerLeterDTO.getDepartment());
	    offerLetterVO.setLocation(offerLeterDTO.getLocation());
	    offerLetterVO.setRemarks(offerLeterDTO.getRemarks());
	    offerLetterVO.setActive(offerLeterDTO.isActive());
	    offerLetterVO.setOrgId(offerLeterDTO.getOrgId());
	    offerLetterVO.setBranchCode(offerLeterDTO.getBranchCode());
	    offerLetterVO.setBranch(offerLeterDTO.getBranch());
	}

	@Override
	public List<OfferLetterVO> getOfferLetterByOrgId(Long orgId, String branchCode) {
		// TODO Auto-generated method stub
		return offerLetterRepo.getOfferLetterByOrgId(orgId,branchCode);
	}
	
	@Override
	public OfferLetterVO getOfferLetterById(Long id) {
		return offerLetterRepo.getOfferLetterById(id);
	}


	

}
