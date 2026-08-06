package com.efit.hrms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.efit.hrms.dto.BranchDTO;
import com.efit.hrms.entity.BranchVO;
import com.efit.hrms.exception.ApplicationException;
import com.efit.hrms.repo.BranchRepo;

@ExtendWith(MockitoExtension.class)
public class BranchServiceTest {
	
	@InjectMocks
    private MasterServiceImpl masterService;
	
	@Mock
	BranchRepo branchRepo;
	
	
	private BranchDTO getBranchDTO() {
	    BranchDTO dto = new BranchDTO();
	    dto.setBranch("Chennai");
	    dto.setBranchCode("CHN");
	    dto.setOrgId(1L);
	    dto.setCreatedBy("admin");
	    dto.setActive(true);
	    dto.setState("Tamil Nadu");
	    dto.setCity("Chennai");
	    dto.setCountry("India");
	    return dto;
	}

	private BranchVO getBranchVO() {
	    BranchVO vo = new BranchVO();
	    vo.setId(1L);
	    vo.setBranch("CHENNAI");
	    vo.setBranchCode("CHN");
	    vo.setOrgId(1L);
	    return vo;
	}
	
	@Test
	void createBranch_success() throws Exception {

	    BranchDTO dto = getBranchDTO();

	    when(branchRepo.existsByBranchAndOrgId(dto.getBranch(), dto.getOrgId()))
	            .thenReturn(false);

	    when(branchRepo.existsByBranchCodeAndOrgId(dto.getBranchCode(), dto.getOrgId()))
	            .thenReturn(false);

	    when(branchRepo.save(any(BranchVO.class)))
	            .thenAnswer(invocation -> invocation.getArgument(0));

	    Map<String, Object> response = masterService.createUpdateBranch(dto);

	    assertNotNull(response);
	    assertEquals("Branch Created Successfully", response.get("message"));

	    BranchVO savedVO = (BranchVO) response.get("branchVO");
	    assertEquals("CHENNAI", savedVO.getBranch());
	    assertEquals("CHN", savedVO.getBranchCode());

	    verify(branchRepo).save(any(BranchVO.class));
	}
	
	@Test
	void createBranch_duplicateBranch_shouldThrowException() {

	    BranchDTO dto = getBranchDTO();

	    when(branchRepo.existsByBranchAndOrgId(dto.getBranch(), dto.getOrgId()))
	            .thenReturn(true);

	    ApplicationException ex = assertThrows(
	            ApplicationException.class,
	            () -> masterService.createUpdateBranch(dto)
	    );

	    assertTrue(ex.getMessage().contains("Already Exists"));

	    verify(branchRepo, never()).save(any());
	}

	@Test
	void createBranch_duplicateBranchCode_shouldThrowException() {

	    BranchDTO dto = getBranchDTO();

	    when(branchRepo.existsByBranchAndOrgId(dto.getBranch(), dto.getOrgId()))
	            .thenReturn(false);

	    when(branchRepo.existsByBranchCodeAndOrgId(dto.getBranchCode(), dto.getOrgId()))
	            .thenReturn(true);

	    ApplicationException ex = assertThrows(
	            ApplicationException.class,
	            () -> masterService.createUpdateBranch(dto)
	    );

	    assertTrue(ex.getMessage().contains("BranchCode"));

	    verify(branchRepo, never()).save(any());
	}
	
	@Test
	void updateBranch_success() throws Exception {

	    BranchDTO dto = getBranchDTO();
	    dto.setId(1L);
	    dto.setBranch("Bangalore");
	    dto.setBranchCode("BLR");

	    BranchVO existingVO = getBranchVO();

	    when(branchRepo.findById(dto.getId()))
	            .thenReturn(Optional.of(existingVO));

	    when(branchRepo.existsByBranchAndOrgId("Bangalore", dto.getOrgId()))
	            .thenReturn(false);

	    when(branchRepo.existsByBranchCodeAndOrgId("BLR", dto.getOrgId()))
	            .thenReturn(false);

	    when(branchRepo.save(any(BranchVO.class)))
	            .thenAnswer(invocation -> invocation.getArgument(0));

	    Map<String, Object> response = masterService.createUpdateBranch(dto);

	    assertEquals("Branch Updated Successfully", response.get("message"));

	    BranchVO updatedVO = (BranchVO) response.get("branchVO");
	    assertEquals("BANGALORE", updatedVO.getBranch());
	    assertEquals("BLR", updatedVO.getBranchCode());
	}


	@Test
	void updateBranch_notFound_shouldThrowException() {

	    BranchDTO dto = getBranchDTO();
	    dto.setId(99L);

	    when(branchRepo.findById(99L))
	            .thenReturn(Optional.empty());

	    ApplicationException ex = assertThrows(
	            ApplicationException.class,
	            () -> masterService.createUpdateBranch(dto)
	    );

	    assertTrue(ex.getMessage().contains("Branch not found"));
	}



}
