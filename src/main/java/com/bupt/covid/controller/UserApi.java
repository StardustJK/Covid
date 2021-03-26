package com.bupt.covid.controller;


import com.bupt.covid.pojo.User;
import com.bupt.covid.response.ResponseResult;
import com.bupt.covid.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserApi {
    @Autowired
    private IUserService userService;

    @PostMapping("login")
    public ResponseResult logIn(@RequestBody User user){
        return userService.logIn(user);
    }


}
