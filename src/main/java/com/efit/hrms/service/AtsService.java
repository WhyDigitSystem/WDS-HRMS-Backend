package com.efit.hrms.service;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.efit.hrms.dto.AtsRequestDTO;
import com.efit.hrms.dto.AtsResponseDTO;

public interface AtsService {


	AtsResponseDTO processAts(@Valid AtsRequestDTO atsRequestDTO, MultipartFile resumeFile);
}
