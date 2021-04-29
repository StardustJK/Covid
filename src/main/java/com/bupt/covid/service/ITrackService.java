package com.bupt.covid.service;

import com.bupt.covid.pojo.BusTrack;
import com.bupt.covid.pojo.Track;
import com.bupt.covid.response.ResponseResult;

import java.util.Date;
import java.util.List;

public interface ITrackService {
    ResponseResult getTracksByUser(int userId);

    ResponseResult getBusTrackByUser(int userId);

    ResponseResult getUserIdHasTrack();

    ResponseResult getTrackByDateAndCity(String low, String up, String city, int userId);

    ResponseResult getTrackByDateAndDistrict(String low, String up, String district, int userId);

    ResponseResult addTrack(List<Track> track);

    ResponseResult addBusTrack(List<BusTrack> busTracks);
}
