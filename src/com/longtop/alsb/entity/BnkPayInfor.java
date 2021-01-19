package com.longtop.alsb.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "BNK_PAY_INFOR", schema = "OSBSQL")
public class BnkPayInfor {
    private String ordernum;
    private String version;
    private String agentid;
    private String personflag;
    private String txmoment;
    private String tranxml;
    private String erpcode;
    private String erpmsg;
    private String erpxml;
    private Time erpdate;
    private String atomcode;
    private String atommsg;
    private String atomxml;
    private Time atomdate;
    private String osbcode;
    private String osbmsg;
    private String osbxml;
    private Time osbdate;
    private String banksyncode;
    private String banksynmsg;
    private String banksynxml;
    private Time banksyndate;
    private String bankfinalcode;
    private String bankfinalmsg;
    private String bankfinalxml;
    private Time bankfinaldate;
    private String payprocess;
    private String erpsyncode;
    private String erpsynmsg;
    private String erpsynxml;
    private String sendatomtag;
    private String sendatomosbtag;
    private String subbranchid;
    private String txamount;
    private Time trandate;
    private Time erpsyndate;
    private String nbkcode;
    private String nbkmsg;
    private String nbkxml;
    private Time nbkdate;
    private String txcode;

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
    @Column(name = "AGENTID")
    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    @Basic
    @Column(name = "PERSONFLAG")
    public String getPersonflag() {
        return personflag;
    }

