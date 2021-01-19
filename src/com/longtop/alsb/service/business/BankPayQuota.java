package com.longtop.alsb.service.business;

import com.longtop.alsb.dao.BankPayQuotaDao;
import com.longtop.alsb.dao.HibernateContext;
import com.longtop.alsb.entity.BnkPayquota;

import java.util.List;

public class BankPayQuota {

    public static String getPayQuota(HibernateContext hiContext,String acctidType){
        String amount="";
       try {
           List list =new BankPayQuotaDao().getPayQuota(hiContext,acctidType);
           amount =(list == null || list.size() == 0) ? "":((BnkPayquota) list.get(0)).getAmount();
       } catch (Exception e) {
           e.printStackTrace();
       }
        return  amount;
    }
}
