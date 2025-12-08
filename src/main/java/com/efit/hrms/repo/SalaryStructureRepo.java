package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.SalaryStructureVO;

@Repository
public interface SalaryStructureRepo extends JpaRepository<SalaryStructureVO, Long>{

	@Query(nativeQuery = true,value = "select * from salarystructure a where a.orgid=?1 ")
	List<SalaryStructureVO> getAllSalaryStructureByOrgId(Long orgId);

	
	@Query(nativeQuery = true,value = "select * from salarystructure a where a.salarystructureid=?1 ")
	SalaryStructureVO getSalaryStructureById(Long id);



	@Query(nativeQuery = true,value =
		    " SELECT *\r\n"
		    + "    FROM salarystructure s\r\n"
		    + "    WHERE s.orgid = ?1\r\n"
		    + "      AND s.employeecode = ?2\r\n"
		    + "      AND STR_TO_DATE(s.createdon, '%d-%m-%Y %h:%i:%s %p') = (\r\n"
		    + "          SELECT MAX(STR_TO_DATE(s2.createdon, '%d-%m-%Y %h:%i:%s %p'))\r\n"
		    + "          FROM salarystructure s2\r\n"
		    + "          WHERE s2.orgid = s.orgid\r\n"
		    + "            AND s2.employeecode = s.employeecode" +
		    "  )"
		)
		SalaryStructureVO findByOrgIdAndEmployeeCode(
		        Long orgId,
		        String employeeCode);







}
