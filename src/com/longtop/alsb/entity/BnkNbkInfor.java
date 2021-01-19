package com.longtop.alsb.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "BNK_NBK_INFOR", schema = "OSBSQL", catalog = "")
public class BnkNbkInfor {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BnkNbkInfor that = (BnkNbkInfor) o;

        if (txserial != null ? !txserial.equals(that.txserial) : that.txserial != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (subbranchid != null ? !subbranchid.equals(that.subbranchid) : that.subbranchid != null) return false;
        if (txmoment != null ? !txmoment.equals(that.txmoment) : that.txmoment != null) return false;
        if (reccount != null ? !reccount.equals(that.reccount) : that.reccount != null) return false;
        if (inputdate != null ? !inputdate.equals(that.inputdate) : that.inputdate != null) return false;
        if (agentid != null ? !agentid.equals(that.agentid) : that.agentid != null) return false;
        if (payresultiswait != null ? !payresultiswait.equals(that.payresultiswait) : that.payresultiswait != null)
            return false;
        if (tranxml != null ? !tranxml.equals(that.tranxml) : that.tranxml != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = txserial != null ? txserial.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (subbranchid != null ? subbranchid.hashCode() : 0);
        result = 31 * result + (txmoment != null ? txmoment.hashCode() : 0);
        result = 31 * result + (reccount != null ? reccount.hashCode() : 0);
        result = 31 * result + (inputdate != null ? inputdate.hashCode() : 0);
        result = 31 * result + (agentid != null ? agentid.hashCode() : 0);
        result = 31 * result + (payresultiswait != null ? payresultiswait.hashCode() : 0);
        result = 31 * result + (tranxml != null ? tranxml.hashCode() : 0);
        return result;
    }
}
