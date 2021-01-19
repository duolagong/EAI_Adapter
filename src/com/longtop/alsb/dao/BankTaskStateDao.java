package com.longtop.alsb.dao;

import com.longtop.Util.HelpCentre.DateUtil;
import com.longtop.alsb.entity.BnkTaskState;

public class BankTaskStateDao {

    public void  insertTaskStateXml(HibernateContext hiContext, String ordernum, String version, String subBranchId,
                                     String respCode,String respMsg) throws Exception {
        BnkTaskState bnkTaskState = new BnkTaskState();
        bnkTaskState.setOrdernum(ordernum);
        bnkTaskState.setVersion(version);
        bnkTaskState.setSubbranchid(subBranchId);
        bnkTaskState.setRespcode(respCode);
        bnkTaskState.setRespmsg(respMsg);
        bnkTaskState.setInputdate(new DateUtil().getSqlTime());
        hiContext.add(bnkTaskState);
    }
}
