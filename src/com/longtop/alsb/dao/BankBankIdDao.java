package com.longtop.alsb.dao;

import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import java.util.List;

public class BankBankIdDao {
    public List getBankId(HibernateContext hiContext,String id) throws Exception {
        String hqlString = "from BnkBankid where id=?";
        Object[] values = new Object[]{id};
        Type[] types=new Type[]{StandardBasicTypes.STRING};
        return hiContext.get(hqlString,types,values);
    }
}
