package com.bupt.covid.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "tb_user_trip")
public class UserTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private int userId;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @Column(name = "date")
    private Date date;
    @Column(name = "type")
    private int type;
    @Column(name = "no")
    private String no;
    @Column(name = "memo")
    private String memo;//备注
    @Column(name = "no_sub")
    private String no_sub;//车厢号
    @Column(name = "pos_start")
    private String pos_start;
    @Column(name = "pos_end")
    private String pos_end;

    @Column(name = "risk")
    private boolean risk;

    public boolean isRisk() {
        return risk;
    }

    public void setRisk(boolean risk) {
        this.risk = risk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getNo_sub() {
        return no_sub;
    }

    public void setNo_sub(String no_sub) {
        this.no_sub = no_sub;
    }

    public String getPos_start() {
        return pos_start;
    }

    public void setPos_start(String pos_start) {
        this.pos_start = pos_start;
    }

    public String getPos_end() {
        return pos_end;
    }

    public void setPos_end(String pos_end) {
        this.pos_end = pos_end;
    }
}
