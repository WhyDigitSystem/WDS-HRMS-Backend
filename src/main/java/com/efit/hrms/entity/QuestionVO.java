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
@Table(name = "question")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questiongen")
	@SequenceGenerator(name = "questiongen", sequenceName = "questionseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "questionid")
	private Long id;
	@Column(name = "question")
	private String question;
	
  @ManyToOne
    @JoinColumn(name = "exitinterviewdepartmentid", nullable = false)
    @JsonBackReference
    private ExitInterviewDepartmentVO exitInterviewDepartmentVO;
		
}
