package com.bupt.covid.controller;

import com.bupt.covid.pojo.SecretKeyInfo;
import com.bupt.covid.service.SecretKeyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class SecretKeyInfoAPI {

    @Autowired
    SecretKeyInfoService secretKeyInfoService;

    /**
     * 获得14天内 userid 为 0 的记录的每日跟踪秘钥，
     * 以列表形式返回
     * @return
     * @throws Exception
     */
    @GetMapping("/api/Bluetooth/getSecretKeyList")
    public List<HashMap> getSecretKeyList() throws Exception{
        return secretKeyInfoService.getSecretKeyList();
    }

    /**
     * 插入多条记录
     * @param secretKeyInfoList
     * @return
     */
    @PostMapping("/api/Bluetooth/postSecretKeyInfoList")
    public int postSecretKeyInfoList(@RequestBody List<SecretKeyInfo> secretKeyInfoList){
        int result = 1;
        for(SecretKeyInfo secretKeyInfo : secretKeyInfoList){
            result = secretKeyInfoService.insertOneSecretKeyInfo(secretKeyInfo.getUserid(),
                    secretKeyInfo.getSecretkey(), secretKeyInfo.getDate());
        }
        return result;
    }

//    @PostMapping("/api/Bluetooth/postOneSecretKeyInfo")
//    public int postOneSecretKeyInfo(
//            @RequestParam("userid") Integer userid,
//            @RequestParam("secretkey") String secretkey,
//            @RequestParam("date") String date) throws Exception{
//        int result = 1;
//        result = secretKeyInfoService.insertOneSecretKeyInfo(userid, secretkey, date);
//        return result;
//    }
}
