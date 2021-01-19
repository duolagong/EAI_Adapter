package com.longtop.alsb.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "BNK_PAYMENTTIME", schema = "OSBSQL", catalog = "")
public class BnkPaymenttime {
    private String paymenttype;
    private String starttime;
    private String endtime;
    private Time inputdate;

    @Id
    @Column(name = "PAYMENTTYPE")
    public String getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }

    @Basic
    @Column(name = "STARTTIME")
    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    @Basic
    @Column(name = "ENDTIME")
    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
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

        BnkPaymenttime that = (BnkPaymenttime) o;

        if (paymenttype != null ? !paymenttype.equals(that.paymenttype) : that.paymenttype != null) return false;
        if (starttime != null ? !starttime.equals(that.starttime) : that.starttime != null) return false;
        if (endtime != null ? !endtime.equals(that.endtime) : that.endtime != null) return false;
        if (inputdate != null ? !inputdate.equals(that.inputdate) : that.inputdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = paymenttype != null ? paymenttype.hashCode() : 0;
        result = 31 * result + (starttime != null ? starttime.hashCode() : 0);
        result = 31 * result + (endtime != null ? endtime.hashCode() : 0);
        result = 31 * result + (inputdate != null ? inputdate.hashCode() : 0);
        return result;
    }
}
