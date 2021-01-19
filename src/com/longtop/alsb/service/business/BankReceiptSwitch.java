package com.longtop.alsb.service.business;

import com.longtop.alsb.dao.BankReceiptSwitchDao;
import com.longtop.alsb.dao.HibernateContext;

public class BankReceiptSwitch {

    public static String insertBankReceiptSwitch(HibernateContext hiContext, String agentId, String queryDate, String respCode, String respMsg) {
        try {
            return new BankReceiptSwitchDao().insertBankReceiptSwitch(hiContext,agentId,queryDate,respCode,respMsg);
        }catch (Exception e){
            e.printStackTrace();
            return "E9999|失败"+e.getMessage();
        }
    }

}

