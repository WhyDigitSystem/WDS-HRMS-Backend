package com.efit.hrms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirstLevelSupervisorInputDTO {

    private Long id;
    private String appraisalId;
    private String employeeName;
    private String employeeCode;
    private String department;
    private String designation;
    private String supervisorCode;
    private String supervisorName;
    private String reportingHeadDesignation;
    private Long orgId;
    private String branchCode;
    private String branch;
    private String finyear;
    private String createdBy;
    private String updatedBy;


    private List<FirstLevelSupervisorInputDetailsDTO> details;
}
