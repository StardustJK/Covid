package com.bupt.covid.service;

import com.bupt.covid.pojo.PatientTrip;
import com.bupt.covid.pojo.UserTrip;
import com.bupt.covid.response.ResponseResult;

import java.util.Date;

import java.util.List;


public interface ITripService {

    /**
     * 条件查询
     * @return
     */
    ResponseResult searchTrip(String area, int type, String no, Date start, Date end);

    ResponseResult addTrip(UserTrip userTrip);

    ResponseResult getTripByUser(int userId);

    ResponseResult tripRisk(List<UserTrip> userTrip);

}
