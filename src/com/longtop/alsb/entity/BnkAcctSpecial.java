package com.longtop.alsb.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Time;

@Entity
@Table(name = "BNK_ACCT_SPECIAL", schema = "OSBSQL", catalog = "")
public class BnkAcctSpecial {
    private String subbranchid;
    private Time inputdate;

    @Basic
    @Column(name = "SUBBRANCHID")
    public String getSubbranchid() {
        return subbranchid;
    }

    public void setSubbranchid(String subbranchid) {
        this.subbranchid = subbranchid;
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

        BnkAcctSpecial that = (BnkAcctSpecial) o;

        if (subbranchid != null ? !subbranchid.equals(that.subbranchid) : that.subbranchid != null) return false;
        if (inputdate != null ? !inputdate.equals(that.inputdate) : that.inputdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = subbranchid != null ? subbranchid.hashCode() : 0;
        result = 31 * result + (inputdate != null ? inputdate.hashCode() : 0);
        return result;
    }
}
