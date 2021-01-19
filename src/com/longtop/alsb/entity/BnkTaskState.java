package com.longtop.alsb.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "BNK_TASK_STATE", schema = "OSBSQL", catalog = "")
public class BnkTaskState {
    private String ordernum;
    private String version;
    private String subbranchid;
    private String respcode;
    private String respmsg;
    private Time inputdate;

    @Id
    @Column(name = "ORDERNUM")
    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    @Basic
    @Column(name = "VERSION")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Basic
    @Column(name = "SUBBRANCHID")
    public String getSubbranchid() {
        return subbranchid;
    }

    public void setSubbranchid(String subbranchid) {
        this.subbranchid = subbranchid;
    }

    @Basic
    @Column(name = "RESPCODE")
    public String getRespcode() {
        return respcode;
    }

    public void setRespcode(String respcode) {
        this.respcode = respcode;
    }

    @Basic
    @Column(name = "RESPMSG")
    public String getRespmsg() {
        return respmsg;
    }

    public void setRespmsg(String respmsg) {
        this.respmsg = respmsg;
    }

    @Basic
    @Column(name = "INPUTDATE")
    public Time getInputdate() {
        return inputdate;
    }

    public void setInputdate(Time inputdate) {
        this.inputdate = inputdate;
    }
}