    public void setPersonflag(String personflag) {
        this.personflag = personflag;
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
    @Column(name = "TRANXML")
    public String getTranxml() {
        return tranxml;
    }

    public void setTranxml(String tranxml) {
        this.tranxml = tranxml;
    }

    @Basic
    @Column(name = "ERPCODE")
    public String getErpcode() {
        return erpcode;
    }

    public void setErpcode(String erpcode) {
        this.erpcode = erpcode;
    }

    @Basic
    @Column(name = "ERPMSG")
    public String getErpmsg() {
        return erpmsg;
    }

    public void setErpmsg(String erpmsg) {
        this.erpmsg = erpmsg;
    }

    @Basic
    @Column(name = "ERPXML")
    public String getErpxml() {
        return erpxml;
    }

    public void setErpxml(String erpxml) {
        this.erpxml = erpxml;
    }

    @Basic
    @Column(name = "ERPDATE")
    public Time getErpdate() {
        return erpdate;
    }

    public void setErpdate(Time erpdate) {
        this.erpdate = erpdate;
    }

    @Basic
    @Column(name = "ATOMCODE")
    public String getAtomcode() {
        return atomcode;
    }

    public void setAtomcode(String atomcode) {
        this.atomcode = atomcode;
    }

    @Basic
    @Column(name = "ATOMMSG")
    public String getAtommsg() {
        return atommsg;
    }

    public void setAtommsg(String atommsg) {
        this.atommsg = atommsg;
    }

    @Basic
    @Column(name = "ATOMXML")
    public String getAtomxml() {
        return atomxml;
    }

    public void setAtomxml(String atomxml) {
        this.atomxml = atomxml;
    }

    @Basic
    @Column(name = "ATOMDATE")
    public Time getAtomdate() {
        return atomdate;
    }

    public void setAtomdate(Time atomdate) {
        this.atomdate = atomdate;
    }

    @Basic
    @Column(name = "OSBCODE")
    public String getOsbcode() {
        return osbcode;
    }

    public void setOsbcode(String osbcode) {
        this.osbcode = osbcode;
    }

    @Basic
    @Column(name = "OSBMSG")
    public String getOsbmsg() {
        return osbmsg;
    }

    public void setOsbmsg(String osbmsg) {
        this.osbmsg = osbmsg;
    }

    @Basic
    @Column(name = "OSBXML")
    public String getOsbxml() {
        return osbxml;
    }

    public void setOsbxml(String osbxml) {
        this.osbxml = osbxml;
    }

    @Basic
    @Column(name = "OSBDATE")
    public Time getOsbdate() {
        return osbdate;
    }

    public void setOsbdate(Time osbdate) {
        this.osbdate = osbdate;
    }

    @Basic
    @Column(name = "BANKSYNCODE")
    public String getBanksyncode() {
        return banksyncode;
    }

    public void setBanksyncode(String banksyncode) {
        this.banksyncode = banksyncode;
    }

    @Basic
    @Column(name = "BANKSYNMSG")
    public String getBanksynmsg() {
        return banksynmsg;
    }

    public void setBanksynmsg(String banksynmsg) {
        this.banksynmsg = banksynmsg;
    }

    @Basic
    @Column(name = "BANKSYNXML")
    public String getBanksynxml() {
        return banksynxml;
    }

    public void setBanksynxml(String banksynxml) {
        this.banksynxml = banksynxml;
    }

    @Basic
    @Column(name = "BANKSYNDATE")
    public Time getBanksyndate() {
        return banksyndate;
    }

    public void setBanksyndate(Time banksyndate) {
        this.banksyndate = banksyndate;
    }

    @Basic
    @Column(name = "BANKFINALCODE")
    public String getBankfinalcode() {
        return bankfinalcode;
    }

    public void setBankfinalcode(String bankfinalcode) {
        this.bankfinalcode = bankfinalcode;
    }

    @Basic
    @Column(name = "BANKFINALMSG")
    public String getBankfinalmsg() {
        return bankfinalmsg;
    }

    public void setBankfinalmsg(String bankfinalmsg) {
        this.bankfinalmsg = bankfinalmsg;
    }

    @Basic
    @Column(name = "BANKFINALXML")
    public String getBankfinalxml() {
        return bankfinalxml;
    }

    public void setBankfinalxml(String bankfinalxml) {
        this.bankfinalxml = bankfinalxml;
    }

    @Basic
    @Column(name = "BANKFINALDATE")
    public Time getBankfinaldate() {
        return bankfinaldate;
    }

    public void setBankfinaldate(Time bankfinaldate) {
        this.bankfinaldate = bankfinaldate;
    }

    @Basic
    @Column(name = "PAYPROCESS")
    public String getPayprocess() {
        return payprocess;
    }

    public void setPayprocess(String payprocess) {
        this.payprocess = payprocess;
    }

    @Basic
    @Column(name = "ERPSYNCODE")
    public String getErpsyncode() {
        return erpsyncode;
    }

    public void setErpsyncode(String erpsyncode) {
        this.erpsyncode = erpsyncode;
    }

    @Basic
    @Column(name = "ERPSYNMSG")
    public String getErpsynmsg() {
        return erpsynmsg;
    }

    public void setErpsynmsg(String erpsynmsg) {
        this.erpsynmsg = erpsynmsg;
    }

    @Basic
    @Column(name = "ERPSYNXML")
    public String getErpsynxml() {
        return erpsynxml;
    }

    public void setErpsynxml(String erpsynxml) {
        this.erpsynxml = erpsynxml;
    }

    @Basic
    @Column(name = "SENDATOMTAG")
    public String getSendatomtag() {
        return sendatomtag;
    }

    public void setSendatomtag(String sendatomtag) {
        this.sendatomtag = sendatomtag;
    }

    @Basic
    @Column(name = "SENDATOMOSBTAG")
    public String getSendatomosbtag() {
        return sendatomosbtag;
    }

    public void setSendatomosbtag(String sendatomosbtag) {
        this.sendatomosbtag = sendatomosbtag;
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
    @Column(name = "TXAMOUNT")
    public String getTxamount() {
        return txamount;
    }

    public void setTxamount(String txamount) {
        this.txamount = txamount;
    }

    @Basic
    @Column(name = "TRANDATE")
    public Time getTrandate() {
        return trandate;
    }

    public void setTrandate(Time trandate) {
        this.trandate = trandate;
    }

    @Basic
    @Column(name = "ERPSYNDATE")
    public Time getErpsyndate() {
        return erpsyndate;
    }

    public void setErpsyndate(Time erpsyndate) {
        this.erpsyndate = erpsyndate;
    }

    @Basic
    @Column(name = "NBKCODE")
    public String getNbkcode() {
        return nbkcode;
    }

    public void setNbkcode(String nbkcode) {
        this.nbkcode = nbkcode;
    }

    @Basic
    @Column(name = "NBKMSG")
    public String getNbkmsg() {
        return nbkmsg;
    }

    public void setNbkmsg(String nbkmsg) {
        this.nbkmsg = nbkmsg;
    }

    @Basic
    @Column(name = "NBKXML")
    public String getNbkxml() {
        return nbkxml;
    }

    public void setNbkxml(String nbkxml) {
        this.nbkxml = nbkxml;
    }

    @Basic
    @Column(name = "NBKDATE")
    public Time getNbkdate() {
        return nbkdate;
    }

    public void setNbkdate(Time nbkdate) {
        this.nbkdate = nbkdate;
    }

    @Basic
    @Column(name = "TXCODE")
    public String getTxcode() {
        return txcode;
    }

    public void setTxcode(String txcode) {
        this.txcode = txcode;
    }
}
