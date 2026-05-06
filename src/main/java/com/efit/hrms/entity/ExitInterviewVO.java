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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exitinterview")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExitInterviewVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exitinterviewgen")
	@SequenceGenerator(name = "exitinterviewgen", sequenceName = "exitinterviewseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "exitinterviewid")
	private Long id;
	
	@Column(name = "questions")
	private String questions;
	
	@Column(name = "answer")
	private String answer;
	
	@Column(name = "screencode", length = 5)
	private String screenCode = "EI";

	@Column(name = "screenname", length = 25)
	private String screenName = "EXIT INTERVIEW";
	


    @ManyToOne
    @JoinColumn(name = "initiateseparationid", nullable = false)
    @JsonBackReference
    private InitiateSeparationVO initiateSeparationVO;
	
	

}
