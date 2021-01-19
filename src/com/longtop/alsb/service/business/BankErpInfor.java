package com.longtop.alsb.service.business;

import com.longtop.Util.HelpCentre.StringUtil;
import com.longtop.alsb.dao.BankErpInforDao;
import com.longtop.alsb.dao.HibernateContext;
import org.apache.log4j.Logger;

import java.util.List;

public class BankErpInfor {

    private static Logger logger = Logger.getLogger(BankErpInfor.class);

    public static int getErpInforCount(HibernateContext hiContext, String txserial) throws Exception {
        List list = new BankErpInforDao().getErpInforCount(hiContext, txserial);
        String count = list.get(0).toString();
        return Integer.parseInt(count);
    }

    public static String insertERPInfor(HibernateContext hiContext, String version,
                                        String subbranchid, String txserial, String txmoment, String agentid,
                                        String reccount, String payresultIsWait, String tranxml) {
        String InforMsg="";
        try {
            if(!StringUtil.isEmpty(txserial)) return "E0001|txserial上送为空";
            if(!StringUtil.isEmpty(subbranchid)) return "E0001|subbranchid上送为空";
            if(!StringUtil.isEmpty(version)) return "E0001|version上送为空";
            if(!StringUtil.isEmpty(txmoment)) return "E0001|txmoment上送为空";
            if(!StringUtil.isEmpty(agentid)) return "E0001|agentid上送为空";
            InforMsg=getErpInforCount(hiContext, txserial) == 0 ? new BankErpInforDao().insertERPInfor(hiContext, version, subbranchid,
                    txserial, txmoment, agentid, reccount, payresultIsWait, tranxml) : "P00000|报文序列号重复";
        } catch (Exception e) {
                logger.error("异常数据" + txserial);
                InforMsg = "E9999|" + e.getMessage();
                e.printStackTrace();
            }
        return InforMsg;
        }
}
