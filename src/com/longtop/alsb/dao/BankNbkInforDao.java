package com.longtop.alsb.dao;

import com.longtop.Util.HelpCentre.DateUtil;
import com.longtop.alsb.entity.BnkErpInfor;
import com.longtop.alsb.entity.BnkNbkInfor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import java.util.List;

public class BankNbkInforDao {

    public List getNbkInforCount(HibernateContext hiContext, String txserial)throws Exception{
        String hqlString ="select count(txserial) from BnkNbkInfor where txserial=?";
        Object[] values=new Object[]{txserial};
        Type[] types=new Type[]{StandardBasicTypes.STRING};
        List list = hiContext.get(hqlString,types,values);
        return list;
    }

    public String insertNbkInfor(HibernateContext hiContext,String version,
                               String subbranchid, String txserial, String txmoment, String agentid,
                               String reccount, String payresultIsWait,String tranxml) throws Exception{
        BnkNbkInfor bnkNbkInfor = new BnkNbkInfor();
        bnkNbkInfor.setVersion(version);
        bnkNbkInfor.setSubbranchid(subbranchid);
        bnkNbkInfor.setTxserial(txserial);
        bnkNbkInfor.setTxmoment(txmoment);
        bnkNbkInfor.setAgentid(agentid);
        bnkNbkInfor.setReccount(reccount);
        bnkNbkInfor.setInputdate(DateUtil.getSqlTime());
        bnkNbkInfor.setPayresultiswait(payresultIsWait);
        bnkNbkInfor.setTranxml(tranxml);
        hiContext.add(bnkNbkInfor);
        return "M00001|接收成功";
    }
}

