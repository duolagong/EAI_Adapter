package com.longtop.alsb.dao;

import com.longtop.Util.HelpCentre.DateUtil;
import com.longtop.alsb.entity.BnkAcctpower;
import com.longtop.alsb.service.business.BankAcctPower;
import org.hibernate.Hibernate;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import java.util.List;

public class BankAcctPowerDao {

    public List confirmAcctPower(HibernateContext hiContext,String version) throws Exception {
        String hqlString = "from BnkAcctpower where version=?";
        Object[] values = new Object[]{version};
        Type[] types = new Type[]{StandardBasicTypes.STRING};
        List list = hiContext.get(hqlString,types,values);
        return list;
    }

    public List getAcctPower(HibernateContext hiContext,String agentId,String acctId,String version) throws Exception {
        String hqlString = "from BnkAcctpower where agentId =? and acctId =? and version=?";
        Object[] values = new Object[]{agentId,acctId,version};
        Type[] types = new Type[]{StandardBasicTypes.STRING,StandardBasicTypes.STRING,StandardBasicTypes.STRING};
        List list = hiContext.get(hqlString,types,values);
        return list;
    }

    public List getAcctidPower(HibernateContext hiContext,String bankAgentId, String acctId, String version) throws Exception {
        String hqlString ="from BnkAcctpower  where  acctid =? and version =? and  agentid like '%"+bankAgentId+"'";
        Object[] values = new Object[]{acctId,version};
        Type[] types = new Type[]{StandardBasicTypes.STRING,StandardBasicTypes.STRING};
        List list = hiContext.get(hqlString,types,values);
        return list;
    }

    public String updateAcctPower(HibernateContext hiContext,String agentId,String acctId,String queryPower,String paypower,
                                  String billpower,String version ,String subbranchid){
        try{
            String hqlString = "update BnkAcctpower set querypower= ?, paypower= ?,billpower= ?,subbranchid= ? where agentid= ? " +
                    "and acctid= ? and version= ?";
            Object[] values = new Object[]{queryPower,paypower,billpower,subbranchid,agentId,acctId,version};
            Type[] types = new Type[]{StandardBasicTypes.STRING,StandardBasicTypes.STRING,StandardBasicTypes.STRING,
                    StandardBasicTypes.STRING,StandardBasicTypes.STRING,StandardBasicTypes.STRING,StandardBasicTypes.STRING};
            if(hiContext.update(hqlString, types, values)){
                return "M0001|修改账户信息成功";
            }else {
                return "E0001|修改账户信息失败";
            }
        }catch(Exception e){
            e.printStackTrace();
            return "E9999|账户信息修改失败";
        }
    }

    public String delectAcctPower(HibernateContext hiContext,String agentId,String acctId,String version){
        try{
            String hqlString ="delete BnkAcctpower where  agentId= ? and  acctId= ? and version=?";
            Object[] values=new Object[]{agentId,acctId,version};
            Type[] types=new Type[]{StandardBasicTypes.STRING,StandardBasicTypes.STRING,StandardBasicTypes.STRING};
            if (hiContext.update(hqlString,types,values)){
                return "M0001|账户删除成功";
            }else {
                return "E009|账户信息删除失败";
            }
        }catch(Exception e){
            return "E9999|异常"+e.getMessage();
        }
    }

    public static String addAcctPower(HibernateContext hiContext,String agentId,String acctId,String queryPower,
                                      String paypower,String billpower,String version,String subbranchid){
        try{
            BnkAcctpower bnkAcctpower = new BnkAcctpower();
            bnkAcctpower.setAgentid(agentId);
            bnkAcctpower.setAcctid(acctId);
            bnkAcctpower.setQuerypower(queryPower);
            bnkAcctpower.setPaypower(paypower);
            bnkAcctpower.setBillpower(billpower);
            bnkAcctpower.setVersion(version);
            bnkAcctpower.setSubbranchid(subbranchid);
            bnkAcctpower.setInputdate(new DateUtil().getSqlTime());
            hiContext.add(bnkAcctpower);
            return "M0001|插入成功";
        }catch(Exception e){
            return "E9999|账户信息插入失败";
        }
    }
}
