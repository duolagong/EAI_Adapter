package com.longtop.alsb.service.business;

import com.longtop.Util.HelpCentre.StringUtil;
import com.longtop.alsb.dao.BankPaymentTimeDao;
import com.longtop.alsb.dao.HibernateContext;
import com.longtop.alsb.entity.BnkPaymenttime;
import org.apache.log4j.Logger;

import java.util.List;

public class BankPaymentTime {
    private static Logger logger =Logger.getLogger(BankPaymentTime.class);

    public static String getPaymentTime(HibernateContext hiContext,String agentId){
        List list = null;
        try{
            String bankType="";
            if("Z01".equals(agentId)){
                bankType="Total";
            }else if("".equals(agentId)){
                bankType="";
            }else {
                bankType = BankBankId.getBankId(hiContext, agentId);
                if(!StringUtil.isEmpty(bankType)) return "250000-250000|250000-250000";//由于银行数据未维护，拟一条错误数据返回
            }
            list = new BankPaymentTimeDao().getPaymentTime(hiContext,bankType+"inBank");
            String insideStartTime = (list == null || list.size() == 0) ? "250000": ((BnkPaymenttime) list.get(0)).getStarttime();
            String insideEndTime = (list == null || list.size() == 0) ? "250000": ((BnkPaymenttime) list.get(0)).getEndtime();
            list = new BankPaymentTimeDao().getPaymentTime(hiContext,bankType+"outBank");
            String externalStartTime = (list == null || list.size() == 0) ? "250000": ((BnkPaymenttime) list.get(0)).getStarttime();
            String externalEndTime = (list == null || list.size() == 0) ? "250000": ((BnkPaymenttime) list.get(0)).getEndtime();
            return insideStartTime+"-"+insideEndTime + "|" + externalStartTime+"-"+externalEndTime;
        }catch (Exception e){
            e.printStackTrace();
            return "250000-250000|250000-250000";
        }
    }

}
