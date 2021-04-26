package com.bupt.covid.service;

import com.bupt.covid.pojo.PatientTrip;
import com.bupt.covid.response.ResponseResult;

import java.util.Date;

public interface ITripService {

    /**
     * 条件查询
     * @return
     */
    ResponseResult searchTrip(String area, int type, String no, Date start, Date end);

}
