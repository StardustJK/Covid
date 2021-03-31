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
}
