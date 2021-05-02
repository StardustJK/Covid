package com.bupt.covid.service;


import com.bupt.covid.dao.SecretKeyInfoDAO;
import com.bupt.covid.pojo.SecretKeyInfo;
import com.bupt.covid.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SecretKeyInfoService {

    @Autowired
    SecretKeyInfoDAO secretKeyInfoDAO;

    /**
     * 获得14天内 userid 为 0 的记录的每日跟踪秘钥，以列表形式返回
     * @return
     */
    public List<HashMap> getSecretKeyList(){

        // 获得14天前的日期的字符串
        String dateString14dAgo = Utility.getDateString(14);

        List<HashMap> secretKeysList = new ArrayList<>();
        List<SecretKeyInfo> secretKeyInfoList =  secretKeyInfoDAO.getByUseridAndDate(0, dateString14dAgo);

        for (SecretKeyInfo secretKeyInfo : secretKeyInfoList){
            HashMap<String, String> myMap = new HashMap<>();
            myMap.put("secretKey", secretKeyInfo.getSecretkey());
            myMap.put("date", secretKeyInfo.getDate());
            secretKeysList.add(myMap);
        }

        return  secretKeysList;
    }


    /**
     * 插入一条记录
     * @param userid
     * @param secretkey
     * @param date
     * @return
     */
    public int insertOneSecretKeyInfo(Integer userid,
                                      String secretkey,
                                      String date){
        SecretKeyInfo secretKeyInfo = new SecretKeyInfo();
        secretKeyInfo.setUserid(userid);
        secretKeyInfo.setDate(date);
        secretKeyInfo.setSecretkey(secretkey);
        secretKeyInfoDAO.saveAndFlush(secretKeyInfo);
        return 0;
    }

    /**
     * 获得14天内的，userid的蓝牙扫描信息列表
     * @param userid
     * @return
     */
    public List<SecretKeyInfo> getBluetoothInfoListByUserid(Integer userid){
        // 获得14天前的日期的字符串
        String dateString14dAgo = Utility.getDateString(14);
        List<SecretKeyInfo> secretKeyInfo = secretKeyInfoDAO.getByUseridAndDate(userid, dateString14dAgo);
        return secretKeyInfo;

    }


}
