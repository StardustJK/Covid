package com.bupt.covid.service.impl;

import com.bupt.covid.dao.UserDao;
import com.bupt.covid.pojo.User;
import com.bupt.covid.response.ResponseResult;
import com.bupt.covid.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

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
        boolean matches = password.equals(userFromDB.getPassword());
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

    @Override
    public User checkUser() {
        return null;
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


}
