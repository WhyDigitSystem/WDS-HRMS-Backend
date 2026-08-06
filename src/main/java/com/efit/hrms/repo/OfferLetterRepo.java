package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.OfferLetterVO;

@Repository
public interface OfferLetterRepo extends JpaRepository<OfferLetterVO, Long>{

	@Query(nativeQuery = true, value = "select * from offerletter where orgid=?1 and branchcode=?2 and active=1")
	List<OfferLetterVO> getOfferLetterByOrgId(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "select * from offerletter where offerletterid=?1")
	OfferLetterVO getOfferLetterById(Long id);

}
