package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.ClearanceDetailsVO;
import com.efit.hrms.entity.DepartmentHeadVO;

@Repository
public interface ClearanceDetailsRepo extends JpaRepository<ClearanceDetailsVO, Long>{

	List<ClearanceDetailsVO> findByDepartmentHeadVO(DepartmentHeadVO departmentHeadVO);


}
