package com.longtop.alsb.dao;

import com.longtop.Util.HelpCentre.DateUtil;
import com.longtop.alsb.entity.BnkErpInfor;
import org.hibernate.Hibernate;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import java.util.List;

public class BankErpInforDao {

    public List getErpInforCount(HibernateContext hiContext, String txserial)throws Exception{
        String hqlString ="select count(txserial) from BnkErpInfor where txserial=?";
        Object[] values=new Object[]{txserial};
        Type[] types=new Type[]{StandardBasicTypes.STRING};
        List list = hiContext.get(hqlString,types,values);
        return list;
    }

    public String insertERPInfor(HibernateContext hiContext,String version,
                                        String subbranchid, String txserial, String txmoment, String agentid,
                                        String reccount, String payresultIsWait,String tranxml) throws Exception{
        BnkErpInfor bnkErpInfor = new BnkErpInfor();
        bnkErpInfor.setVersion(version);
        bnkErpInfor.setSubbranchid(subbranchid);
        bnkErpInfor.setTxserial(txserial);
        bnkErpInfor.setTxmoment(txmoment);
        bnkErpInfor.setAgentid(agentid);
        bnkErpInfor.setReccount(reccount);
        bnkErpInfor.setInputdate(DateUtil.getSqlTime());
        bnkErpInfor.setPayresultiswait(payresultIsWait);
        bnkErpInfor.setTranxml(tranxml);
        hiContext.add(bnkErpInfor);
        return "M00001|接收成功";
    }
}
