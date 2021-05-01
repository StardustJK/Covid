package com.bupt.covid.service;

import com.alibaba.fastjson.JSONArray;
import com.bupt.covid.dao.PatientTripDao;
import com.bupt.covid.pojo.PatientTrip;
import com.bupt.covid.response.PatientTripResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
//import sun.rmi.runtime.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 每日获取患者的行程，保存在数据库中方便查询
 */
@Slf4j
@Configuration
@EnableScheduling//开启定时任务
public class GetPatientTripTask {
    @Autowired
    PatientTripDao patientTripDao;
    //添加定时任务,每天晚上12点执行
    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(cron = "0 0/1 * * * ?")
    private void Tasks(){
        String url = "http://2019ncov.nosugartech.com/data.json";
        RestTemplate restTemplate = new RestTemplate();
        PatientTripResponse patientTrip = restTemplate.getForObject(url, PatientTripResponse.class);
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll((List<HashMap<String, Object>>) patientTrip.getData());
        List<PatientTrip> patientTrips = jsonArray.toJavaList(PatientTrip.class);
        //筛选时间，只添加当天新增的
        List<PatientTrip> addList = null;
        Date today=new Date();
//        log.info("today :  " + today);
        Calendar c=Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DAY_OF_MONTH,-1);
        Date yesterday=c.getTime();
        addList = patientTrips.stream()
                .filter((PatientTrip p) -> yesterday.before(p.getCreated_at()))
                .collect(Collectors.toList());
//        addList.forEach((PatientTrip p) -> log.info(""+p.getCreated_at()));
        patientTripDao.saveAll(addList);
//        log.info(" insert data done");
    }

}
