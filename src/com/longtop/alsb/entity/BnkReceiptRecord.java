package com.longtop.alsb.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "BNK_RECEIPT_RECORD", schema = "OSBSQL", catalog = "")
public class BnkReceiptRecord {
    private String version;
    private String subbranchid;
    private String agentid;
    private String queryacctid;
    private String querydate;
    private Time inputdate;

    @Id
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
    @Column(name = "AGENTID")
    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    @Basic
    @Column(name = "QUERYACCTID")
    public String getQueryacctid() {
        return queryacctid;
    }

    public void setQueryacctid(String queryacctid) {
        this.queryacctid = queryacctid;
    }

    @Basic
    @Column(name = "QUERYDATE")
    public String getQuerydate() {
        return querydate;
    }

    public void setQuerydate(String querydate) {
        this.querydate = querydate;
    }

    @Basic
    @Column(name = "INPUTDATE")
    public Time getInputdate() {
        return inputdate;
    }

    public void setInputdate(Time inputdate) {
        this.inputdate = inputdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BnkReceiptRecord that = (BnkReceiptRecord) o;

        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (subbranchid != null ? !subbranchid.equals(that.subbranchid) : that.subbranchid != null) return false;
        if (agentid != null ? !agentid.equals(that.agentid) : that.agentid != null) return false;
        if (queryacctid != null ? !queryacctid.equals(that.queryacctid) : that.queryacctid != null) return false;
        if (querydate != null ? !querydate.equals(that.querydate) : that.querydate != null) return false;
        if (inputdate != null ? !inputdate.equals(that.inputdate) : that.inputdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = version != null ? version.hashCode() : 0;
        result = 31 * result + (subbranchid != null ? subbranchid.hashCode() : 0);
        result = 31 * result + (agentid != null ? agentid.hashCode() : 0);
        result = 31 * result + (queryacctid != null ? queryacctid.hashCode() : 0);
        result = 31 * result + (querydate != null ? querydate.hashCode() : 0);
        result = 31 * result + (inputdate != null ? inputdate.hashCode() : 0);
        return result;
    }
}
