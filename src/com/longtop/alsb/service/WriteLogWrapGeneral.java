package com.longtop.alsb.service;

import com.longtop.Util.HelpCentre.SFTPUtil;
import com.longtop.Util.LoadMotor.LoadEngine;
import com.longtop.alsb.dao.HibernateContext;
import com.longtop.alsb.service.business.*;
import org.apache.log4j.Logger;

public class WriteLogWrapGeneral {

    private static Logger logger = Logger.getLogger(WriteLogWrapGeneral.class);

    private static HibernateContext getHqlContext() {
        return LoadEngine.getDBContext("");
    }

    private static HibernateContext getSqlContext() {
        return LoadEngine.getDBContext("SQL");
    }

    //----------bnk_erp_infor start!
    public static String insertERPInfor(String version, String subbranchid, String txserial, String txmoment, String agentid,
                                        String reccount, String payresultIsWait, String tranxml) {
        return BankErpInfor.insertERPInfor(getHqlContext(), version, subbranchid, txserial, txmoment, agentid,
                reccount, payresultIsWait, tranxml);
    }
    //----------bnk_erp_infor End!

    //----------bnk_nbk_infor start!
    public static String insertNBKInfor(String version, String subbranchid, String txserial, String txmoment, String agentid,
                                        String reccount, String payresultIsWait, String tranxml) {
        return BankNbkInfor.insertERPInfor(getHqlContext(), version, subbranchid, txserial, txmoment, agentid,
                reccount, payresultIsWait, tranxml);
    }
    //----------bnk_nbk_infor End!

    //----------bnk_admininfor start!
    public static String confirmAdminInfor(String version) {
        return BankAdminInfor.confirmAdminInfor(getHqlContext(), version);
    }

    public static String getAdminInfor(String UserVersion, String CompanyNum, String Appendix, String UserName) {
        return BankAdminInfor.getAdminInfor(getHqlContext(), UserVersion, CompanyNum, Appendix, UserName);
    }

    public static String judgeAdminInfor(String MakeUserCert, String MakeUserID, String version, String subBranchId, String Appendix) {
        return BankAdminInfor.judgeAdminInfor(getHqlContext(), MakeUserCert, MakeUserID, version, subBranchId, Appendix);
    }

    public static String addAdminInfor(String UserVersion, String CompanyNum, String Appendix, String UserName, String IdCard, String CertNum) {
        return BankAdminInfor.addAdminInfor(getHqlContext(), UserVersion, CompanyNum, Appendix, UserName, IdCard, CertNum);
    }

    public static String delectAdminInfor(String UserVersion, String CompanyNum, String Appendix, String UserName) {
        return BankAdminInfor.delectAdminInfor(getHqlContext(), UserVersion, CompanyNum, Appendix, UserName);
    }
    //----------bnk_admininfor End!

    //---------Bnk_AcctPower start!
    public static String confirmAcctPower(String version) {
        return BankAcctPower.confirmAcctPower(getHqlContext(), version);
    }

    public static String getAcctIdPower(String bankAgentid, String acctId, String powerNum, String version) {
        return BankAcctPower.getAcctIdPower(getHqlContext(), bankAgentid, acctId, powerNum, version);
    }

    public static String getAcctSubBranchId(String agentId, String acctId, String version){
        return BankAcctPower.getAcctSubBranchId(getHqlContext(),agentId, acctId, version);
    }

    public static String getAcctPower(String agentId, String acctId, String version) {
        return BankAcctPower.getAcctPower(getHqlContext(), agentId, acctId, version);
    }

    //删除
    public static String addAcctPower(String agentId, String acctId, String queryPower, String paypower, String billpower, String version) {return null;}

    public static String addAcctPower(String agentId, String acctId, String queryPower, String paypower, String billpower,
                                      String version, String subbranchid) {
        return BankAcctPower.addAcctPower(getHqlContext(), agentId, acctId, queryPower, paypower, billpower, version,subbranchid);
    }

    //删除
    public static String updateAcctPower(String agentId, String acctId, String queryPower, String paypower, String billpower, String version){return null;}

    public static String updateAcctPower(String agentId, String acctId, String queryPower, String paypower, String billpower,
                                         String version, String subbranchid) {
        return BankAcctPower.updateAcctPower(getHqlContext(), agentId, acctId, queryPower, paypower, billpower, version,subbranchid);
    }

    public static String delectAcctPower(String agentId, String acctId, String version) {
        return BankAcctPower.delectAcctPower(getHqlContext(), agentId, acctId, version);
    }
    //---------Bnk_AcctPower End!

    //---------Bnk_PaymentTime start!
    public static String getPaymentTime(String agentId) {
        return BankPaymentTime.getPaymentTime(getHqlContext(), agentId);
    }
    //---------Bnk_PaymentTime End!

    //---------Bnk_PayQuota start!
    public static String getPayQuota(String acctidType) {
        return BankPayQuota.getPayQuota(getHqlContext(), acctidType);
    }
    //---------Bnk_PayQuota End!

    //---------Bnk_PayQuota start!
    public static String getSpecialAcctId(String subBranchId) {
        return BankAcctSpecial.getSpecialAcctId(getHqlContext(), subBranchId);
    }
    //---------Bnk_PayQuota End!

