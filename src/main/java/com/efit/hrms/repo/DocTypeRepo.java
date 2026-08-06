package com.efit.hrms.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.DocTypeVO;

@Repository
public interface DocTypeRepo extends JpaRepository<DocTypeVO, Long> {

	boolean existsByScreenCode(String screenCode);

	boolean existsByDocCode(String docCode);

	boolean existsByScreenName(String screenName);

	Optional<DocTypeVO> findByScreenCode(String screenCode);


}
