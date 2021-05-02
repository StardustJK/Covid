package com.bupt.covid.dao;

import com.bupt.covid.pojo.HealthInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HealthInfoDAO extends JpaRepository<HealthInfo, Integer> {

    List<HealthInfo> findAllById(Integer id);
    List<HealthInfo> findAllByAuditStatus(String auditStatus);

//    @Query(value = "select * from user_info order by risk_level desc ",nativeQuery = true)
    List<HealthInfo> findAllByAuditStatusIsNot(String auditStatus);
}
