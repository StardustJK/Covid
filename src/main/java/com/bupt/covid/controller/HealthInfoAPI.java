package com.bupt.covid.controller;

import com.bupt.covid.pojo.HealthInfo;
import com.bupt.covid.service.HealthInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HealthInfoAPI {
    @Autowired
    HealthInfoService healthInfoService;

    /**
     * 根据审核状态获得健康信息列表
     * @param healthStatus
     * @return
     */
    @GetMapping("/api/ManagerWeb/findHealthInfoListByStatus")
    public List<HealthInfo> findHealthInfoListByStatus(
            @RequestParam("healthStatus") String healthStatus){
        return healthInfoService.findHealthInfoListByStatus(healthStatus);
    }


    /**
     * 插入一条用户健康信息
     * @param healthInfo
     * @return
     */
    @PostMapping("/api/ManagerWeb/insertOneHealthInfo")
    public int insertOneHealthInfo(@RequestBody HealthInfo healthInfo){
        int result = 0;
        result = healthInfoService.insertOneHealthInfo(
                healthInfo.getUserid(), healthInfo.getType(),
                healthInfo.getContent(), healthInfo.getSubmitTime(),
                healthInfo.getAuditStatus(), healthInfo.getAuditTime(),
                healthInfo.getAuditOpinion());
        return result;
    }

    /**
     * 更新一条数据
     * @param healthInfo
     * @return
     */
    @PostMapping("/api/ManagerWeb/updateOneHealthInfo")
    public int updateOneHealthInfo(@RequestBody HealthInfo healthInfo){
        int result = 0;
        result = healthInfoService.updateOneHealthInfo(healthInfo);
        return result;
    }

//    @PostMapping("/api/Bluetooth/updateOneHealthInfo")
//    public int updateOneHealthInfo(@RequestBody HealthInfo healthInfo){
//        int result = 0;
//        result = healthInfoService.updateOneHealthInfo(healthInfo.getId(),
//                healthInfo.getUserid(),  healthInfo.getAction(),
//                healthInfo.getContent(), healthInfo.getSubmitTime(),
//                healthInfo.getAuditStatus(), healthInfo.getAuditTime());
//        return result;
//    }
}
