package com.longtop.alsb.service.business;

import com.longtop.alsb.dao.BankReceiptRecordDao;
import com.longtop.alsb.dao.HibernateContext;

public class BankReceiptRecord {

    public static String insertBankReceipt(HibernateContext hiContext,String verison, String subBranchId,
                                           String agentId, String queryAcctId, String queryDate) {
        try {
            return new BankReceiptRecordDao().insertBankReceipt(hiContext,verison,subBranchId,agentId,queryAcctId,queryDate);
        } catch (Exception e) {
            e.printStackTrace();
            return "E9999|失败"+e.getMessage();
        }
    }
}
