package com.bupt.covid.dao;

import com.bupt.covid.pojo.BusTrack;
import com.bupt.covid.pojo.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TrackDao extends JpaRepository<Track,String>, JpaSpecificationExecutor<Track> {
    @Query(nativeQuery = true, value = "select * from `tb_track` where `user_id`=? order by `date_time` ")
    List<Track> getTracksByUser(int userId);

    @Query(nativeQuery = true, value = "select distinct user_id from `tb_track` ")
    List<String> getUserIdHasTrack();

    @Query(nativeQuery = true, value = "select * from `tb_track` where  `date_time` between ?1 and ?2 and `city`=?3 and `user_id`=?4")
    List<Track> getTracksByDateAndCity(String low, String  up,String city,int userId);

    @Query(nativeQuery = true, value = "select * from `tb_track` where  `date_time` between ?1 and ?2 and `district`=?3 and `user_id`=?4")
    List<Track> getTracksByDateAndDistrict(String low, String up, String district, int userId);

    @Modifying
    @Query(nativeQuery = true,value = "insert into `tb_track` (user_id, date_time, longitude, latitude, description, location, district, city)" +
            " values (?1,?2,?3,?4,?5,?6,?7,?8)")
    int addTrack(int userId,
                  Date dateTime,
                  double longitude,
                  double latitude,
                  String description,
                  String location,
                  String district,
                  String city);

    void deleteAllByUserId(int userId);
}
