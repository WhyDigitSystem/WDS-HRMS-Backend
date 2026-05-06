package com.efit.hrms.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efit.hrms.entity.AtsResultVO;

public interface AtsResultRepo extends JpaRepository<AtsResultVO, Long> {

	Optional<AtsResultVO> findByJobIdAndEmail(Long jobId, String email);

}
