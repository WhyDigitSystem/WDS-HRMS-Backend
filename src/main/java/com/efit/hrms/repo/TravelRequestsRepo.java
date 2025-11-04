package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.TravelRequestsVO;

@Repository
public interface TravelRequestsRepo extends JpaRepository<TravelRequestsVO, Long>{

	@Query(nativeQuery = true, value = "select * from travelrequests where orgid=?1 and branchcode=?2")
	List<TravelRequestsVO> getTravelRequestsByOrgId(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "select * from travelrequests where travelrequestsid=?1 ")
	TravelRequestsVO getTravelRequestsById(Long id);

}
