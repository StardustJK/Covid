package com.bupt.covid.controller;

import com.bupt.covid.pojo.UserTrip;
import com.bupt.covid.response.ResponseResult;
import com.bupt.covid.service.ITripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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

    @PostMapping("add")
    ResponseResult addTrip(@RequestBody UserTrip userTrip){
        return tripService.addTrip(userTrip);
    }

    @GetMapping("getByUser")
    ResponseResult getTripByUser(@RequestParam("user_id")int userId){
        return tripService.getTripByUser(userId);
    }

    @PostMapping("risk")
    ResponseResult tripRisk(@RequestBody List<UserTrip> userTrip){
        return tripService.tripRisk(userTrip);
    }
    @GetMapping("delete")
    ResponseResult deleteTrip(@RequestParam("id")int id){
        return tripService.deleteTrip(id);
    }

    @GetMapping("riskNotify")
    ResponseResult riskNotify(@RequestParam("user_id")int userId){
        return tripService.riskNotify(userId);
    }

}
