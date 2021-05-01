package com.bupt.covid.dao;

import com.bupt.covid.pojo.BusTrack;
import com.bupt.covid.pojo.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BusTrackDao extends JpaRepository<BusTrack,String>, JpaSpecificationExecutor<BusTrack> {
    @Query(nativeQuery = true, value = "select * from `tb_bus_track` where `user_id`=? order by `date_time` ")
    List<BusTrack> getBusTracksByUser(int userId);

    @Modifying
    @Query(nativeQuery = true,value = "insert into `tb_bus_track` (id, user_id, name, start, end, date_time)" +
            " values (?1,?2,?3,?4,?5,?6)")
    int addBusTrack(String id,
                 int userId,
                 String name,
                 String start,
                 String end,
                 Date date_time);

    void deleteAllByUserId(int userId);

}
