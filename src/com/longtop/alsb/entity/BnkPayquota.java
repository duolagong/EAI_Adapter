package com.longtop.alsb.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "BNK_PAYQUOTA", schema = "OSBSQL", catalog = "")
public class BnkPayquota {
    private String acctidtype;
    private String amount;
    private Time inputdate;

    @Id
    @Column(name = "ACCTIDTYPE")
    public String getAcctidtype() {
        return acctidtype;
    }

    public void setAcctidtype(String acctidtype) {
        this.acctidtype = acctidtype;
    }

    @Basic
    @Column(name = "AMOUNT")
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

        BnkPayquota that = (BnkPayquota) o;

        if (acctidtype != null ? !acctidtype.equals(that.acctidtype) : that.acctidtype != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (inputdate != null ? !inputdate.equals(that.inputdate) : that.inputdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = acctidtype != null ? acctidtype.hashCode() : 0;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (inputdate != null ? inputdate.hashCode() : 0);
        return result;
    }
}
