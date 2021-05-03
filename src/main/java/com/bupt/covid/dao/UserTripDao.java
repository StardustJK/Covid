package com.bupt.covid.dao;

import com.bupt.covid.pojo.PatientTrip;
import com.bupt.covid.pojo.UserTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserTripDao extends JpaRepository<UserTrip,Integer>, JpaSpecificationExecutor<UserTrip> {

    List<UserTrip> findAllByUserIdOrderByDateDesc(int userId);
    UserTrip findOneById(int id);

}
