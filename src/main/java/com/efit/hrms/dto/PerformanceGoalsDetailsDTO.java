package com.efit.hrms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceGoalsDetailsDTO {

    private String perspective;
    private String objectivedesc;
    private String perassigned;
    private String measurement;
    private String qtrtarget;
    private String performance;
    private String comments;
    private String performanceself;
    private Long selfrating;
    private Long appraiserrating;
    private String apprjustification;

}
