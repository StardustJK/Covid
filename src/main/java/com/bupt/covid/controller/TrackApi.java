package com.bupt.covid.controller;

import com.bupt.covid.response.ResponseResult;
import com.bupt.covid.service.ITrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/track")
public class TrackApi {

    @Autowired
    ITrackService trackService;

    @GetMapping("trackInfo")
    public ResponseResult getTrackInfoByUser(@RequestParam("userId") String userId){
        return trackService.getTracksByUser(userId);
    }

    @GetMapping("busTrack")
    public ResponseResult getBusTrackInfoByUser(@RequestParam("userId") String userId){
        return trackService.getBusTrackByUser(userId);
    }

    @GetMapping("userIds")
    public ResponseResult getUserIDHasTrack(){
        return trackService.getUserIdHasTrack();
    }

    @GetMapping("trackByDateAndCity")
    public ResponseResult getTrackByDateAndCity(@RequestParam("low") String low,
                                                @RequestParam("up") String up,
                                                @RequestParam("city") String city,
                                                @RequestParam("userId") String userId){
        return trackService.getTrackByDateAndCity(low,up,city,userId);
    }
}
