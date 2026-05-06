package com.efit.hrms.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtsRequestDTO {

	private Long jobId;
	
    private String candidateName;
    
    @Email
    private String email;
    
    @Pattern(regexp="^\\+?[0-9]{10,15}$", message="Invalid mobile number")
    private String mobile;

    
    //common fields
    
    private String branch;
    
    private String branchCode;
    
    private Long orgId;
    
    private String createdBy;
    
   
}
