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

    @PostMapping("register")
    public ResponseResult register(@RequestBody User user,
                                   @RequestParam("verify_code") String verifyCode){
        return userService.register(user,verifyCode);
    }

    /**
     * 检查邮箱是否被注册过；手机发短信不好测，先做成邮箱注册
     */
    @GetMapping("checkRegister")
    public ResponseResult checkRegister(@RequestParam("phone")String email){
        return userService.checkRegister(email);
    }



}
