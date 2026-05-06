package com.efit.hrms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.efit.hrms.dto.AtsRequestDTO;
import com.efit.hrms.dto.AtsResponseDTO;
import com.efit.hrms.dto.ResponseDTO;
import com.efit.hrms.service.AtsService;

@RestController
@RequestMapping("/api/ats")
public class AtsController {

    @Autowired
    private AtsService atsService;

    @PutMapping(
        value = "/analyze",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDTO> analyzeAts(
            @RequestPart("atsRequestDTO") @Valid AtsRequestDTO atsRequestDTO,
            @RequestPart("resumefile") MultipartFile resumeFile
    ) {

        Map<String, Object> responseMap = new HashMap<>();
        ResponseDTO responseDTO;

        try {
            AtsResponseDTO atsResponse =
                    atsService.processAts(atsRequestDTO, resumeFile);

            responseMap.put("atsResult", atsResponse);
            responseMap.put("message", "ATS Resume analyzed successfully");

            responseDTO = successResponse(responseMap);
            return ResponseEntity.ok(responseDTO);

        } catch (Exception e) {
            responseDTO = errorResponse(
                    responseMap,
                    "ATS Analysis Failed",
                    e.getMessage()
            );
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(responseDTO);
        }
    }

    /* ---------- Common Response Helpers ---------- */

    private ResponseDTO successResponse(Map<String, Object> map) {
        ResponseDTO dto = new ResponseDTO();
        dto.setStatus(true);
        dto.setStatusFlag("Success");
        dto.setParamObjectsMap(map);
        return dto;
    }

    private ResponseDTO errorResponse(
            Map<String, Object> map,
            String message,
            String error
    ) {
        ResponseDTO dto = new ResponseDTO();
        dto.setStatus(false);
        dto.setStatusFlag("Error");
        map.put("message", message);
        map.put("errorMessage", error);
        dto.setParamObjectsMap(map);
        return dto;
    }
}
