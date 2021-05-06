package com.bupt.covid.service.impl;


import com.bupt.covid.dao.BusTrackDao;
import com.bupt.covid.dao.TrackDao;
import com.bupt.covid.pojo.BusTrack;
import com.bupt.covid.pojo.Track;
import com.bupt.covid.response.ResponseResult;
import com.bupt.covid.service.ITrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class TrackServiceImpl implements ITrackService {

    @Autowired
    TrackDao trackDao;

    @Autowired
    BusTrackDao busTrackDao;

    @Override
    public ResponseResult getTracksByUser(int userId) {
        List<Track> tracksByUser = trackDao.getTracksByUser(userId);
        if (tracksByUser != null) {
            return ResponseResult.SUCCESS("获取成功").setData(tracksByUser);
        }
        return ResponseResult.FAILED("该用户无轨迹记录");
    }

    @Override
    public ResponseResult getBusTrackByUser(int userId) {

        List<BusTrack> busTrackByUser = busTrackDao.getBusTracksByUser(userId);
        if (busTrackByUser != null) {
            return ResponseResult.SUCCESS("获取成功").setData(busTrackByUser);
        }
        return ResponseResult.FAILED("该用户无轨迹记录");


    }

    @Override
    public ResponseResult getUserIdHasTrack() {
        List<String> userIds = trackDao.getUserIdHasTrack();

        return ResponseResult.SUCCESS("获取成功").setData(userIds);
    }

    @Override
    public ResponseResult getTrackByDateAndCity(String low, String up, String city, int userId) {
        List<Track> tracksByDateAndCity = trackDao.getTracksByDateAndCity(low, up, city, userId);
        if (tracksByDateAndCity == null || tracksByDateAndCity.size() == 0) {
            return ResponseResult.FAILED("没有符合条件的轨迹");
        }
        return ResponseResult.SUCCESS("成功获取轨迹").setData(tracksByDateAndCity);
    }

    @Override
    public ResponseResult getTrackByDateAndDistrict(String low, String up, String district, int userId) {
        List<Track> tracksByDateAndCity = trackDao.getTracksByDateAndDistrict(low, up, district, userId);
        if (tracksByDateAndCity == null || tracksByDateAndCity.size() == 0) {
            return ResponseResult.FAILED("没有符合条件的轨迹");
        }
        return ResponseResult.SUCCESS("成功获取轨迹").setData(tracksByDateAndCity);
    }

    @Override
    public ResponseResult addTrack(List<Track> trackList) {
        if(trackList.size()==0){
            return ResponseResult.FAILED("轨迹为空");
        }
        trackDao.deleteAllByUserId(trackList.get(0).getUserId());
        int result = 0;
        for (int i = 0; i < trackList.size(); i++) {
            Track track = trackList.get(i);
            result = trackDao.addTrack(track.getUserId(), track.getDateTime(), track.getLongitude(),
                    track.getLatitude(), track.getDescription(), track.getLocation(), track.getDistrict(), track.getCity());

        }
        if (result != 0) {
            return ResponseResult.SUCCESS("轨迹上传成功");
        }
        return ResponseResult.FAILED("轨迹上传失败");
    }

    @Override
    public ResponseResult addBusTrack(List<BusTrack> busTracks) {
        if(busTracks.size()==0){
            return ResponseResult.FAILED("轨迹为空");
        }
        busTrackDao.deleteAllByUserId(busTracks.get(0).getUserId());
        int result = 0;
        for (int i = 0; i < busTracks.size(); i++) {
            BusTrack busTrack = busTracks.get(i);
            busTrackDao.save(busTrack);

        }
        return ResponseResult.FAILED("轨迹上传成功");

    }
}
