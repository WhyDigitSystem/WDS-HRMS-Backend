package com.efit.hrms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.GoalsVO;

@Repository
public interface GoalsRepo extends JpaRepository<GoalsVO, Long> {

	@Query(nativeQuery = true, value = "select * from goals where orgid=?1")
	List<GoalsVO> getGoals(Long orgId);

//	@Query(nativeQuery =true,value= "select * from goals where orgid=?1")
//	String getAppraisalDocId(Long orgId);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(lastno ,5,0)) AS docid from documenttypemappingdetails where orgid=?1  and screencode=?2")
	String getGoalsDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select appraisalid,designation from goals where orgid=?1 and active=1  and cancel=0")
	Set<Object[]> getAppraisalDocId(Long orgId);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(lastno +1,5,0)) AS docid from documenttypemappingdetails where orgid=?1  and screencode=?2")
	String getLatestGoalsDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select * from goals where orgid=?1 and designation=?2 and appraisalid=?3")
	List<GoalsVO> getGoalsByOrgIdByDesignation(Long orgId, String designation,String appraisalid);

}
