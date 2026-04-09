package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.ExitInterviewDepartmentVO;
import com.efit.hrms.entity.QuestionVO;

@Repository
public interface QuestionRepo extends JpaRepository<QuestionVO, Long>{

	List<QuestionVO> findByExitInterviewDepartmentVO(ExitInterviewDepartmentVO exitInterviewDepartmentVO);

}
