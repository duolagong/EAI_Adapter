package com.longtop.alsb.service.business;

import com.longtop.Util.HelpCentre.StringUtil;
import com.longtop.alsb.dao.BankGSBHDao;
import com.longtop.alsb.dao.BankPaymentTimeDao;
import com.longtop.alsb.dao.HibernateContext;
import com.longtop.alsb.entity.BnkGsbh;

import java.util.List;

public class BankGSBH {

    public static String getTargetAddress(HibernateContext hiContext, String version) {
        String Address = "";
        try {
            List list = new BankGSBHDao().checkVersion(hiContext, version);
            Address = (list == null || list.size() == 0) ? "" : ((BnkGsbh) list.get(0)).getfMbdz();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Address;
    }

    public static String getBudgetAddress(HibernateContext hiContext, String version) {
        String Address = "";
        try {
            List list = new BankGSBHDao().checkVersion(hiContext, version);
            Address = (list == null || list.size() == 0) ? "" : ((BnkGsbh) list.get(0)).getfBudgetSite();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Address;
    }

    public static String checkVersion(HibernateContext hiContext, String Version) {
        if (Version != null && !Version.equals("")) {
            try {
                List list = new BankGSBHDao().checkVersion(hiContext, Version);
                if (list == null || list.size() == 0) {
                    return "E0001|系统信息查询不到";
                } else {
                    return "M";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "E0001|数据异常:" + e.getMessage();
            }
        } else {
            return "E9999|报文中未填写系统来源信息";
        }
    }

    public static  String checkSwitch(HibernateContext hiContext, String version, String txCode){
        try {
            boolean paySign=false;
            List list = new BankGSBHDao().checkVersion(hiContext, version);
            String aSwitch = ((BnkGsbh) list.get(0)).getfSwitch();
            if (StringUtil.isEmpty(aSwitch) && aSwitch.equals("Y")){
                paySign= true;
            }
            if("2000".equals(txCode) && paySign){
                return "M";
            }else if("1000".equals(txCode) && paySign==false){
                return "M";
            }else {
                return "E0001|贵单位没有使用本交易类型的付款权限!";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "E0001|数据异常:" + e.getMessage();
        }
    }

    public static String updateBudgetParameter(HibernateContext hiContext, String version, String autoMonitorInd,
                                               String budgetTargetAdd, String budgetTargetUser, String budgetTargetPass) {
        return new BankGSBHDao().updateBudgetParameter(hiContext, version, autoMonitorInd, budgetTargetAdd, budgetTargetUser, budgetTargetPass);
    }

    public static String updateSystemParameter(HibernateContext hiContext,String version,String sftpIp,String sftpPost,String user,String password){
        return new BankGSBHDao().updateSystemParameter(hiContext,version,sftpIp, sftpPost,user,password);
    }

    public static String updateSystemParameter(HibernateContext hiContext, String subBranchId, String agentId, String modifyType, String modifyInfor, String appendix,
                                               String modifyInforAdd, String appendixAdd, String targetAddress) {
        if (modifyType.equals("5")) {
            String returnTag = checkVersion(hiContext, modifyInfor);
            if (returnTag.equals("E0001|系统信息查询不到")) {
                if (targetAddress == null || targetAddress.equals("")) {
                    return "E9999|目标地址未填写";
                } else {
                    return new BankGSBHDao().insertSystemParameter(hiContext, modifyInfor, appendix, targetAddress);
                }
            } else if (returnTag.equals("M")) {
                if (targetAddress == null || targetAddress.equals("")) {
                    return "E9999|目标地址未填写";
                } else {
                    String returnDelectTag = new BankGSBHDao().delectSystemParameter(hiContext, modifyInfor);
                    if (returnDelectTag.startsWith("E")) {
                        return returnDelectTag;
                    } else {
                        return new BankGSBHDao().insertSystemParameter(hiContext, modifyInfor, appendix, targetAddress);
                    }
                }
            } else {
                return returnTag;
            }
        } else if (modifyType.equals("6")) {
            String returnTag = checkVersion(hiContext, modifyInfor);
            if (returnTag.equals("M")) {
                String returnAcctPower = new BankAcctPower().confirmAcctPower(hiContext, modifyInfor);
                String returnAdminInfor = new BankAdminInfor().confirmAdminInfor(hiContext, modifyInfor);
                if (returnAcctPower.equals("E") && returnAdminInfor.equals("E")) {
                    return new BankGSBHDao().delectSystemParameter(hiContext, modifyInfor);
                } else {
                    return "E0001|" + modifyInfor + "下已创建用户，不允许删除" + modifyInfor;
                }
            } else {
                return returnTag;
            }
        } else {
            return updateSystemParameter(hiContext, subBranchId, agentId, modifyType, modifyInfor, appendix, modifyInforAdd, appendixAdd);
        }
    }

    public static String updateSystemParameter(HibernateContext hiContext, String subBranchId, String agentId, String modifyType,
                                               String modifyInfor, String appendix, String modifyInforAdd, String appendixAdd) {
        String bnakParameter = "";
        if (modifyType.equals("1") && subBranchId.equals("6666")) {
            bnakParameter = "Total";
        } else if (modifyType.equals("3")) {
            if (agentId.equals("Z01") && subBranchId.equals("6666")) {
                bnakParameter = "Total";
            } else if(agentId.equals("Z01") && subBranchId.equals("8888")){
                bnakParameter = "";
            }else {
                bnakParameter = BankBankId.getBankId(hiContext, agentId);
                if (!StringUtil.isEmpty(bnakParameter)) return "E0001|未维护AgentId的银行信息";
            }
            try {
                List list = new BankPaymentTimeDao().getPaymentTime(hiContext, bnakParameter+"inBank");
                if (list == null || list.size() == 0) {
                    new BankPaymentTimeDao().addPaymentTime(hiContext, bnakParameter+"inBank", modifyInfor, appendix);
                }
                list = new BankPaymentTimeDao().getPaymentTime(hiContext, bnakParameter+"outBank");
                if (list == null || list.size() == 0) {
                    return new BankPaymentTimeDao().addPaymentTime(hiContext,bnakParameter+"outBank", modifyInfor, appendix);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "E9999|异常" + e.getMessage();
            }
        }
        String isSuccess = new BankGSBHDao().updateSystemParameter(hiContext, bnakParameter, modifyType, modifyInfor, appendix, modifyInforAdd, appendixAdd);
        return isSuccess;
    }

    public static String delectSystemParameter(HibernateContext hiContext, String modifyInfor) {
        return new BankGSBHDao().delectSystemParameter(hiContext, modifyInfor);
    }
}
