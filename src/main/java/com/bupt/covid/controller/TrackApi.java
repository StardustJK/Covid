package com.bupt.covid.controller;

import com.bupt.covid.response.ResponseResult;
import com.bupt.covid.service.ITrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
