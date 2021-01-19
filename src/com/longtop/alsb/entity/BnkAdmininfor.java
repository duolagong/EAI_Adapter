package com.longtop.alsb.entity;

import javax.persistence.*;

@Entity
@Table(name = "BNK_ADMININFOR", schema = "OSBSQL")
public class BnkAdmininfor {
    private String userversion;
    private String companynum;
    private String appendix;
    private String username;
    private String idcard;
    private String certnum;

    @Id
    @Column(name = "USERVERSION")
    public String getUserversion() {
        return userversion;
    }

    public void setUserversion(String userversion) {
        this.userversion = userversion;
    }

    @Basic
    @Column(name = "COMPANYNUM")
    public String getCompanynum() {
        return companynum;
    }

    public void setCompanynum(String companynum) {
        this.companynum = companynum;
    }

    @Basic
    @Column(name = "APPENDIX")
    public String getAppendix() {
        return appendix;
    }

    public void setAppendix(String appendix) {
        this.appendix = appendix;
    }

    @Basic
    @Column(name = "USERNAME")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "IDCARD")
    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    @Basic
    @Column(name = "CERTNUM")
    public String getCertnum() {
        return certnum;
    }

    public void setCertnum(String certnum) {
        this.certnum = certnum;
    }

}
