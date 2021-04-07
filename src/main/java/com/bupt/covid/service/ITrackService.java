package com.bupt.covid.service;

import com.bupt.covid.pojo.BusTrack;
import com.bupt.covid.pojo.Track;
import com.bupt.covid.response.ResponseResult;

import java.util.Date;
import java.util.List;

public interface ITrackService {
    ResponseResult getTracksByUser(String userId);

    ResponseResult getBusTrackByUser(String userId);

    ResponseResult getUserIdHasTrack();

    ResponseResult getTrackByDateAndCity(String low, String up, String city, String userId);

    ResponseResult getTrackByDateAndDistrict(String low, String up, String district, String userId);

    ResponseResult addTrack(List<Track> track);

    ResponseResult addBusTrack(List<BusTrack> busTracks);
}
