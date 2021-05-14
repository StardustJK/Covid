package com.bupt.covid.service;


import com.bupt.covid.dao.ManagerInfoDAO;
import com.bupt.covid.pojo.ManagerInfo;
import com.bupt.covid.response.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerInfoService {
    @Autowired
    ManagerInfoDAO managerInfoDAO;


    /**
     * 登录
     *
     * @param userName
     * @param pw
     * @return resultJson.code
     * 0录成功；1用户id错误；2密码错误
     */
    public ResultJson login(String userName, String pw) {
        ResultJson resultJson = new ResultJson();
        resultJson.setData(null);
        List<ManagerInfo> managerInfoList = managerInfoDAO.findAllByUserName(userName);
        if (managerInfoList.size() == 1) {
            ManagerInfo managerInfo = managerInfoList.get(0);

            if (managerInfo.getPassword().equals(pw)) {
                managerInfoDAO.saveAndFlush(managerInfo);
                resultJson.setCode(0);
                resultJson.setData(managerInfo.getRole());
            } else
                resultJson.setCode(2);

        } else
            resultJson.setCode(1);
        return resultJson;
    }

    /**
     * 登出
     *
     * @param userName
     * @param pw
     * @return resultJson.code
     * 0录成功；1用户id错误；2密码错误
     */
    public ResultJson logout(String userName, String pw) {
        ResultJson resultJson = new ResultJson();
        resultJson.setData(null);
        List<ManagerInfo> managerInfoList = managerInfoDAO.findAllByUserName(userName);
        if (managerInfoList.size() == 1) {
            ManagerInfo managerInfo = managerInfoList.get(0);
            if (managerInfo.getPassword().equals(pw)) {
                managerInfoDAO.saveAndFlush(managerInfo);
                resultJson.setCode(0);
            } else
                resultJson.setCode(2);
        } else
            resultJson.setCode(1);
        return resultJson;
    }

    /**
     * 注册账号
     * @param userName
     * @param pw
     * @param role
     * @return
     */
    public ResultJson signIn(String userName, String pw,String role) {
        ResultJson resultJson = new ResultJson();
        resultJson.setCode(1);
        resultJson.setData(null);
        if(managerInfoDAO.findAllByUserName(userName).size() == 0) {
            ManagerInfo managerInfo = new ManagerInfo();
            managerInfo.setUserName(userName);
            managerInfo.setPassword(pw);
            managerInfo.setRole(role);

            managerInfoDAO.saveAndFlush(managerInfo);
            resultJson.setCode(0);
        }

        return  resultJson;
    }
}