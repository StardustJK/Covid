package com.bupt.covid.controller;

import com.bupt.covid.dao.UserDao;
import com.bupt.covid.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @Autowired
    UserDao userDao;
    @GetMapping("/hello")
    public User hello(){
        User oneById = userDao.findAllById("1");
        //log.info(oneById.getName());
        return oneById;
    }
}
