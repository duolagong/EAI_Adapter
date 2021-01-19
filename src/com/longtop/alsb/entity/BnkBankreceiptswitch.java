package com.longtop.alsb.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "BNK_BANKRECEIPTSWITCH", schema = "OSBSQL")
public class BnkBankreceiptswitch {
    private String agentId;
    private String queryDate;
    private String respCode;
    private String respMsg;
    private Time inputDate;

    @Id
    @Column(name = "AGENTID")
    public String getAgentid() {
        return agentId;
    }

    public void setAgentid(String agentId) {
        this.agentId = agentId;
    }

    @Basic
    @Column(name = "QUERYDATE")
    public String getQuerydate() {
        return queryDate;
    }

    public void setQuerydate(String queryDate) {
        this.queryDate = queryDate;
    }

    @Basic
    @Column(name = "RESPCODE")
    public String getRespcode() {
        return respCode;
    }

    public void setRespcode(String respCode) {
        this.respCode = respCode;
    }

    @Basic
    @Column(name = "RESPMSG")
    public String getRespmsg() {
        return respMsg;
    }

    public void setRespmsg(String respMsg) {
        this.respMsg = respMsg;
    }

    @Basic
    @Column(name = "INPUTDATE")
    public Time getInputdate() {
        return inputDate;
    }

    public void setInputdate(Time inputDate) {
        this.inputDate = inputDate;
    }
}
