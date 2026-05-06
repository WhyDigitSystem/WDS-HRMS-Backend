package com.efit.hrms.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.hrms.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "compensationdetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompensationDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compensationdetailsgen")
	@SequenceGenerator(name = "compensationdetailsgen", sequenceName = "compensationdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "compensationdetailsid")
	private Long id;

	@Column(name = "componenttype")
	private String componentType;
	@Column(name = "amount")
	private BigDecimal amount;

	@ManyToOne
	@JoinColumn(name="createofferid")
	@JsonBackReference
	private CreateOfferVO createOfferVO;




	

}

