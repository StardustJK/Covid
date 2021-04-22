package com.bupt.covid.service;

import com.bupt.covid.pojo.User;
import com.bupt.covid.response.ResponseResult;

public interface IUserService {
    //TODO 注册（认证）流程



    //登录
    ResponseResult logIn(User user);


    ResponseResult getUserInfo(String userId);

    ResponseResult register(User user, String verifyCode);

    ResponseResult checkRegister(String email);

}
