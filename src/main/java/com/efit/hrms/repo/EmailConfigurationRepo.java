package com.efit.hrms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.EmailConfigurationVO;

@Repository
public interface EmailConfigurationRepo extends JpaRepository<EmailConfigurationVO, Long> {

	@Query(value = "SELECT * FROM emailconfiguration LIMIT 1", nativeQuery = true)
	EmailConfigurationVO findByType();

}
