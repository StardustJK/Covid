package com.bupt.covid.service;

import com.bupt.covid.dao.HealthInfoDAO;
import com.bupt.covid.pojo.HealthInfo;
import com.bupt.covid.pojo.User;
import com.bupt.covid.service.impl.UserServiceImpl;
import com.bupt.covid.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HealthInfoService {
    @Autowired
    HealthInfoDAO healthInfoDAO;

    @Autowired
    UserServiceImpl userService;

    /**
     * 根据审核状态获得健康信息列表
     * @param auditStatus
     * @return
     */
    public List<HealthInfo> findHealthInfoListByStatus(String auditStatus){
        List<HealthInfo> healthInfoList = new ArrayList<>();
        if(auditStatus.equals("待审核"))
            healthInfoList = healthInfoDAO.findAllByAuditStatus("待审核");
        else if(auditStatus.equals("已审核"))
            healthInfoList = healthInfoDAO.findAllByAuditStatusIsNot("待审核");
        return healthInfoList;
    }

    /**
     * 插入一条记录
     * @param userid
     * @param type
     * @param content
     * @param submitTime
     * @param auditStatus
     * @param auditTime
     * @return
     */
    public int insertOneHealthInfo(Integer userid, String type,
                                   String content, String submitTime,
                                   String auditStatus, String auditTime,
                                   String auditOpinion){
        HealthInfo healthInfo = new HealthInfo();
        healthInfo.setUserid(userid);
        healthInfo.setType(type);
        healthInfo.setContent(content);
        healthInfo.setSubmitTime(submitTime);
//        healthInfo.setAuditStatus(auditStatus);
//        healthInfo.setAuditOpinion(auditOpinion);
//        healthInfo.setAuditTime(auditTime);
        healthInfoDAO.saveAndFlush(healthInfo);
        return 0;
    }

    /**
     * 更新一条记录
     * @param healthInfo
     * @return
     */
    public int updateOneHealthInfo(HealthInfo healthInfo){
        String auditTimeString = Utility.getDateTimeString();
        healthInfo.setAuditTime(auditTimeString);

        if(healthInfo.getAuditStatus().equals("通过")){
         if(healthInfo.getType().equals("确诊信息")){
             User userInfo = userService.findUserInfoByUserid(healthInfo.getUserid());
             userInfo.setStatus(1);
             userInfo.setWifiInfectionRate(1);
             userInfo.setBluetoothInfectionRate(1);
             userInfo.setWifiRiskLevel(10);
             userInfo.setBluetoothRiskLevel(10);
             userService.updateOneUserInfo(userInfo);
         }
         if(healthInfo.getType().equals("康复信息")){
             User userInfo = userService.findUserInfoByUserid(healthInfo.getUserid());
             userInfo.setStatus(3);
             userInfo.setWifiInfectionRate(0);
             userInfo.setBluetoothInfectionRate(0);
             userInfo.setWifiRiskLevel(0);
             userInfo.setBluetoothRiskLevel(0);
             userService.updateOneUserInfo(userInfo);
         }
        }
        healthInfoDAO.saveAndFlush(healthInfo);
        //应插入一条用户健康状态记录

        return 0;
    }
//
//    /**
//     * 更新一条记录
//     * @param id
//     * @param userid
//     * @param action
//     * @param content
//     * @param submitTime
//     * @param auditStatus
//     * @param auditTime
//     * @return
//     */
//    public int updateOneHealthInfo(Integer id, Integer userid, String action,
//                                   String content, String submitTime,
//                                   String auditStatus, String auditTime){
//        List<HealthInfo> healthInfoList = healthInfoDAO.findAllById(id);
//        HealthInfo healthInfo_ = healthInfoList.get(0);
//        healthInfo_.setUserid(userid);
//        healthInfo_.setAction(action);
//        healthInfo_.setContent(content);
//        healthInfo_.setSubmitTime(submitTime);
//        healthInfo_.setAuditStatus(auditStatus);
//        healthInfo_.setAuditTime(auditTime);
//        healthInfoDAO.saveAndFlush(healthInfo_);
//
//        return 0;
//    }

}
