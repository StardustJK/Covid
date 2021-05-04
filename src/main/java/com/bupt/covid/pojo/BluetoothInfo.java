package com.bupt.covid.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "bluetooth_info")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class BluetoothInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    Integer userid;
    Integer time_stamp;
    String my_identifier;
    String target_identifier;
    Float distance;
    Integer duration;
    String date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(Integer time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getMy_identifier() {
        return my_identifier;
    }

    public void setMy_identifier(String my_identifier) {
        this.my_identifier = my_identifier;
    }

    public String getTarget_identifier() {
        return target_identifier;
    }

    public void setTarget_identifier(String target_identifier) {
        this.target_identifier = target_identifier;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
