package com.longtop.alsb.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Time;

@Entity
@Table(name = "BNK_ACCTPOWER", schema = "OSBSQL", catalog = "")
public class BnkAcctpower {
    private String agentid;
    private String acctid;
    private String querypower;
    private String paypower;
    private String billpower;
    private Time inputdate;
    private String version;
    private String subbranchid;

    @Basic
    @Column(name = "AGENTID")
    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    @Basic
    @Column(name = "ACCTID")
    public String getAcctid() {
        return acctid;
    }

    public void setAcctid(String acctid) {
        this.acctid = acctid;
    }

    @Basic
    @Column(name = "QUERYPOWER")
    public String getQuerypower() {
        return querypower;
    }

    public void setQuerypower(String querypower) {
        this.querypower = querypower;
    }

    @Basic
    @Column(name = "PAYPOWER")
    public String getPaypower() {
        return paypower;
    }

    public void setPaypower(String paypower) {
        this.paypower = paypower;
    }

    @Basic
    @Column(name = "BILLPOWER")
    public String getBillpower() {
        return billpower;
    }

    public void setBillpower(String billpower) {
        this.billpower = billpower;
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

}
