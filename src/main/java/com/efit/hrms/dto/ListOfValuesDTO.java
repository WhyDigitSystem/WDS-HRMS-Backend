package com.efit.hrms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListOfValuesDTO {
	private Long id;
	private String listDescription;
	private Long orgId;
	private String createdBy;

	private List<ListOfValuesDetailsDTO> listOfValuesDetailsDTO;

}