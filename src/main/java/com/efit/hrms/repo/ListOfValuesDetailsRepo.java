package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.ListOfValuesDetailsVO;
import com.efit.hrms.entity.ListOfValuesVO;

@Repository
public interface ListOfValuesDetailsRepo extends JpaRepository<ListOfValuesDetailsVO, Long> {

	List<ListOfValuesDetailsVO> findByListOfValuesVO(ListOfValuesVO listOfValuesVO);


}