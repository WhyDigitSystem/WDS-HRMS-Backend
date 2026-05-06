package com.efit.hrms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.ListOfValuesVO;

@Repository
public interface ListOfValuesRepo extends JpaRepository<ListOfValuesVO, Long> {

	@Query(nativeQuery = true, value = "select * from listofvalues where orgid=?1 ")
	List<ListOfValuesVO> getAllListOfValuesByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from listofvalues where listofvaluesid=?1")
	ListOfValuesVO getAllListOfValuesById(Long id);

	boolean existsByListDescriptionAndOrgId(String listDescription, Long orgId);
	
	@Query(nativeQuery = true, value = "select a1.listvalues from  listofvalues a, listofvaluesdetails a1 where a.listofvaluesid=a1.listofvaluesid and a.orgid=?1 and a.listdescription=?2  and  a1.active=1")
	Set<Object[]> getAllListValues(Long orgId,String listDescription);
}