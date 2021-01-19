package com.longtop.alsb.entity;

import javax.persistence.*;

@Entity
@Table(name = "BNK_GSBH", schema = "OSBSQL")
public class BnkGsbh {
    private String fQybh;
    private String fQymc;
    private String fMbdz;
    private String fSftpIp;
    private String fSftpPost;
    private String fUser;
    private String fPassword;
    private String fBudgetSite;
    private String fBudgetUser;
    private String fBudgetPassword;
    private String fSwitch;

    @Id
    @Column(name = "F_QYBH")
    public String getfQybh() {
        return fQybh;
    }

    public void setfQybh(String fQybh) {
        this.fQybh = fQybh;
    }

    @Basic
    @Column(name = "F_QYMC")
    public String getfQymc() {
        return fQymc;
    }

    public void setfQymc(String fQymc) {
        this.fQymc = fQymc;
    }

    @Basic
    @Column(name = "F_MBDZ")
    public String getfMbdz() {
        return fMbdz;
    }

    public void setfMbdz(String fMbdz) {
        this.fMbdz = fMbdz;
    }

    @Basic
    @Column(name = "F_SFTP_IP")
    public String getfSftpIp() {
        return fSftpIp;
    }

    public void setfSftpIp(String fSftpIp) {
        this.fSftpIp = fSftpIp;
    }

    @Basic
    @Column(name = "F_SFTP_POST")
    public String getfSftpPost() {
        return fSftpPost;
    }

    public void setfSftpPost(String fSftpPost) {
        this.fSftpPost = fSftpPost;
    }

    @Basic
    @Column(name = "F_USER")
    public String getfUser() {
        return fUser;
    }

    public void setfUser(String fUser) {
        this.fUser = fUser;
    }

    @Basic
    @Column(name = "F_PASSWORD")
    public String getfPassword() {
        return fPassword;
    }

    public void setfPassword(String fPassword) {
        this.fPassword = fPassword;
    }

    @Basic
    @Column(name = "F_BUDGET_SITE")
    public String getfBudgetSite() {
        return fBudgetSite;
    }

    public void setfBudgetSite(String fBudgetSite) {
        this.fBudgetSite = fBudgetSite;
    }

    @Basic
    @Column(name = "F_BUDGET_USER")
    public String getfBudgetUser() {
        return fBudgetUser;
    }

    public void setfBudgetUser(String fBudgetUser) {
        this.fBudgetUser = fBudgetUser;
    }

    @Basic
    @Column(name = "F_BUDGET_PASSWORD")
    public String getfBudgetPassword() {
        return fBudgetPassword;
    }

    public void setfBudgetPassword(String fBudgetPassword) {
        this.fBudgetPassword = fBudgetPassword;
    }

    @Basic
    @Column(name = "F_SWITCH")
    public String getfSwitch() {
        return fSwitch;
    }

    public void setfSwitch(String fSwitch) {
        this.fSwitch = fSwitch;
    }
}
