package com.bupt.covid.dao;

import com.bupt.covid.pojo.BluetoothInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BluetoothInfoDAO extends JpaRepository<BluetoothInfo, Integer> {

    /**
     * 获得从 date到现在的 userid的蓝牙扫描信息
     * @param userid
     * @param date
     * @return
     */
    @Query(value = "select * from bluetooth_info where userid =?1 and (date >= ?2)",nativeQuery = true)
    List<BluetoothInfo> getAllByUseridAndDate(Integer userid, String date);
}
