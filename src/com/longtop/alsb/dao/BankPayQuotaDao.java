package com.longtop.alsb.dao;

import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import java.util.List;

public class BankPayQuotaDao {

    public List getPayQuota(HibernateContext hiContext,String acctidType) throws Exception {
        String hqlString ="from BnkPayquota where acctidType=? ";
        Object[] values = new Object[]{acctidType};
        Type[] types = new Type[]{StandardBasicTypes.STRING};
        List list = hiContext.get(hqlString,types,values);
        return list;
    }
}
