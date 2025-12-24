package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.CandidatesVO;

@Repository
public interface CandidatesRepo extends JpaRepository<CandidatesVO, Long> {

	@Query(nativeQuery = true, value = "select * from candidates where candidatesid=?1  ")
	CandidatesVO getCandidatesById(Long id);

	@Query(nativeQuery = true, value = "SELECT *\r\n" + "FROM candidates c\r\n" + "WHERE c.orgid = ?1\r\n"
			+ "  AND c.branchcode = ?2\r\n" + "  AND c.active = 1\r\n" + "  AND c.interviewstatus = 'Selected'\r\n"
			+ "  AND NOT EXISTS (\r\n" + "        SELECT 1\r\n" + "        FROM createoffer o\r\n"
			+ "        WHERE o.candidateid = c.candidatesid\r\n" + "  )")
	List<CandidatesVO> getSelectedCandidates(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "SELECT * FROM candidates c\r\n" + "			WHERE c.orgid =?1\r\n"
			+ "			  AND c.branchcode =?2\r\n" + "			  AND c.active = 1")
	List<CandidatesVO> getCandidatesByOrgId(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "select * from candidates where orgid=?1 and branchcode=?2 and active=1 AND interviewdate >= CURRENT_DATE ")
	List<CandidatesVO> getSchedulerCandidatesByOrgId(Long orgId, String branchCode);

}
