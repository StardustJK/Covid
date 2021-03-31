package com.bupt.covid.dao;

import com.bupt.covid.pojo.BusTrack;
import com.bupt.covid.pojo.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusTrackDao extends JpaRepository<BusTrack,String>, JpaSpecificationExecutor<BusTrack> {
    @Query(nativeQuery = true, value = "select * from `tb_bus_track` where `user_id`=? order by `date_time` ")
    List<BusTrack> getBusTracksByUser(String userId);

}
