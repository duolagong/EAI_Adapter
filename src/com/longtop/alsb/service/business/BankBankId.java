package com.longtop.alsb.service.business;

import com.longtop.alsb.dao.BankBankIdDao;
import com.longtop.alsb.dao.HibernateContext;
import com.longtop.alsb.entity.BnkBankid;

import java.util.List;

public class BankBankId {

    public static String getBankId(HibernateContext hibernate,String id){
        String bankId="";
        try {
            List list = new BankBankIdDao().getBankId(hibernate,id);
            bankId= (list == null || list.size() == 0) ? "" : ((BnkBankid) list.get(0)).getBank().trim();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return bankId;
        }
    }
}
