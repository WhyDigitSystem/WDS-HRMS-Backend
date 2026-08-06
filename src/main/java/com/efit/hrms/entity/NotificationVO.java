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
@Table(name = "notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notificationgen")
	@SequenceGenerator(name = "notificationgen", sequenceName = "notificationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "notificationid")
	private Long id;


	@Column(name = "userid")
    private Long userid; 
	@Column(name = "auctionsid")
    private Long auctionsid;        // Auction reference
	@Column(name = "message")
    private String message;
	@Column(name = "isread")
    private boolean isRead = false;
	@Column(name = "isdeleted")
    private boolean isDeleted = false;
	@Column(name = "notificationtype")
    private String notificationType;
	
	@Column(name = "createdby")
	private String createdBy;
	
	@Column(name = "modifiedy")
	private String updatedBy;
	
	
	@Column(name = "orgid")
	private Long orgId;

	
	
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}

