package com.efit.hrms.entity;


import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.hrms.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "atsresult")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtsResultVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "atsresultgen")
    @SequenceGenerator(
        name = "atsresultgen",
        sequenceName = "atsresultseq",
        initialValue = 1000000001,
        allocationSize = 1
    )
    @Column(name = "atsresultid")
    private Long id;

    @Column(name = "jobid")
    private Long jobId;

    @Column(name = "candidatename")
    private String candidateName;

    @Column(name = "email")
    private String email;

    @Column(name = "overallscore")
    private Integer overallScore;

    @Column(name = "skillsscore")
    private Integer skillsScore;

    @Column(name = "experiencescore")
    private Integer experienceScore;

    @Column(name = "educationscore")
    private Integer educationScore;

    // Common fields
    
    @Column(name = "branch")
    private String branch;

    @Column(name = "branchcode")
    private String branchCode;

    @Column(name = "createdby")
    private String createdBy;

    @Column(name = "modifiedby")
    private String updatedBy;

    @Column(name = "orgid")
    private Long orgId;


    @Column(name = "screenname")
    private String screenName = "ATS_RESULT";

    @Column(name = "screencode")
    private String screenCode = "ATSR";

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

   
}