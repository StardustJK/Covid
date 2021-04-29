package com.bupt.covid.controller;

import com.bupt.covid.pojo.BusTrack;
import com.bupt.covid.pojo.Track;
import com.bupt.covid.response.ResponseResult;
import com.bupt.covid.service.ITrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/track")
public class TrackApi {

    @Autowired
    ITrackService trackService;

    @GetMapping("trackInfo")
    public ResponseResult getTrackInfoByUser(@RequestParam("userId") int userId){
        return trackService.getTracksByUser(userId);
    }

    @GetMapping("busTrack")
    public ResponseResult getBusTrackInfoByUser(@RequestParam("userId") int userId){
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
                                                @RequestParam("userId") int userId){
        return trackService.getTrackByDateAndCity(low,up,city,userId);
    }

    @GetMapping("trackByDateAndDistrict")
    public ResponseResult getTrackByDateAndDistrict(@RequestParam("low") String low,
                                                @RequestParam("up") String up,
                                                @RequestParam("district") String district,
                                                @RequestParam("userId") int userId){
        return trackService.getTrackByDateAndDistrict(low,up,district,userId);
    }

    @PostMapping("addTrack")
    public ResponseResult addTrack(@RequestBody List<Track> track){
        return trackService.addTrack(track);
    }

    @PostMapping("addBusTrack")
    public ResponseResult addBusTrack(@RequestBody List<BusTrack> busTracks){
        return trackService.addBusTrack(busTracks);
    }
}

