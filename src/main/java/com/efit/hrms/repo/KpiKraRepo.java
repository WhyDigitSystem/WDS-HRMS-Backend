package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.KpiKraVO;

@Repository
public interface KpiKraRepo extends JpaRepository<KpiKraVO, Long> {

	@Query(value = "select * from kpikra where orgid=?1", nativeQuery = true)
	List<KpiKraVO> getKpiKra(Long orgId);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1  and screencode=?2")
	String getKpiDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1  and screencode=?2")
	String getKraDocId(Long orgId, String screenCode);

}
