package com.efit.hrms.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efit.hrms.dto.CandidatesDTO;
import com.efit.hrms.dto.CompensationDetailsDTO;
import com.efit.hrms.dto.CreateOfferDTO;
import com.efit.hrms.dto.JobPostingsDTO;
import com.efit.hrms.dto.OfferLetterDTO;
import com.efit.hrms.entity.CandidatesVO;
import com.efit.hrms.entity.CompensationDetailsVO;
import com.efit.hrms.entity.CreateOfferVO;
import com.efit.hrms.entity.JobPostingsVO;
import com.efit.hrms.entity.OfferLetterVO;
import com.efit.hrms.exception.ApplicationException;
import com.efit.hrms.repo.CandidatesRepo;
import com.efit.hrms.repo.CompensationDetailsRepo;
import com.efit.hrms.repo.CreateOfferRepo;
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

	@Autowired
	CreateOfferRepo createOfferRepo;
	
	@Autowired
	CompensationDetailsRepo compensationDetailsRepo;
	

	
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
	@Transactional
	public Map<String, Object> createUpdateCandidates(CandidatesDTO candidatesDTO) throws ApplicationException {

	    CandidatesVO candidatesVO;
	    String message;

	    if (ObjectUtils.isNotEmpty(candidatesDTO.getId())) {
	        // Update existing record
	        candidatesVO = candidatesRepo.findById(candidatesDTO.getId())
	                .orElseThrow(() -> new ApplicationException("Invalid Candidates details"));
	        candidatesVO.setUpdatedBy(candidatesDTO.getCreatedBy());
	        message = "Candidates Updated Successfully";
	    } else {
	        // Create new record
	        candidatesVO = new CandidatesVO();
	        candidatesVO.setCreatedBy(candidatesDTO.getCreatedBy());
	        candidatesVO.setUpdatedBy(candidatesDTO.getCreatedBy());
	        message = "Candidates Created Successfully";
	    }

	    // Map DTO → Entity
	    createUpdateCandidatesVOFromCandidatesDTO(candidatesVO, candidatesDTO);

	    // Save parent to get generated ID
	    CandidatesVO savedCandidatesVO = candidatesRepo.save(candidatesVO);

	    // ✅ Set candidateId = id only when creating new record
	    if (candidatesDTO.getId() == null && savedCandidatesVO.getCandidateId() == null) {
	        savedCandidatesVO.setCandidateId(savedCandidatesVO.getId());
	        candidatesRepo.save(savedCandidatesVO);
	    }

	    // Prepare response
	    Map<String, Object> response = new HashMap<>();
	    response.put("candidatesVO", savedCandidatesVO);
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
	    candidatesVO.setInterviewStatus(candidatesDTO.getInterviewStatus());
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


	
	 
	 
	@Override
	@Transactional
	public Map<String, Object> createUpdateCreateOffer(CreateOfferDTO createOfferDTO)
	        throws ApplicationException {

	    Map<String, Object> response = new LinkedHashMap<>();

	    CreateOfferVO createOfferVO;

	    // ✅ 1. Create or Update Offer
	    if (createOfferDTO.getId() != null) {
	        createOfferVO = createOfferRepo.findById(createOfferDTO.getId())
	                .orElseThrow(() -> new ApplicationException(
	                        "Error: createOffer ID " + createOfferDTO.getId() + " not found!"));
	        createOfferVO.setUpdatedBy(createOfferDTO.getCreatedBy());
	        response.put("message", "createOffer Updated Successfully");
	    } else {
	        createOfferVO = new CreateOfferVO();
	        createOfferVO.setCreatedBy(createOfferDTO.getCreatedBy());
	        createOfferVO.setUpdatedBy(createOfferDTO.getCreatedBy());
	        createOfferVO.setApproveStatus("PENDING");
	        response.put("message", "createOffer Created Successfully");
	    }

	    // ✅ 2. Set Fields from DTO
	    createOfferVO.setCandidateName(createOfferDTO.getCandidateName());
	    createOfferVO.setCandidateId(createOfferDTO.getCandidateId());
	    createOfferVO.setPosition(createOfferDTO.getPosition());
	    createOfferVO.setJoiningDate(createOfferDTO.getJoiningDate());
	    createOfferVO.setReportingPerson(createOfferDTO.getReportingPerson());
	    createOfferVO.setReportingcode(createOfferDTO.getReportingcode());
	    createOfferVO.setReportingEmail(createOfferDTO.getReportingEmail());
	    createOfferVO.setWorkLocation(createOfferDTO.getWorkLocation());
	    createOfferVO.setProbationPeriod(createOfferDTO.getProbationPeriod());
	    createOfferVO.setNoticePeriod(createOfferDTO.getNoticePeriod());
	    createOfferVO.setWorkhours(createOfferDTO.getWorkhours());
	    createOfferVO.setTemplateType(createOfferDTO.getTemplateType());
	    createOfferVO.setAdditionalBenefits(createOfferDTO.getAdditionalBenefits());
	    createOfferVO.setSpecialTermsCondition(createOfferDTO.getSpecialTermsCondition());
	    createOfferVO.setOrgId(createOfferDTO.getOrgId());
	    createOfferVO.setBranchCode(createOfferDTO.getBranchCode());
	    createOfferVO.setBranch(createOfferDTO.getBranch());
	    createOfferVO.setDepartment(createOfferDTO.getDepartment());
	    createOfferVO.setFinYear(createOfferDTO.getFinYear());

	    // ✅ 3. Save parent first to get generated ID
	    CreateOfferVO savedCreateOfferVO = createOfferRepo.save(createOfferVO);

	    // ✅ 4. Remove existing child records if updating
	    if (createOfferDTO.getId() != null) {
	        compensationDetailsRepo.deleteByCreateOfferVO(savedCreateOfferVO);
	    }

	    // ✅ 5. Validate duplicate components
	    Set<String> earningHeadings = new HashSet<>();
	    for (CompensationDetailsDTO dto : createOfferDTO.getCompensationDetailsDTO()) {
	        if (!earningHeadings.add(dto.getComponentType().toUpperCase())) {
	            throw new ApplicationException(
	                    "Duplicate heading found in CompensationDetails: " + dto.getComponentType());
	        }
	    }

	    // ✅ 6. Save Child Table (CompensationDetails)
	    List<CompensationDetailsVO> compensationDetailsVOs = createOfferDTO.getCompensationDetailsDTO().stream()
	            .filter(dto -> dto.getAmount() != null && dto.getAmount().compareTo(BigDecimal.ZERO) > 0)
	            .map(dto -> {
	                CompensationDetailsVO vo = new CompensationDetailsVO();
	                vo.setComponentType(dto.getComponentType());
	                vo.setAmount(dto.getAmount());
	                vo.setCreateOfferVO(savedCreateOfferVO);
	                return vo;
	            })
	            .collect(Collectors.toList());

	    compensationDetailsRepo.saveAll(compensationDetailsVOs);

	    // ✅ 7. Attach Child Records
	    savedCreateOfferVO.setCompensationDetailsVO(compensationDetailsVOs);

	    // ✅ 8. Final Response
	    Map<String, Object> paramObjectsMap = new LinkedHashMap<>();
	    paramObjectsMap.put("message", response.get("message"));
	    paramObjectsMap.put("createOfferVO", savedCreateOfferVO);
	    response.put("paramObjectsMap", paramObjectsMap);

	    return response;
	}

	 @Override
		public CreateOfferVO getCreateOfferById(Long id) {
			return createOfferRepo.getCreateOfferById(id);
		}
		
	 
	 @Override
		public List<CreateOfferVO> getCreateOfferByOrgId(Long orgId,String branchCode) {
			// TODO Auto-generated method stub
			return createOfferRepo.getCreateOfferByOrgId(orgId,branchCode);
		}
	 
	 @Override
		public List<CreateOfferVO> getCreateOfferByOrgIdAndDepartment(Long orgId, String branchCode, String status,
				String department) {
			// TODO Auto-generated method stub
			return createOfferRepo.getCreateOfferByOrgIdAndDepartment(orgId,branchCode,status,department);
			
		}
	 
//	 @Override
//	    public List<Map<String, Object>> getApprovedCreateOfferByCompany(Long orgId, String branchCode) {
//	        List<Object[]> results = createOfferRepo.getApprovedCreateOfferByCompany(orgId, branchCode);
//	        List<Map<String, Object>> list = new ArrayList<>();
//
//	        for (Object[] row : results) {
//	            Map<String, Object> map = new LinkedHashMap<>();
//
//	            // --- createoffer fields ---
//	            map.put("createofferid", row[0] != null ? row[0] : "");
//	            map.put("active", row[1] != null ? row[1] : "");
//	            map.put("additionalbenefits", row[2] != null ? row[2] : "");
//	            map.put("branch", row[3] != null ? row[3] : "");
//	            map.put("branchcode", row[4] != null ? row[4] : "");
//	            map.put("candidatename", row[5] != null ? row[5] : "");
//	            map.put("createdon", row[6] != null ? row[6] : "");
//	            map.put("modifiedon", row[7] != null ? row[7] : "");
//	            map.put("createdby", row[8] != null ? row[8] : "");
//	            map.put("department", row[9] != null ? row[9] : "");
//	            map.put("finyear", row[10] != null ? row[10] : "");
//	            map.put("joiningdate", row[11] != null ? row[11] : "");
//	            map.put("noticeperiod", row[12] != null ? row[12] : "");
//	            map.put("orgid", row[13] != null ? row[13] : "");
//	            map.put("position", row[14] != null ? row[14] : "");
//	            map.put("probationperiod", row[15] != null ? row[15] : "");
//	            map.put("reportingemail", row[16] != null ? row[16] : "");
//	            map.put("reportingperson", row[17] != null ? row[17] : "");
//	            map.put("reportingcode", row[18] != null ? row[18] : "");
//	            map.put("screencode", row[19] != null ? row[19] : "");
//	            map.put("screenname", row[20] != null ? row[20] : "");
//	            map.put("specialtermscondition", row[21] != null ? row[21] : "");
//	            map.put("templatetype", row[22] != null ? row[22] : "");
//	            map.put("modifiedby", row[23] != null ? row[23] : "");
//	            map.put("worklocation", row[24] != null ? row[24] : "");
//	            map.put("workhours", row[25] != null ? row[25] : "");
//	            map.put("approveby", row[26] != null ? row[26] : "");
//	            map.put("approveon", row[27] != null ? row[27] : "");
//	            map.put("approvestatus", row[28] != null ? row[28] : "");
//
//	            // --- company fields (start from column 29 onwards) ---
//	            map.put("address", row[29] != null ? row[29] : "");
//	            map.put("companycode", row[30] != null ? row[30] : "");
//	            map.put("companyname", row[31] != null ? row[31] : "");
//	            map.put("companylogo", row[32] != null ? row[32] : "");
//
//	            list.add(map);
//	        }
//
//	        return list;
//	    }
	 
	 
	 @Override
	 public List<Map<String, Object>> getApprovedCreateOfferByCompany(Long orgId, String branchCode, String candidateName) {

	     List<Object[]> results = createOfferRepo.getApprovedCreateOfferByCompany(orgId, branchCode, candidateName);
	     List<Map<String, Object>> finalList = new ArrayList<>();

	     for (Object[] row : results) {
	         Map<String, Object> offerMap = new LinkedHashMap<>();

	         // ---- CreateOffer fields ----
	         offerMap.put("createofferid", row[0] != null ? row[0] : "");
	         offerMap.put("active", row[1] != null ? row[1] : "");
	         offerMap.put("additionalbenefits", row[2] != null ? row[2] : "");
	         offerMap.put("branch", row[3] != null ? row[3] : "");
	         offerMap.put("branchcode", row[4] != null ? row[4] : "");
	         offerMap.put("candidatename", row[5] != null ? row[5] : "");
	         offerMap.put("createdon", row[6] != null ? row[6] : "");
	         offerMap.put("modifiedon", row[7] != null ? row[7] : "");
	         offerMap.put("createdby", row[8] != null ? row[8] : "");
	         offerMap.put("department", row[9] != null ? row[9] : "");
	         offerMap.put("finyear", row[10] != null ? row[10] : "");
	         offerMap.put("joiningdate", row[11] != null ? row[11] : "");
	         offerMap.put("noticeperiod", row[12] != null ? row[12] : "");
	         offerMap.put("orgid", row[13] != null ? row[13] : "");
	         offerMap.put("position", row[14] != null ? row[14] : "");
	         offerMap.put("probationperiod", row[15] != null ? row[15] : "");
	         offerMap.put("reportingemail", row[16] != null ? row[16] : "");
	         offerMap.put("reportingperson", row[17] != null ? row[17] : "");
	         offerMap.put("reportingcode", row[18] != null ? row[18] : "");
	         offerMap.put("screencode", row[19] != null ? row[19] : "");
	         offerMap.put("screenname", row[20] != null ? row[20] : "");
	         offerMap.put("specialtermscondition", row[21] != null ? row[21] : "");
	         offerMap.put("templatetype", row[22] != null ? row[22] : "");
	         offerMap.put("modifiedby", row[23] != null ? row[23] : "");
	         offerMap.put("worklocation", row[24] != null ? row[24] : "");
	         offerMap.put("workhours", row[25] != null ? row[25] : "");
	         offerMap.put("approveby", row[26] != null ? row[26] : "");
	         offerMap.put("approveon", row[27] != null ? row[27] : "");
	         offerMap.put("approvestatus", row[28] != null ? row[28] : "");
	         offerMap.put("candidateId", row[29] != null ? row[29] : "");

	         // ---- Company fields ----
	         Map<String, Object> companyMap = new LinkedHashMap<>();
	         companyMap.put("address", row[30] != null ? row[30] : "");
	         companyMap.put("companycode", row[31] != null ? row[31] : "");
	         companyMap.put("companyname", row[32] != null ? row[32] : "");
	         companyMap.put("companylogo", row[33] != null ? row[33] : "");
	         offerMap.put("companyDetails", companyMap);

	         // ---- Child table: compensation details ----
	         Long createOfferId = ((Number) row[0]).longValue();
	         List<Object[]> childRows = compensationDetailsRepo.getCompensationByOfferId(createOfferId);
	         List<Map<String, Object>> compensationList = new ArrayList<>();

	         for (Object[] cd : childRows) {
	             Map<String, Object> comp = new LinkedHashMap<>();
	             comp.put("compensationdetailsid", cd[0] != null ? cd[0] : "");
	             comp.put("amount", cd[1] != null ? cd[1] : "");
	             comp.put("componenttype", cd[2] != null ? cd[2] : "");
	             comp.put("createofferid", cd[3] != null ? cd[3] : "");
	             compensationList.add(comp);
	         }

	         offerMap.put("compensationDetails", compensationList); // ✅ Array of child rows
	         finalList.add(offerMap);
	     }

	     return finalList;
	 }

	 
	 @Override
		public List<CreateOfferVO> getPendingCreateOfferByOrgId(Long orgId, String branchCode) {
			// TODO Auto-generated method stub
			return createOfferRepo.getPendingCreateOfferByOrgId(orgId,branchCode);
		}
	
	 
	 @Override
	    public Map<String, Object> createApprovalCreateOffer(
	            Long orgId,
	            Long id,
	            String candidateName,
	            String action,
	            String actionBy,
	            String notifyCode,
	            String notify,
	            String screenName,
	            String email) throws Exception {

	        // Response map
	        Map<String, Object> response = new HashMap<>();
	        String message = "";

	        // 1️⃣ Fetch increment management record
	        CreateOfferVO createOfferVO =
	        		createOfferRepo.findByOrgIdAndIdAndCandidateName(orgId, id, candidateName);

	        if (createOfferVO == null) {
	            throw new ApplicationException("Candidate record not found for the given details.");
	        }

	        // 2️⃣ Check if already approved or rejected
	        String currentStatus = createOfferVO.getApproveStatus();
	        if (currentStatus != null &&
	            (currentStatus.equalsIgnoreCase("Approved") || currentStatus.equalsIgnoreCase("Rejected"))) {
	            throw new ApplicationException(
	                    "This TravelRequests is already " + currentStatus + ".");
	        }

	        // 3️⃣ Proceed only if action is valid
	        if ("APPROVED".equalsIgnoreCase(action)) {
	            message = "Approved Successfully";

	        } else if ("REJECTED".equalsIgnoreCase(action)) {
	            // Just mark as rejected
	            message = "Rejected Successfully";

	        } else {
	            throw new ApplicationException("Invalid action: must be APPROVED or REJECTED.");
	        }

	        // 4️⃣ Update approval details
	        createOfferVO.setApproveStatus(action);
	        createOfferVO.setApproveBy(actionBy);

	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
	        createOfferVO.setApproveOn(LocalDateTime.now().format(formatter).toUpperCase());

	        createOfferRepo.save(createOfferVO);

	        // 5️⃣ Prepare response
	        response.put("createOfferVO", createOfferVO);
	        response.put("message", message);
	        return response;
	    }

	

	
	@Override
    public List<Map<String, Object>> getCreateOfferCountByOrgId(Long orgId, String branchCode) {
        List<Object[]> results = createOfferRepo.getCreateOfferCountByOrgId(orgId, branchCode);
        List<Map<String, Object>> list = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("pendingCount", row[0]);
            map.put("approvedCount", row[1]);
            map.put("rejectedCount", row[2]);
            map.put("totalCount", row[3]);

            list.add(map);
        }

        return list;
    }
	
	 
	 

}
