package com.longtop.alsb.dao;

import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import java.util.List;

public class BankReceiptRuleDao {

    public List getReceiptRule(HibernateContext hiContext,String agentid) throws Exception {
        String hqlString="from BnkReceiptRule where agentid=?";
        Object[] values = new Object[]{agentid.trim()};
        Type[] types = new Type[]{StandardBasicTypes.STRING};
        return hiContext.get(hqlString,types,values);
    }
}
