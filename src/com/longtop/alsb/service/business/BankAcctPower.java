package com.longtop.alsb.service.business;

import com.longtop.alsb.dao.BankAcctPowerDao;
import com.longtop.alsb.dao.HibernateContext;
import com.longtop.alsb.entity.BnkAcctpower;

import java.util.List;

public class BankAcctPower {

    public static String confirmAcctPower(HibernateContext hiContext,String version){
        try{
            List list = new BankAcctPowerDao().confirmAcctPower(hiContext,version);
            return (list!=null && !list.isEmpty()) ? "M" : "E";
        } catch (Exception e) {
            e.printStackTrace();
            return "E9999|系统信息查询失败"+e.getMessage();
        }
    }

    public static String getAcctSubBranchId(HibernateContext hiContext,String agentId,String acctId,String version){
        try {
            List<BnkAcctpower> acctpowersList = new BankAcctPowerDao().getAcctidPower(hiContext, agentId, acctId, version);
            if(acctpowersList!=null && !acctpowersList.isEmpty()){
                String subbranchid = acctpowersList.get(0).getSubbranchid();
                return (subbranchid !=null && !"".equals(subbranchid)) ? "M|"+subbranchid : "E0001|付款账户未维护所属单位";
            }else {
                return "E0001|系统未维护该付款账户";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "E9999|异常"+e.getMessage();
        }
    }

    public static String getAcctPower(HibernateContext hiContext,String agentId,String acctId,String version){
        List list = null;
        String returnMsg="";
        try{
            list = new BankAcctPowerDao().getAcctPower(hiContext,agentId, acctId,version);
            returnMsg =(list!=null && !list.isEmpty()) ? "M" : "E0001|账户信息查询不到";
        } catch (Exception e) {
            e.printStackTrace();
            returnMsg="E9999|异常"+e.getMessage();
        }
        return returnMsg;
    }

    public static String addAcctPower(HibernateContext hiContext,String agentId,String acctId,String queryPower,String paypower,
                               String billpower,String version,String subbranchid){
        String returnTag=getAcctPower(hiContext,agentId,acctId,version);
        if(returnTag.equals("E0001|账户信息查询不到")){
            return  new BankAcctPowerDao().addAcctPower(hiContext,agentId,acctId,queryPower,paypower,billpower,version,subbranchid);
        }else if(returnTag.equals("E9999|账户信息查询失败")){
            return  returnTag;
        }else{
            return  "E0002|账户已存在";
        }
    }

    public static String updateAcctPower(HibernateContext hiContext,String agentId,String acctId,String queryPower,String paypower,
                                  String billpower,String version,String subbranchid) {
        String returnTag = getAcctPower(hiContext,agentId,acctId,version);
        if (returnTag.startsWith("E")) {
            return returnTag;
        } else {
            return new BankAcctPowerDao().updateAcctPower(hiContext,agentId,acctId,queryPower,paypower,billpower,version,subbranchid);
        }
    }

    public static String delectAcctPower(HibernateContext hiContext,String agentId,String acctId,String version){
        String returnTag=getAcctPower(hiContext,agentId,acctId,version);
        if(returnTag.startsWith("E")){
            return  returnTag;
        }else{
            return new BankAcctPowerDao().delectAcctPower(hiContext,agentId,acctId,version);
        }
    }

    public static String getAcctIdPower(HibernateContext hiContext,String bankAgentid, String acctId,
                                        String powerNum, String version) {
        List list = null;
        String personLevel = "";
        if (bankAgentid==null || bankAgentid.equals("")) {
            return "E0001|AgentId字段为空";
        } else if (acctId==null || acctId.equals("")) {
            return "E0001|AcctId字段为空";
        } else if (version==null || version.equals("")) {
            return "E0001|Version字段为空";
        } else if (powerNum==null || powerNum.equals("")){
            return "E0001|TxCode字段为空";
        }else{
            if("1000".equals(powerNum) || "2000".equals(powerNum)){
                powerNum="1";
            }else if("1030".equals(powerNum)||"1032".equals(powerNum)||"1033".equals(powerNum)
                    ||"1034".equals(powerNum)||powerNum.equals("1036")){
                powerNum="2";
            }else if(powerNum.equals("DP")){
                powerNum="3";
            }else{
                return "E0001|TxCode交易类型上送有误";
            }
            try{
                list = new BankAcctPowerDao().getAcctidPower(hiContext,bankAgentid, acctId,version);
            }catch (Exception e){
                e.printStackTrace();
                return "E0001|上送数据异常:"+e.getMessage();
            }
            if (list == null || list.size() == 0) {
                return "E0001|系统未维护该账户";
            } else {
                if (powerNum.equals("1")) {
                    personLevel = (list == null || list.size() == 0) ? "" : ((BnkAcctpower) list.get(0)).getPaypower().trim();
                    if (personLevel.equals("1")) {
                        return "M0001";
                    } else {
                        return "E0008|账户无支付权限,请求被拒绝";
                    }
                } else if (powerNum.equals("2")) {
                    personLevel = (list == null || list.size() == 0) ? "" : ((BnkAcctpower) list.get(0)).getQuerypower().trim();
                    if (personLevel.equals("1")) {
                        return "M0001";
                    } else {
                        return "E0008|账户无查询权限,请求被拒绝";
                    }
                } else if (powerNum.equals("3")) {
                    personLevel = (list == null || list.size() == 0) ? "" : ((BnkAcctpower) list.get(0)).getBillpower().trim();
                    if (personLevel.equals("1")) {
                        return "M0001";
                    } else {
                        return "E0008|账户电票业务权限,请求被拒绝";
                    }
                } else {
                    return "E0008|上送交易号异常,请求被拒绝";
                }
            }
        }
    }
}
