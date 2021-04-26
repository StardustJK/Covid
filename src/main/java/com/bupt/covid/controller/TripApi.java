package com.bupt.covid.controller;

import com.bupt.covid.response.ResponseResult;
import com.bupt.covid.service.ITripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/trip")
public class TripApi {
    @Autowired
    ITripService tripService;


    /**
     * @param area 全部->空
     * @param type 全部-> -1
     * @param no 全部-> 空
     * @param start 必填（默认前端获取前14天）
     * @param end 必填
     * @return
     */
    @GetMapping("search")
    ResponseResult searchTrip(@RequestParam("area") String area,
                              @RequestParam("type") int type,
                              @RequestParam("no") String no,
                              @RequestParam("start") Date start,
                              @RequestParam("end") Date end){
        return tripService.searchTrip(area,type,no,start,end);
    }


}
