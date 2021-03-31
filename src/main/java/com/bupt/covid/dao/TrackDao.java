package com.bupt.covid.dao;

import com.bupt.covid.pojo.BusTrack;
import com.bupt.covid.pojo.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrackDao extends JpaRepository<Track,String>, JpaSpecificationExecutor<Track> {
    @Query(nativeQuery = true, value = "select * from `tb_track` where `user_id`=? order by `date_time` ")
    List<Track> getTracksByUser(String userId);

    @Query(nativeQuery = true, value = "select distinct user_id from `tb_track` ")
    List<String> getUserIdHasTrack();

}
