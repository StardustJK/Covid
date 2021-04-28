package com.bupt.covid.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 患者行程实体类
 */
@Entity
@Table(name = "tb_patient_trip")
public class PatientTrip {
    @Id
    private int id;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "date")
    private Date t_date;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "start")
    private Date t_start;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "end")
    private Date t_end;
    @Column(name = "type")
    private int t_type;
    @Column(name = "no")
    private String t_no;
    @Column(name = "memo")
    private String t_memo;//备注
    @Column(name = "no_sub")
    private String t_no_sub;//车厢号
    @Column(name = "pos_start")
    private String t_pos_start;
    @Column(name = "pos_end")
    private String t_pos_end;
    @Column(name = "source")
    private String source;
    @Column(name = "who")
    private String who;
    @Column(name = "verified")
    private int verified;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private Date created_at;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    private Date updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getT_date() {
        return t_date;
    }

    public void setT_date(Date t_date) {
        this.t_date = t_date;
    }

    public Date getT_start() {
        return t_start;
    }

    public void setT_start(Date t_start) {
        this.t_start = t_start;
    }

    public Date getT_end() {
        return t_end;
    }

    public void setT_end(Date t_end) {
        this.t_end = t_end;
    }

    public int getT_type() {
        return t_type;
    }

    public void setT_type(int t_type) {
        this.t_type = t_type;
    }

    public String getT_no() {
        return t_no;
    }

    public void setT_no(String t_no) {
        this.t_no = t_no;
    }

    public String getT_memo() {
        return t_memo;
    }

    public void setT_memo(String t_memo) {
        this.t_memo = t_memo;
    }

    public String getT_no_sub() {
        return t_no_sub;
    }

    public void setT_no_sub(String t_no_sub) {
        this.t_no_sub = t_no_sub;
    }

    public String getT_pos_start() {
        return t_pos_start;
    }

    public void setT_pos_start(String t_pos_start) {
        this.t_pos_start = t_pos_start;
    }

    public String getT_pos_end() {
        return t_pos_end;
    }

    public void setT_pos_end(String t_pos_end) {
        this.t_pos_end = t_pos_end;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
