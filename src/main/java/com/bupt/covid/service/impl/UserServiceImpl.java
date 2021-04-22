package com.bupt.covid.service.impl;

import com.bupt.covid.dao.UserDao;
import com.bupt.covid.pojo.User;
import com.bupt.covid.response.ResponseResult;
import com.bupt.covid.service.IUserService;
import com.bupt.covid.utils.Constants;
import com.bupt.covid.utils.RedisUtil;
import com.bupt.covid.utils.SnowFlakeIdWorker;
import com.bupt.covid.utils.TextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.Random;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    Random random;

    @Autowired
    TaskService taskService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SnowFlakeIdWorker idWorker;




    /**
     * 登录：使用手机号(or用户名)和密码,前端做判空
     *
     * @param user
     * @return
     */
    @Override
    public ResponseResult logIn(User user) {
        String phone = user.getPhone();
        String password = user.getPassword();
        User userFromDB = userDao.findOneByPhone(phone);
        if (userFromDB == null) {
            userFromDB = userDao.findOneByName(phone);
        }
        //用户不存在
        if (userFromDB == null) {
            return ResponseResult.FAILED("用户不存在");
        }

        //用户存在，判断密码是否正确
        boolean matches = bCryptPasswordEncoder.matches(password, userFromDB.getPassword());
        if (!matches) {
            return ResponseResult.FAILED("账号或者密码错误");
        }

        //密码正确
        return ResponseResult.SUCCESS("登录成功").setData(getUserNoPassWord(userFromDB));
    }


    /**
     * 仅返回Id,phone,name,status,role信息
     *
     * @param user
     * @return
     */
    public User getUserNoPassWord(User user) {
        User returnedUser = new User();
        returnedUser.setId(user.getId());
        returnedUser.setPhone(user.getPhone());
        returnedUser.setName(user.getName());
        returnedUser.setStatus(user.getStatus());
        returnedUser.setRole(user.getRole());
        returnedUser.setAuth(user.getAuth());
        return returnedUser;
    }


    /**
     * 仅返回Id,phone,name,status,role信息
     *
     * @param userId
     * @return
     */
    @Override
    public ResponseResult getUserInfo(String userId) {

        User userFromDb = userDao.findOneById(userId);
        if (userFromDb == null) {
            return ResponseResult.FAILED("用户不存在");
        } else return ResponseResult.SUCCESS("成功获取").setData(getUserNoPassWord(userFromDb));
    }

    @Override
    public ResponseResult register(User user, String verifyCode) {
        //register之前会先checkRegister，分开写是为了少发验证码
        //检查验证码
        String email=user.getPhone();
        String emailVerifyCode= (String) redisUtil.get(Constants.User.KEY_EMAIL_CODE_CONTENT+email);
        if (TextUtils.isEmpty(emailVerifyCode)) {
            return ResponseResult.FAILED("邮箱验证码已过期");
        }
        if (!emailVerifyCode.equals(verifyCode)) {
            return ResponseResult.FAILED("邮箱验证码不正确");
        } else {
            //正确，删掉redis里面的内容
            redisUtil.del(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
        }
        //密码加密
        String password=user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));

        //补全数据
        String id=idWorker.nextId()+"";
        user.setId(id);
        user.setName("未认证用户"+id.substring(0,4));

        //存数据
        userDao.save(user);
        return ResponseResult.SUCCESS("注册成功");


    }

    @Override
    public ResponseResult checkRegister(String email) {
        if (TextUtils.isEmpty(email)) {
            return ResponseResult.FAILED("邮箱不可以为空");
        }
        User userByName = userDao.findOneByPhone(email);
        if (userByName != null) {
            return ResponseResult.FAILED("该邮箱已被注册");
        }
        if (!TextUtils.isEmailAddressValid(email)) {
            return ResponseResult.FAILED("邮箱格式不正确");
        }

        return sendEmail(email);

    }


    public ResponseResult sendEmail(String email) {
        //1 防止暴力发送（不断发送）。同一个邮箱间隔要超过60s
        Object hasEmailSend=redisUtil.get(Constants.User.KEY_EMAIL_SEND_ADDRESS+email);
        if (hasEmailSend != null) {
            return ResponseResult.FAILED("发送验证码过于频繁2");
        }
        //2.发送验证码6位数100000-999999
        int code=random.nextInt(999999);
        if (code < 100000) {
            code += 100000;
        }
        log.info("sendEmail code == > " + code);
        try {
            taskService.sendEmailVerifyCode(code + "", email);
        } catch (MessagingException e) {
            return ResponseResult.FAILED("验证码发送失败，请稍后重试");
        }
        //60s消失
        redisUtil.set(Constants.User.KEY_EMAIL_SEND_ADDRESS + email, "true", Constants.User.EMAIL_SEND_TIME_INTERVAL);
        //保存code,10分钟
        redisUtil.set(Constants.User.KEY_EMAIL_CODE_CONTENT+email,code+"");

        return ResponseResult.SUCCESS("验证码发送成功");


    }


}
