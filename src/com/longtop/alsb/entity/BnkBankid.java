package com.longtop.alsb.entity;

import javax.persistence.*;

@Entity
@Table(name = "BNK_BANKID", schema = "OSBSQL")
public class BnkBankid {
    private String id;
    private String bank;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "BANK")
    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
