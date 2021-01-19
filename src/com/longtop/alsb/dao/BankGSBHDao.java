package com.longtop.alsb.dao;

import com.longtop.Util.HelpCentre.StringUtil;
import com.longtop.alsb.entity.BnkGsbh;
import com.longtop.alsb.service.business.BankGSBH;
import org.hibernate.Hibernate;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import sun.misc.BASE64Encoder;

import java.util.ArrayList;
import java.util.List;

public class BankGSBHDao {

    public List checkVersion(HibernateContext hiContext,String version) throws Exception {
        String hqlString ="from BnkGsbh where F_QYBH=? ";
        Object[] values = new Object[]{version};
        Type[] types = new Type[]{ StandardBasicTypes.STRING };
        List list = hiContext.get(hqlString,types,values);
        return list;
    }

    public String updateBudgetParameter(HibernateContext hiContext, String version, String autoMonitorInd,
                                        String budgetTargetAdd, String budgetTargetUser, String budgetTargetPass) {
        List<Object> list = new ArrayList<Object>();
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("update BnkGsbh set f_budget_site=?");
            list.add(budgetTargetAdd);
            if(StringUtil.isEmpty(autoMonitorInd)){
                stringBuffer.append(", f_switch=?");
                list.add(autoMonitorInd);
            }
            if(StringUtil.isEmpty(budgetTargetUser) && StringUtil.isEmpty(budgetTargetPass)){
                stringBuffer.append(", f_budget_user=? ,f_budget_password=?");
                budgetTargetUser=new BASE64Encoder().encode(budgetTargetUser.getBytes("GBK"));
                budgetTargetPass=new BASE64Encoder().encode(budgetTargetPass.getBytes("GBK"));
                list.add(budgetTargetUser);
                list.add(budgetTargetPass);
            }
            list.add(version);
            String hqlString=stringBuffer.append("where f_qybh=?").toString();
            Object[] values = list.toArray(new Object[list.size()]);
            Type[] types=new Type[values.length];
            for (int i=0;i<values.length;i++){
                types[i]=StandardBasicTypes.STRING;
            }
            if(hiContext.update(hqlString, types, values)){
                return "M0001|信息插入成功";
            }else {
                return "E0009|信息插入失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "E9999|信息插入异常"+e.getMessage();
        }
    }

    public String updateSystemParameter(HibernateContext hiContext,String version,String sftpIp, String sftpPost,String user, String password) {
        try {
            String hqlString = "update BnkGsbh set f_sftp_ip= ?,f_sftp_post=?,f_user=?,f_password=? where  f_qybh=?";
            if(StringUtil.isEmpty(user) && StringUtil.isEmpty(password)){
                String userBase64=new BASE64Encoder().encode(user.getBytes("GBK"));
                String passwordBase64=new BASE64Encoder().encode(password.getBytes("GBK"));
                Object[] values = new Object[]{sftpIp,sftpPost,userBase64,passwordBase64,version};
                Type[] types = new Type[]{StandardBasicTypes.STRING,StandardBasicTypes.STRING,StandardBasicTypes.STRING,
                        StandardBasicTypes.STRING,StandardBasicTypes.STRING};
                if(hiContext.update(hqlString, types, values)){
                    return "M0001|信息插入成功";
                }else {
                    return "E0009|信息插入失败";
                }
            }else {
                return "E0010|SFTP用户名或密码为空";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "E9999|信息插入异常"+e.getMessage();
        }
    }

    public String insertSystemParameter(HibernateContext hiContext,String modifyInfor,String appendix,String targetAddress){
        try {
            BnkGsbh bnkGSBH = new BnkGsbh();
            bnkGSBH.setfQybh(modifyInfor);
            bnkGSBH.setfQymc(appendix);
            bnkGSBH.setfMbdz(targetAddress);
            hiContext.add(bnkGSBH);
            return "M0001|插入成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "E9999|信息插入异常"+e.getMessage();
        }
    }

    public String updateSystemParameter(HibernateContext hiContext,String bnakParameter,String modifyType,String modifyInfor,String appendix,
                                        String modifyInforAdd,String appendixAdd){
        try{
            String hqlString = "";
            String hqlStringAdd="";
            Object[] values = null ;
            Object[] valuesAdd = null ;
            Type[] types = null ;
            Type[] typesAdd = null ;
            if(modifyType.equals("1")){
                //同名
                hqlString = "update BnkPayquota set amount= ?, inputdate=sysdate where  acctidType='ideName"+bnakParameter+"' ";
                values = new Object[]{modifyInfor};
                types = new Type[]{StandardBasicTypes.STRING};
                //非同名
                hqlStringAdd = "update BnkPayquota set amount= ?, inputdate=sysdate where  acctidType='difName"+bnakParameter+"' ";
                valuesAdd = new Object[]{modifyInforAdd};
                typesAdd = new Type[]{StandardBasicTypes.STRING};
            }else if(modifyType.equals("3")){
                //同行
                hqlString = "update BnkPaymenttime set startTime= ?,endTime= ?, inputdate=sysdate where  paymentType= '"+bnakParameter+"inBank' ";
                values = new Object[]{modifyInfor,appendix};
                types = new Type[]{StandardBasicTypes.STRING,StandardBasicTypes.STRING};
                //跨行
                hqlStringAdd ="update BnkPaymenttime set startTime= ?,endTime= ?,inputdate=sysdate where  paymentType= '"+bnakParameter+"outBank' ";
                valuesAdd = new Object[]{modifyInforAdd,appendixAdd};
                typesAdd = new Type[]{StandardBasicTypes.STRING,StandardBasicTypes.STRING};
            }
            hiContext.update(hqlString, types, values);
            hiContext.update(hqlStringAdd, typesAdd, valuesAdd);
            return "M0001|修改系统信息成功";
        }catch(Exception e){
            e.printStackTrace();
            return "E9999|账户系统修改异常"+e.getMessage();
        }
    }

    public String delectSystemParameter(HibernateContext hiContext,String modifyInfor){
        try{
            String hqlString ="delete BnkGsbh where  F_QYBH= ?";
            Object[] values=new Object[]{modifyInfor};
            Type[] types=new Type[]{StandardBasicTypes.STRING};
            if(hiContext.update(hqlString,types,values)){
                return "M0001|目标系统来源删除成功";
            }else {
                return "E0001|目标系统来源删除失败";
            }
        }catch(Exception e){
            return "E9999|目标系统来源删除异常"+e.getMessage();
        }
    }

}
