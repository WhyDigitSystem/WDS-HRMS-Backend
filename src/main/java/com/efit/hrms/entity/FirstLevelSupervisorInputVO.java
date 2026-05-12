package com.efit.hrms.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "firstlevelsupervisorinput")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirstLevelSupervisorInputVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "firstlevelsupervisorinputgen")
	@SequenceGenerator(name = "firstlevelsupervisorinputgen", sequenceName = "firstlevelsupervisorinputseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "firstlevelsupervisorinputid")
    private Long id;

    @Column(name = "appraisalid")
    private String appraisalId;

    @Column(name = "employeename")
    private String employeeName;

    @Column(name = "employeecode")
    private String employeeCode;

    @Column(name = "department")
    private String department;

    @Column(name = "designation")
    private String designation;

    @Column(name = "supervisorcode")
    private String supervisorCode;

    @Column(name = "supervisorname")
    private String supervisorName;

    @Column(name = "reportingheaddesignation")
    private String reportingHeadDesignation;

    @Column(name = "orgid")
    private Long orgId;

    @Column(name = "branchcode")
    private String branchCode;

    @Column(name = "branch")
    private String branch;

    @Column(name = "finyear")
    private String finyear;

    @Column(name = "createdby")
    private String createdBy;

    @Column(name = "updatedby")
    private String updatedBy;

    @Column(name = "cancel")
    private boolean cancel=false;

    @Column(name = "active")
    private boolean active=true;

    @Column(name = "cancelremarks")
    private String cancelTRemarks;

    @OneToMany(mappedBy = "firstLevelSupervisorInputVO", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<FirstLevelSupervisorInputDetailsVO> firstLevelSupervisorInputDetailsVO;
}
