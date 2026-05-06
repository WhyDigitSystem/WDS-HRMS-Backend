package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.DepartmentHeadVO;
import com.efit.hrms.entity.ReportingHeadVO;

@Repository
public interface ReportingHeadRepo extends JpaRepository<ReportingHeadVO, Long>{

	List<ReportingHeadVO> findByDepartmentHeadVO(DepartmentHeadVO departmentHeadVO);

}
