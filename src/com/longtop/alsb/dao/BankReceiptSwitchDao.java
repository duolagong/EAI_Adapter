package com.longtop.alsb.dao;

import com.longtop.Util.HelpCentre.DateUtil;
import com.longtop.alsb.entity.BnkBankreceiptswitch;

public class BankReceiptSwitchDao {

    public String insertBankReceiptSwitch(HibernateContext hiContext, String agentId, String queryDate, String respCode,
                                          String respMsg) throws Exception {
        BnkBankreceiptswitch bankReceiptSwitch = new BnkBankreceiptswitch();
        bankReceiptSwitch.setAgentid(agentId);
        bankReceiptSwitch.setQuerydate(queryDate);
        bankReceiptSwitch.setRespcode(respCode);
        bankReceiptSwitch.setRespmsg(respMsg);
        bankReceiptSwitch.setInputdate(DateUtil.getSqlTime());
        hiContext.add(bankReceiptSwitch);
        return "M0001|插入成功";
    }
}
