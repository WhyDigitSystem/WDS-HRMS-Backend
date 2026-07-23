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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "emailconfiguration")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailConfigurationVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emailconfigurationgen")
	@SequenceGenerator(name = "emailconfigurationgen", sequenceName = "emailconfigurationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "emailconfigurationid")
	private Long id;

	@Column(name = "adminemail")
	private String adminEmail;

	@Column(name = "password")
	private String password;
	@Column(name = "smtphost")
	private String smtpHost;

	@Column(name = "smtpport")
	private int smtpPort;
	
	@Column(name = "noreplaymail")
	private String noReplayMail;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
