package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.FormVO;

@Repository
public interface FormRepo extends JpaRepository<FormVO, Long> {

	FormVO findByEmployeeCodeAndEmployeeNameAndFileName(String employeeCode, String employeeName, String fileName);

	@Query(nativeQuery = true, value = "select * from form where orgid=?1 and branch=?2 and employeecode=?3  and finyear=?4 order by modifiedon desc limit 1")
	List<FormVO> getFormDetails(Long orgId, String branch, String employeeCode,Long finYear);

}
