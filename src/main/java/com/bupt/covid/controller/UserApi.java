package com.bupt.covid.controller;


import com.bupt.covid.pojo.User;
import com.bupt.covid.response.ResponseResult;
import com.bupt.covid.service.IStatusService;
import com.bupt.covid.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserApi {
    @Autowired
    private IUserService userService;

    @Autowired
    private IStatusService statusService;

    @PostMapping("login")
    public ResponseResult logIn(@RequestBody User user){
        return userService.logIn(user);
    }


    @GetMapping("userInfo")
    public ResponseResult getUserInfo(@RequestParam("userId") String userId){
        return userService.getUserInfo(userId);
    }

    @GetMapping("status")
    public ResponseResult getStatuses(@RequestParam("userId") String userId){
        return statusService.getStatusByUser(userId);
    }


}
