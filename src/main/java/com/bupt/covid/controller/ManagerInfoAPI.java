package com.bupt.covid.controller;

import com.bupt.covid.pojo.User;
import com.bupt.covid.response.ResultJson;
import com.bupt.covid.service.ManagerInfoService;
import com.bupt.covid.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ManagerInfoAPI {
    @Autowired
    ManagerInfoService managerInfoService;

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/api/ManagerWeb/login")
    public ResultJson login(@RequestParam("userName") String userName,
                            @RequestParam("password") String password){
        return managerInfoService.login(userName, password);
    }

    @PostMapping("/api/ManagerWeb/logout")
    public ResultJson logout(@RequestParam("userName") String userName,
                             @RequestParam("password") String password){
        return managerInfoService.logout(userName, password);
    }


    /**
     * 获得userid不为0的用户信息列表，按照userid降序排列
     * @return
     */
    @GetMapping("/api/ManagerWeb/getUserInfoListNot0")
    public List<User> getUserInfoListNot0(){
        return userService.getUserInfoListNot0();
    }



}
