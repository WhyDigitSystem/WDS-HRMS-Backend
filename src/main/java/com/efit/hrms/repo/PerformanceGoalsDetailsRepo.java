package com.efit.hrms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.hrms.entity.PerformanceGoalsDetailsVO;
import com.efit.hrms.entity.PerformanceGoalsVO;

@Repository
public interface PerformanceGoalsDetailsRepo extends JpaRepository<PerformanceGoalsDetailsVO, Long> {

    @Query("select a from PerformanceGoalsDetailsVO a where a.id = ?1")
    PerformanceGoalsDetailsVO getDetails(Long id);
    
    List<PerformanceGoalsDetailsVO> findByPerformanceGoalsVO_Id(Long performanceGoalsId); // Optional: to get all child records by header ID
    
    void deleteByPerformanceGoalsVO(PerformanceGoalsDetailsVO performanceGoalsVO);
    
    List<PerformanceGoalsDetailsVO> findByPerformanceGoalsVO(PerformanceGoalsVO performanceGoalsVO);
    
}