package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efit.hrms.entity.EsslDeviceLogVO;

public interface EsslDeviceLogRepo extends JpaRepository<EsslDeviceLogVO, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM essldevicelogdata")
	List<Object[]> getPendingLogDetails();

	EsslDeviceLogVO findByDeviceLogId(Long logId);
	
	
	

}
