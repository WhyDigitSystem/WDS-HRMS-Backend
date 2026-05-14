package com.efit.hrms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.InvestmentDeclarationVO;

@Repository
public interface InvestmentDeclarationRepo extends JpaRepository<InvestmentDeclarationVO, Long> {

	@Query(nativeQuery = true, value = "select * from investmentdeclaration where orgid=?1 and branchcode=?2 and employeecode=?3")
	List<InvestmentDeclarationVO> getInvestmentDeclarationDetails(Long orgId, String branchCode, String employeeCode);

	@Query(nativeQuery = true, value = "select * from investmentdeclaration where investmentdeclarationid=?1")
	InvestmentDeclarationVO getInvestmentDeclarationById(Long id);

	@Query(nativeQuery = true, value = "select sum(grossincome) as grossincome,sum(totaldedcutions) as totaldedcutions,sum(grossincome) - sum(totaldedcutions) as taxableincome,\r\n"
			+ "    round(( case\r\n" + "                when (sum(grossincome) - sum(totaldedcutions)) <= 400000 \r\n"
			+ "                then 0\r\n"
			+ "                when (sum(grossincome) - sum(totaldedcutions)) <= 800000 \r\n"
			+ "                then (\r\n"
			+ "                    ((sum(grossincome) - sum(totaldedcutions)) - 400000) * 5 / 100)\r\n"
			+ "                when (sum(grossincome) - sum(totaldedcutions)) <= 1200000 \r\n"
			+ "                then (400000 * 5 / 100 + ((sum(grossincome) - sum(totaldedcutions)) - 800000) * 10 / 100)\r\n"
			+ "                when (sum(grossincome) - sum(totaldedcutions)) <= 1600000 \r\n"
			+ "                then (400000 * 5 / 100 + 400000 * 10 / 100 + ((sum(grossincome) - sum(totaldedcutions)) - 1200000) * 15 / 100)\r\n"
			+ "                when (sum(grossincome) - sum(totaldedcutions)) <= 2000000 \r\n"
			+ "                then (400000 * 5 / 100 + 400000 * 10 / 100 + 400000 * 15 / 100 + ((sum(grossincome) - sum(totaldedcutions)) - 1600000) * 20 / 100)\r\n"
			+ "                when (sum(grossincome) - sum(totaldedcutions)) <= 2400000 \r\n"
			+ "                then (400000 * 5 / 100 + 400000 * 10 / 100 + 400000 * 15 / 100 + 400000 * 20 / 100 +\r\n"
			+ "                    ((sum(grossincome) - sum(totaldedcutions)) - 2000000) * 25 / 100)\r\n"
			+ "                else ( 400000 * 5 / 100 + 400000 * 10 / 100 + 400000 * 15 / 100 + 400000 * 20 / 100 + 400000 * 25 / 100 +\r\n"
			+ "                    ((sum(grossincome) - sum(totaldedcutions)) - 2400000) * 30 / 100) end\r\n"
			+ "            + (case\r\n" + "				when (sum(grossincome) - sum(totaldedcutions)) <= 400000 \r\n"
			+ "				then 0\r\n"
			+ "                    when (sum(grossincome) - sum(totaldedcutions)) <= 800000 \r\n"
			+ "                    then (\r\n"
			+ "                        ((sum(grossincome) - sum(totaldedcutions)) - 400000) * 5 / 100)\r\n"
			+ "                    when (sum(grossincome) - sum(totaldedcutions)) <= 1200000 \r\n"
			+ "                    then (400000 * 5 / 100 + ((sum(grossincome) - sum(totaldedcutions)) - 800000) * 10 / 100)\r\n"
			+ "                    when (sum(grossincome) - sum(totaldedcutions)) <= 1600000 \r\n"
			+ "                    then (400000 * 5 / 100 + 400000 * 10 / 100 + ((sum(grossincome) - sum(totaldedcutions)) - 1200000) * 15 / 100)\r\n"
			+ "                    when (sum(grossincome) - sum(totaldedcutions)) <= 2000000 \r\n"
			+ "                    then (400000 * 5 / 100 + 400000 * 10 / 100 + 400000 * 15 / 100 + ((sum(grossincome) - sum(totaldedcutions)) - 1600000) * 20 / 100)\r\n"
			+ "                    when (sum(grossincome) - sum(totaldedcutions)) <= 2400000 \r\n"
			+ "                    then (400000 * 5 / 100 + 400000 * 10 / 100 + 400000 * 15 / 100 + 400000 * 20 / 100 +\r\n"
			+ "                        ((sum(grossincome) - sum(totaldedcutions)) - 2000000) * 25 / 100)\r\n"
			+ "                    else (400000 * 5 / 100 + 400000 * 10 / 100 + 400000 * 15 / 100 + 400000 * 20 / 100 + 400000 * 25 / 100 +\r\n"
			+ "                        ((sum(grossincome) - sum(totaldedcutions)) - 2400000) * 30 / 100)\r\n"
			+ "                end\r\n" + "            ) * 4 / 100\r\n" + "        ) ,\r\n" + "    2) AS monthlytds\r\n"
			+ " from (\r\n"
			+ " select sum(amount) * 12 as grossincome ,0 as  totaldedcutions,0 as taxableincome,0 as monthlytds  from salarystructure\r\n"
			+ " where orgid=?1 and branch=?2 and employeecode=?3\r\n" + " union \r\n"
			+ " select 0 as grossincome ,sum(a1.declared) as  totaldedcutions,0 as taxableincome,0 as monthlytds  from investmentdeclaration a join\r\n"
			+ " investmentdeclarationdetails a1 on a.investmentdeclarationid=a1.investmentdeclarationid\r\n"
			+ " where a.orgid=?1 and a.branch=?2 and a.employeecode=?3\r\n" + " ) a")
	Set<Object[]> getDashBoardDetailsNew(Long orgId, String branch, String employeeCode);

	InvestmentDeclarationVO findByOrgIdAndIdAndEmployeeCode(Long orgId, Long id, String employeeCode);

}
