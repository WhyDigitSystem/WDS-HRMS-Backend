package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.CompensationDetailsVO;
import com.efit.hrms.entity.CreateOfferVO;

@Repository
public interface CompensationDetailsRepo extends JpaRepository<CompensationDetailsVO, Long>{

	void deleteByCreateOfferVO(CreateOfferVO savedCreateOfferVO);

	@Query(value = "SELECT compensationdetailsid, amount, componenttype, createofferid " +
            "FROM compensationdetails WHERE createofferid = ?1",
    nativeQuery = true)
List<Object[]> getCompensationByOfferId(Long createOfferId);
}
