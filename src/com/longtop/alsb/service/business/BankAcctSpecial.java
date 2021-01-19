package com.longtop.alsb.service.business;

import com.longtop.alsb.dao.BankAcctSpecialDao;
import com.longtop.alsb.dao.HibernateContext;

import java.util.List;

public class BankAcctSpecial {

    public static String getSpecialAcctId(HibernateContext hiContext,String subBranchId){
        String SpecialInfor="";
        try {
            List list =new BankAcctSpecialDao().getSpecialAcctId(hiContext,subBranchId);
            if(list==null || list.size()==0){
                SpecialInfor="E0001无特殊权限";
            }else{
                SpecialInfor= "M";
            }
        }catch (Exception e){
            e.printStackTrace();
            SpecialInfor="E9999"+e.getMessage();
        }
        return SpecialInfor;
    }
}
