package com.efit.hrms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.efit.hrms.dto.AtsRequestDTO;
import com.efit.hrms.dto.AtsResponseDTO;
import com.efit.hrms.entity.AtsResultVO;
import com.efit.hrms.entity.JobPostingsVO;
import com.efit.hrms.repo.AtsResultRepo;
import com.efit.hrms.repo.JobPostingsRepo;

@Service
public class AtsServiceImpl implements AtsService {

    @Autowired
    private JobPostingsRepo jobPostingsRepo;

    @Autowired
    private AtsResultRepo atsResultRepo;

    @Autowired
    private RestTemplate restTemplate;

    private static final String PYTHON_ATS_URL =
            "http://127.0.0.1:8000/api/v1/ats/analyze";

    @Override
    @Transactional
    public AtsResponseDTO processAts(
            AtsRequestDTO atsRequestDTO,
            MultipartFile resumeFile
    ) {

        /* 1️⃣ Validate Job */
        JobPostingsVO job = jobPostingsRepo
                .findById(atsRequestDTO.getJobId())
                .orElseThrow(() ->
                        new RuntimeException("Invalid Job ID"));

        if (resumeFile == null || resumeFile.isEmpty()) {
            throw new RuntimeException("Resume file is required");
        }

        /* 2️⃣ Prepare Multipart Request for Python */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ByteArrayResource resumeResource =
                new ByteArrayResource(getBytes(resumeFile)) {
                    @Override
                    public String getFilename() {
                        return resumeFile.getOriginalFilename();
                    }
                };

        LinkedMultiValueMap<String, Object> body =
                new LinkedMultiValueMap<>();

        body.add("job_id", atsRequestDTO.getJobId());
        body.add("candidate_name", atsRequestDTO.getCandidateName());
        body.add("email", atsRequestDTO.getEmail());
        body.add("mobile", atsRequestDTO.getMobile());
        body.add("resume_file", resumeResource);

        HttpEntity<LinkedMultiValueMap<String, Object>> request =
                new HttpEntity<>(body, headers);

        /* 3️⃣ Call Python ATS */
        ResponseEntity<AtsResponseDTO> response =
                restTemplate.postForEntity(
                        PYTHON_ATS_URL,
                        request,
                        AtsResponseDTO.class
                );

        AtsResponseDTO atsResponse = response.getBody();

        if (atsResponse == null || atsResponse.getBreakdown() == null) {
            throw new RuntimeException("Invalid ATS response");
        }

        /* 4️⃣ Save ATS Result */
        AtsResultVO atsResult = new AtsResultVO();

        atsResult.setJobId(atsRequestDTO.getJobId());
        atsResult.setCandidateName(atsRequestDTO.getCandidateName());
        atsResult.setEmail(atsRequestDTO.getEmail());

        atsResult.setOverallScore(atsResponse.getOverall_score());
        atsResult.setSkillsScore(
                atsResponse.getBreakdown().getSkills_score());
        atsResult.setExperienceScore(
                atsResponse.getBreakdown().getExperience_score());
        atsResult.setEducationScore(
                atsResponse.getBreakdown().getEducation_score());

        // Manual UI fields
        atsResult.setBranch(atsRequestDTO.getBranch());
        atsResult.setBranchCode(atsRequestDTO.getBranchCode());
        atsResult.setOrgId(atsRequestDTO.getOrgId());
        atsResult.setCreatedBy(atsRequestDTO.getCreatedBy());

        atsResult.setScreenName("ATS_RESULT");
        atsResult.setScreenCode("ATSR");

        atsResultRepo.save(atsResult);

        /* 5️⃣ Merge UI fields into Response */
        atsResponse.setBranch(atsRequestDTO.getBranch());
        atsResponse.setBranchCode(atsRequestDTO.getBranchCode());
        atsResponse.setOrgId(atsRequestDTO.getOrgId());
        atsResponse.setCreatedBy(atsRequestDTO.getCreatedBy());

        return atsResponse;
    }

    private byte[] getBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read resume file", e);
        }
    }
}
