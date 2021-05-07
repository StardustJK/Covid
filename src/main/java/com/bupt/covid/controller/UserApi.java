package com.bupt.covid.controller;


import com.bupt.covid.pojo.User;
import com.bupt.covid.response.ResponseResult;
import com.bupt.covid.service.IStatusService;
import com.bupt.covid.service.IUserService;
import com.bupt.covid.utils.AlgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserApi {
    @Autowired
    private IUserService userService;

    @Autowired
    private IStatusService statusService;

    @Autowired
    private  AlgUtil algUtil;

    @PostMapping("login")
    public ResponseResult logIn(@RequestBody User user){
        return userService.logIn(user);
    }


    @GetMapping("userInfo")
    public ResponseResult getUserInfo(@RequestParam("userId") int userId){
        return userService.getUserInfo(userId);
    }

    @GetMapping("status")
    public ResponseResult getStatuses(@RequestParam("userId") int userId){
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

    /**
     * 获得指定用户的蓝牙感染风险等级
     * @param userId
     * @return
     */
    @GetMapping("getBluetoothRiskLevel")
    public ResponseResult getBluetoothRiskLevel(@RequestParam("userId") int userId) throws Exception {
        algUtil.updateAllInfectionRateAndRiskLevel();
        return userService.getBluetoothRiskLevel(userId);
    }

    @GetMapping("updateShowTripRisk")
    public ResponseResult updateShowTripRisk(@RequestParam("user_id")int userId,@RequestParam("show")boolean show){
        return userService.updateShowTripRisk(userId,show);
    }


}
