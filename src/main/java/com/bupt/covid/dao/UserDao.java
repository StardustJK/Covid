package com.bupt.covid.dao;

import com.bupt.covid.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDao extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {
    User findAllById(int userId);
    User findOneByPhone(String phone);
    User findOneByName(String name);
    User findOneById(int id);

    /**
     * 获得用户信息列表，
     * 不包括 uerid= id
     * 按照userid降序排列
     * @param id
     * @return
     */
    List<User> findAllByIdIsNotOrderById(int id);

    /**
     * 获得用户信息列表，
     * 按照蓝牙感染风险等级降序排列
     * @return
     */
    @Query(value = "select * from covid.tb_user order by bluetooth_risk_level desc ",nativeQuery = true)
    List<User> findAllOrderByBluetoothRiskLevelDesc();

    /**
     * 获得蓝牙感染风险等级高于riskLevel的用户信息列表
     * @param riskLevel
     * @return
     */
//    @Query(value = "select * from tb_user where bluetooth_trisk_level > ?1 order by bluetooth_risk_level desc ",nativeQuery = true)
    List<User> findAllByBluetoothRiskLevelIsGreaterThanOrderByBluetoothRiskLevel(int riskLevel);

}
