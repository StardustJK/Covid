package com.bupt.covid.dao;

import com.bupt.covid.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDao extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {
    User findAllById(int userId);
    User findOneByPhone(String phone);
    User findOneByName(String name);
    User findOneById(int id);
}
