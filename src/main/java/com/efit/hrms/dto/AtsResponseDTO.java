package com.efit.hrms.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtsResponseDTO {

    private String candidate_name;
    private String email;
    private Long job_id;
    private int overall_score;

    private Breakdown breakdown;
    private List<String> matched_keywords;
    private List<String> missing_keywords;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Breakdown {
        private int experience_score;
        private int skills_score;
        private int education_score;
    }

    // common fields
    private String branch;
    private String branchCode;
    private Long orgId;
    private String createdBy;
}
