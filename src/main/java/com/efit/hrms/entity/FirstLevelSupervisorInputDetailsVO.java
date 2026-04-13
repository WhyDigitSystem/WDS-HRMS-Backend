package com.efit.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "firstlevelsupinputdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirstLevelSupervisorInputDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "firstlevelsupinputdetailsgen")
	@SequenceGenerator(name = "firstlevelsupinputdetailsgen", sequenceName = "firstlevelsupinputdetailsseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "firstlevelsinputdetailsid")
    private Long id;

    @Column(name = "goals")
    private String goals;

    @Column(name = "selfinput")
    private String selfInput;

    @Column(name = "score")
    private int score;

    @Column(name = "supervisorrating")
    private String supervisorRating;

    @ManyToOne
    @JoinColumn(name = "firstlevelsupervisorinputid")
    @JsonBackReference
    private FirstLevelSupervisorInputVO firstLevelSupervisorInputVO;
}
