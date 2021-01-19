package com.longtop.alsb.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "BNK_ERP_INFOR", schema = "OSBSQL")
public class BnkErpInfor {
    private String txserial;
    private String version;
    private String subbranchid;
    private String txmoment;
    private String reccount;
    private Time inputdate;
    private String agentid;
    private String payresultiswait;
    private String tranxml;

    @Id
    @Column(name = "TXSERIAL")
    public String getTxserial() {
        return txserial;
    }

    public void setTxserial(String txserial) {
        this.txserial = txserial;
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
    @Column(name = "TXMOMENT")
    public String getTxmoment() {
        return txmoment;
    }

    public void setTxmoment(String txmoment) {
        this.txmoment = txmoment;
    }

    @Basic
    @Column(name = "RECCOUNT")
    public String getReccount() {
        return reccount;
    }

    public void setReccount(String reccount) {
        this.reccount = reccount;
    }

    @Basic
    @Column(name = "INPUTDATE")
    public Time getInputdate() {
        return inputdate;
    }

    public void setInputdate(Time inputdate) {
        this.inputdate = inputdate;
    }

    @Basic
    @Column(name = "AGENTID")
    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    @Basic
    @Column(name = "PAYRESULTISWAIT")
    public String getPayresultiswait() {
        return payresultiswait;
    }

    public void setPayresultiswait(String payresultiswait) {
        this.payresultiswait = payresultiswait;
    }

    @Basic
    @Column(name = "TRANXML")
    public String getTranxml() {
        return tranxml;
    }

    public void setTranxml(String tranxml) {
        this.tranxml = tranxml;
    }

}
