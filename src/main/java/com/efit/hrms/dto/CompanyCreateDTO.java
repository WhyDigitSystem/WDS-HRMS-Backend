package com.efit.hrms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyCreateDTO {

	private Long id;
    private String companyCode;
    private String companyName;
    private String email;
    private String employeeName;
    private String employeeCode;
    private String password;
    private String createdBy;

    private String phone;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zip;

    private boolean active;
}
