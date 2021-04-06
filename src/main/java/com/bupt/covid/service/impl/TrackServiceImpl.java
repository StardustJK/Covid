package com.bupt.covid.service.impl;


import com.bupt.covid.dao.BusTrackDao;
import com.bupt.covid.dao.TrackDao;
import com.bupt.covid.pojo.BusTrack;
import com.bupt.covid.pojo.Track;
import com.bupt.covid.response.ResponseResult;
import com.bupt.covid.service.ITrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TrackServiceImpl implements ITrackService {

    @Autowired
    TrackDao trackDao;

    @Autowired
    BusTrackDao busTrackDao;

    @Override
    public ResponseResult getTracksByUser(String userId) {
        List<Track> tracksByUser = trackDao.getTracksByUser(userId);
        if (tracksByUser != null) {
            return ResponseResult.SUCCESS("获取成功").setData(tracksByUser);
        }
        return ResponseResult.FAILED("该用户无轨迹记录");
    }

    @Override
    public ResponseResult getBusTrackByUser(String userId) {

        List<BusTrack> busTrackByUser=busTrackDao.getBusTracksByUser(userId);
        if(busTrackByUser!=null){
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
    public ResponseResult getTrackByDateAndCity(String low, String up, String city, String userId) {
        List<Track> tracksByDateAndCity = trackDao.getTracksByDateAndCity(low,up,city,userId);
        if(tracksByDateAndCity==null||tracksByDateAndCity.size()==0){
            return ResponseResult.FAILED("没有符合条件的轨迹");
        }
        return ResponseResult.SUCCESS("成功获取轨迹").setData(tracksByDateAndCity);
    }

    @Override
    public ResponseResult getTrackByDateAndDistrict(String low, String up, String district, String userId) {
        List<Track> tracksByDateAndCity = trackDao.getTracksByDateAndDistrict(low,up,district,userId);
        if(tracksByDateAndCity==null||tracksByDateAndCity.size()==0){
            return ResponseResult.FAILED("没有符合条件的轨迹");
        }
        return ResponseResult.SUCCESS("成功获取轨迹").setData(tracksByDateAndCity);
    }

    @Override
    public ResponseResult addTrack(Track track) {
        trackDao.deleteAllByUserId(track.getUserId());
        int result = trackDao.addTrack(track.getUserId(), track.getDateTime(), track.getLongitude(),
                track.getLatitude(), track.getDescription(), track.getLocation(), track.getDistrict(), track.getCity());
        if(result!=0){
            return ResponseResult.SUCCESS("轨迹上传成功");
        }
        return ResponseResult.FAILED("轨迹上传失败");

    }
}
