package com.longtop.alsb.dao;

import com.longtop.alsb.entity.BnkAdmininfor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import java.util.List;

public class BankAdminInforDao {

    public List confirmAdminInfor(HibernateContext hiContext,String version) throws Exception {
        String hqlString ="from BnkAdmininfor where UserVersion=?";
        Object[] values=new Object[]{version.trim()};
        Type[] types=new Type[]{StandardBasicTypes.STRING};
        List list = hiContext.get(hqlString,types,values);
        return list;
    }

    public List getAdminInfor(HibernateContext hiContext,String UserVersion, String CompanyNum,String Appendix, String UserName) throws Exception {
        String hqlString= "from BnkAdmininfor where UserVersion =? and CompanyNum=? and Appendix=? and UserName=?";
        Object[] values = new Object[]{UserVersion,CompanyNum,Appendix,UserName};
        Type[] types = new Type[]{StandardBasicTypes.STRING,StandardBasicTypes.STRING,StandardBasicTypes.STRING,
                StandardBasicTypes.STRING};
        List list = hiContext.get(hqlString,types,values);
        return list;
    }

    public List judgeAdminInfor(HibernateContext hiContext,String MakeUserCert, String MakeUserID,
                                String version, String subBranchId, String Appendix) throws Exception {
        String hqlString = "from BnkAdmininfor where  CertNum=? and IdCard=? and UserVersion=? and CompanyNum=? and Appendix=?";
        Object[] values = new Object[]{MakeUserCert,MakeUserID,version,subBranchId,Appendix};
        Type[] types = new Type[]{StandardBasicTypes.STRING,StandardBasicTypes.STRING,StandardBasicTypes.STRING,
                StandardBasicTypes.STRING,StandardBasicTypes.STRING};
        List list = hiContext.get( hqlString, types, values);
        return list;
    }

    public String addAdminInfor(HibernateContext hiContext,String UserVersion, String CompanyNum,String Appendix, String UserName, String IdCard,String CertNum){
        try{
            BnkAdmininfor bnkAdmininfor = new BnkAdmininfor();
            bnkAdmininfor.setUserversion(UserVersion);
            bnkAdmininfor.setCompanynum(CompanyNum);
            bnkAdmininfor.setAppendix(Appendix);
            bnkAdmininfor.setUsername(UserName);
            bnkAdmininfor.setIdcard(IdCard);
            bnkAdmininfor.setCertnum(CertNum);
            hiContext.add(bnkAdmininfor);
            return "M0001|插入成功";
        }catch(Exception e){
            return "E9999|账户信息插入失败"+e.getMessage();
        }
    }

    public String delectAdminInfor(HibernateContext hiContext,String UserVersion, String CompanyNum,String Appendix, String UserName){
        try{
            String hqlString="delete BnkAdmininfor where  UserVersion= ? and  CompanyNum= ? and Appendix=? and UserName=?";
            Object[] values=new Object[]{UserVersion,CompanyNum,Appendix,UserName};
            Type[] types=new Type[]{StandardBasicTypes.STRING,StandardBasicTypes.STRING,StandardBasicTypes.STRING,StandardBasicTypes.STRING};
            hiContext.update(hqlString,types,values);
            return "M0001|客户证书信息删除成功";
        }catch(Exception e){
            return "E9999|客户证书信息删除失败";
        }
    }
}