    //---------Bnk_GSBH start!
    public static String checkVersion(String Version) {
        return BankGSBH.checkVersion(getHqlContext(), Version);
    }

    /**
     * 获取共享系统的目标地址
     * @param version
     * @return
     */
    public static String getTargetAddress(String version) {
        return BankGSBH.getTargetAddress(getHqlContext(), version);
    }

    /**
     * 获取共享系统的预算地址
     * @param version
     * @return
     */
    public  static  String getBudgetAddress(String version){
        return BankGSBH.getBudgetAddress(getHqlContext(), version);
    }

    public static String checkSwitch(String version, String txCode) {
        return BankGSBH.checkSwitch(getHqlContext(), version, txCode);
    }

    public static String updateSystemParameter(String subBranchId, String agentId, String modifyType, String modifyInfor, String appendix,
                                               String modifyInforAdd, String appendixAdd, String targetAddress) {
        return BankGSBH.updateSystemParameter(getHqlContext(), subBranchId, agentId, modifyType, modifyInfor, appendix,
                modifyInforAdd, appendixAdd, targetAddress);
    }

    public static String updateSystemParameter(String version, String sftpIp, String sftpPost, String user, String password) {
        return BankGSBH.updateSystemParameter(getHqlContext(), version, sftpIp, sftpPost, user, password);
    }

    public static String updateBudgetParameter(String version, String autoMonitorInd, String budgetTargetAdd, String budgetTargetUser,
                                               String budgetTargetPass) {
        return BankGSBH.updateBudgetParameter(getHqlContext(), version, autoMonitorInd, budgetTargetAdd, budgetTargetUser,
                budgetTargetPass);
    }

    public static String delectSystemParameter(String modifyInfor) {
        return BankGSBH.delectSystemParameter(getHqlContext(), modifyInfor);
    }
    //---------Bnk_GSBH End!

    //----------bnk_pay_infor start!

    /**
     * 校验是否是资管数据
     *
     * @param ordernum
     * @return
     */
    public static String getNBKPayment(String ordernum) {
        return BankPayInfor.getNBKPayment(getHqlContext(), ordernum);
    }

    public static String getVsrsion(String ordernum) {
        return BankPayInfor.getVsrsion(getHqlContext(), ordernum);
    }

    public static String getCheckOrderNum(String orderNum) {
        return BankPayInfor.getCheckOrderNum(getHqlContext(), orderNum);
    }

    public static boolean updateSystemSynXml(String ordernum, String version, String PayProcess, String ProcessType, String RespCode,
                                             String RespMsg, String ProcessXml) {
        return BankPayInfor.updateSystemSynXml(getHqlContext(), ordernum, version, PayProcess, ProcessType, RespCode, RespMsg, ProcessXml);
    }

    public static String getPayStatus(String orderNum, String version, String agentId) {
        return BankPayInfor.getPayStatus(getHqlContext(), orderNum, version, agentId);
    }

    public static String insertLogErpSendXml(String ordernum, String version, String AgentId, String PersonFlag, String TxMoment,
                                             String SendXml, String subBranchId, String txAmount, String txCode) {
        return BankPayInfor.insertLogErpSendXml(getHqlContext(), ordernum, version, AgentId, PersonFlag, TxMoment,
                SendXml, subBranchId, txAmount,txCode);
    }

    public static boolean updateSendAtomTag(String ordernum, String version, String AgentId, String SendTag) {
        return BankPayInfor.updateSendAtomTag(getHqlContext(), ordernum, version, AgentId, SendTag);
    }

    public static void getPayStatus(){
        BankPayInfor.getPayStatus(getSqlContext());
    }

    //-----------bnk_pay_infor End!

    //-----------BNK_BANKID start!
    public static String getBankId(String id) {
        return BankBankId.getBankId(getHqlContext(), id);
    }
    //-----------BNK_BANKID End!

    //-----------BNK_BANKRECEIPTSWITCH start!
    public static String insertBankReceiptSwitch(String agentId, String queryDate, String RespCode, String RespMsg) {
        return BankReceiptSwitch.insertBankReceiptSwitch(getHqlContext(), agentId, queryDate, RespCode, RespMsg);
    }
    //-----------BNK_BANKRECEIPTSWITCH End!

    //-----------BNK_RECEIPT_RECORD start!
    public static String insertBankReceipt(String verison, String subBranchId,
                                           String agentId, String queryAcctId, String queryDate){
        return BankReceiptRecord.insertBankReceipt(getHqlContext(),verison,subBranchId,agentId,queryAcctId,queryDate);
    }
    //-----------BNK_RECEIPT_RECORD End!

    //---------- 电子回单
    public static String sftpSendFile(String version, String sftpPath, String localPath, String fileName, String targetPath) {
        return SFTPUtil.sftpSendFile(getHqlContext(),version,sftpPath,localPath,fileName,targetPath);
    }

    public static String checkBankReceipt(String version, String subBranchId, String agentId,
                                          String queryAcctId, String queryDate,String zipFilePath){
        return ElectronicReceipt.checkBankReceipt(getHqlContext(),version,subBranchId,agentId,queryAcctId,queryDate,zipFilePath);
    }
}

