package com.longtop.alsb.dao;

import com.longtop.Util.HelpCentre.DateUtil;
import com.longtop.alsb.entity.BnkPaymenttime;
import org.hibernate.Hibernate;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import java.util.List;

public class BankPaymentTimeDao {

    public List getPaymentTime(HibernateContext hiContext,String  paymentType) throws Exception {
        String hqlString = "from BnkPaymenttime where paymentType=? ";
        Object[] values = new Object[]{paymentType};
        Type[] types = new Type[]{StandardBasicTypes.STRING};
        List list = hiContext.get(hqlString,types,values);
        return list;
    }

    public String  addPaymentTime(HibernateContext hiContext,String bnakParameterm,String startTime,String endTime) throws Exception {
        BnkPaymenttime bnkPaymenttime = new BnkPaymenttime();
        bnkPaymenttime.setPaymenttype(bnakParameterm);
        bnkPaymenttime.setStarttime(startTime);
        bnkPaymenttime.setEndtime(endTime);
        bnkPaymenttime.setInputdate(DateUtil.getSqlTime());
        hiContext.add(bnkPaymenttime);
        return "M00001|接收成功";
    }
}
