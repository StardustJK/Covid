package com.bupt.covid.dao;

import com.bupt.covid.pojo.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatusDao extends JpaRepository<Status,Integer>, JpaSpecificationExecutor<Status> {
    @Query(nativeQuery = true, value = "select * from `tb_status` where `user_id`=? order by `day` ")
    List<Status> getStatusesByUserId(int userId);
}
