package com.longtop.alsb.dao;

import org.hibernate.Hibernate;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import java.util.List;

public class BankAcctSpecialDao {

    public List getSpecialAcctId(HibernateContext hiContext,String subBranchId) throws Exception {
        String hqlString = "from BnkAcctSpecial where  subBranchId=?";
        Object[] values = new Object[]{subBranchId};
        Type[] types = new Type[]{StandardBasicTypes.STRING};
        List list = hiContext.get(hqlString, types, values);
        return list;
    }
}
