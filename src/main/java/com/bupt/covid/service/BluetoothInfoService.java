package com.bupt.covid.service;



import com.bupt.covid.dao.BluetoothInfoDAO;
import com.bupt.covid.pojo.BluetoothInfo;
import com.bupt.covid.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BluetoothInfoService {
    @Autowired
    BluetoothInfoDAO bluetoothInfoDAO;


    /**
     * 插入一条记录
     * @param userid
     * @param time_stamp
     * @param my_identifier
     * @param target_identifier
     * @param distance
     * @param duration
     * @param date
     * @return
     */
    public int insertOneBluetoothInfo(Integer userid,Integer time_stamp,
                                      String my_identifier,String target_identifier,
                                      Float distance,Integer duration,String date){
        BluetoothInfo bluetoothInfo = new BluetoothInfo();
        bluetoothInfo.setUserid(userid);
        bluetoothInfo.setTime_stamp(time_stamp);
        bluetoothInfo.setMy_identifier(my_identifier);
        bluetoothInfo.setTarget_identifier(target_identifier);
        bluetoothInfo.setDistance(distance);
        bluetoothInfo.setDuration(duration);
        bluetoothInfo.setDate(date);
        bluetoothInfoDAO.saveAndFlush(bluetoothInfo);
        return 0;
    }

    /**
     * 获得14天内的，userid的蓝牙扫描信息列表
     * @param userid
     * @return
     */
    public List<BluetoothInfo> getBluetoothInfoListByUserid(Integer userid){
        // 获得14天前的日期的字符串
        String dateString14dAgo = Utility.getDateString(14);
        List<BluetoothInfo> bluetoothInfoList = bluetoothInfoDAO.getAllByUseridAndDate(userid, dateString14dAgo);
        return bluetoothInfoList;

    }

}

