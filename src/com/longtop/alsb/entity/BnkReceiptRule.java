package com.longtop.alsb.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "BNK_RECEIPT_RULE", schema = "OSBSQL", catalog = "")
public class BnkReceiptRule {
    private String agentid;
    private String lookcount;
    private String intervaltime;
    private Time inputdate;

    @Id
    @Column(name = "AGENTID")
    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    @Basic
    @Column(name = "LOOKCOUNT")
    public String getLookcount() {
        return lookcount;
    }

    public void setLookcount(String lookcount) {
        this.lookcount = lookcount;
    }

    @Basic
    @Column(name = "INTERVALTIME")
    public String getIntervaltime() {
        return intervaltime;
    }

    public void setIntervaltime(String intervaltime) {
        this.intervaltime = intervaltime;
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

        BnkReceiptRule that = (BnkReceiptRule) o;

        if (agentid != null ? !agentid.equals(that.agentid) : that.agentid != null) return false;
        if (lookcount != null ? !lookcount.equals(that.lookcount) : that.lookcount != null) return false;
        if (intervaltime != null ? !intervaltime.equals(that.intervaltime) : that.intervaltime != null) return false;
        if (inputdate != null ? !inputdate.equals(that.inputdate) : that.inputdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = agentid != null ? agentid.hashCode() : 0;
        result = 31 * result + (lookcount != null ? lookcount.hashCode() : 0);
        result = 31 * result + (intervaltime != null ? intervaltime.hashCode() : 0);
        result = 31 * result + (inputdate != null ? inputdate.hashCode() : 0);
        return result;
    }
}
