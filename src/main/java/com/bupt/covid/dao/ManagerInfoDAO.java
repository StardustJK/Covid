package com.bupt.covid.dao;


import com.bupt.covid.pojo.ManagerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerInfoDAO extends JpaRepository<ManagerInfo, Integer> {

    List<ManagerInfo> findAllByUserName(String userName);

}
