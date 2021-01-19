package com.longtop.alsb.service.business;

import com.longtop.alsb.dao.BankAdminInforDao;
import com.longtop.alsb.dao.HibernateContext;

import java.util.List;

public class BankAdminInfor {

    /*
    校验该version内是否还含有证书信息
     */
    public static String confirmAdminInfor(HibernateContext hiContext, String version){
        try{
            List list =new BankAdminInforDao().confirmAdminInfor(hiContext,version);
            if(list!=null &&!list.isEmpty()){
                return "M";
            }else {
                return "E";
            }
        }catch(Exception e){
            e.printStackTrace();
            return "E9999|客户证书信息查询失败"+e.getMessage();
        }
    }

    public static String getAdminInfor(HibernateContext hiContext, String UserVersion, String CompanyNum, String Appendix, String UserName) {
        try {
            List list = new BankAdminInforDao().getAdminInfor(hiContext,UserVersion, CompanyNum, Appendix, UserName);
            if (list != null && !list.isEmpty()) {
                return "M";
            }else {
                return "E0001|客户证书信息查询不到";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "E9999|客户证书信息查询失败" + e.getMessage();
        }
    }

    /*
    校验证书和身份证信息
     */
    public static String judgeAdminInfor(HibernateContext hiContext, String MakeUserCert, String MakeUserID, String version,
                                  String subBranchId, String Appendix){
        try{
            List list=new BankAdminInforDao().judgeAdminInfor(hiContext,MakeUserCert, MakeUserID, version, subBranchId,Appendix);
            if(list==null || list.size()==0){
                return "E00001|未查询到该人员信息";
            }else{
                return "M";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "E9999|校验人员信息失败"+e.getMessage();
        }
    }

    public static String addAdminInfor(HibernateContext hiContext,String UserVersion, String CompanyNum,String Appendix,
                                String UserName, String IdCard,String CertNum){
        String returnTag=getAdminInfor(hiContext,UserVersion,CompanyNum,Appendix,UserName);
        if(returnTag.equals("E0001|客户证书信息查询不到")){
            return new BankAdminInforDao().addAdminInfor(hiContext,UserVersion,CompanyNum,Appendix,UserName,IdCard,CertNum);
        }else if(returnTag.equals("E9999|客户证书信息查询失败")){
            return  returnTag;
        }else{
            String delectReturnTag=new BankAdminInforDao().delectAdminInfor(hiContext,UserVersion,CompanyNum,Appendix,UserName);
            if(delectReturnTag.startsWith("M")){
                return new BankAdminInforDao().addAdminInfor(hiContext,UserVersion,CompanyNum,Appendix,UserName,IdCard,CertNum);
            }else{
                return delectReturnTag;
            }
        }
    }
    public static String delectAdminInfor(HibernateContext hiContext,String UserVersion, String CompanyNum,String Appendix, String UserName){
        String  returnTag=getAdminInfor(hiContext,UserVersion,CompanyNum,Appendix,UserName);
        if(returnTag.startsWith("E")){
            return  returnTag;
        }else{
            return new BankAdminInforDao().delectAdminInfor(hiContext,UserVersion,CompanyNum,Appendix,UserName);
        }
    }
}
