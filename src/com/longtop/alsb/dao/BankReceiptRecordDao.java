package com.longtop.alsb.dao;

import com.longtop.Util.HelpCentre.DateUtil;
import com.longtop.alsb.entity.BnkReceiptRecord;
import org.hibernate.Hibernate;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import java.util.List;

public class BankReceiptRecordDao {

    public  String insertBankReceipt(HibernateContext hiContext,String verison, String subBranchId,
                                                       String agentId, String queryAcctId, String queryDate) throws Exception {
        BnkReceiptRecord bnkReceiptRecord=new BnkReceiptRecord();
        bnkReceiptRecord.setVersion(verison);
        bnkReceiptRecord.setSubbranchid(subBranchId);
        bnkReceiptRecord.setAgentid(agentId);
        bnkReceiptRecord.setQueryacctid(queryAcctId);
        bnkReceiptRecord.setQuerydate(queryDate);
        bnkReceiptRecord.setInputdate(DateUtil.getSqlTime());
        hiContext.add(bnkReceiptRecord);
        return "M0001|插入成功";
    }

    public List getBankReceipt(HibernateContext hiContext,String verison, String subBranchId, String agentId,
                               String queryAcctId, String queryDate, String newDate) throws Exception {
        String hqlString = "from BnkReceiptRecord where version=? and subBranchId=? and AgentId =?" +
                "and queryAcctId =? and queryDate=? and to_char(inputdate,'yyyymmdd')=? order by inputdate desc";
        Object[] values = new Object[]{verison,subBranchId,agentId,queryAcctId,queryDate,newDate};
        Type[] types = new Type[]{StandardBasicTypes.STRING,StandardBasicTypes.STRING,StandardBasicTypes.STRING,
                StandardBasicTypes.STRING,StandardBasicTypes.STRING,StandardBasicTypes.STRING};
        List list = hiContext.get(hqlString,types,values);
        return list;
    }
}
