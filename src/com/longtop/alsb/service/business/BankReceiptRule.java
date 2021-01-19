package com.longtop.alsb.service.business;

import com.longtop.alsb.dao.BankReceiptRuleDao;
import com.longtop.alsb.dao.HibernateContext;

import java.util.List;

public class BankReceiptRule {

    public List getReceiptInfo(HibernateContext hiContext, String agentid) throws Exception {
            return new BankReceiptRuleDao().getReceiptRule(hiContext,agentid);
    }
}
