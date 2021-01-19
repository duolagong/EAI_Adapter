package com.longtop.alsb.dao;
import com.longtop.Util.HelpCentre.DateUtil;
import com.longtop.Util.Hibernate.HibernateDao;
import com.longtop.alsb.entity.BnkPayInfor;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Union;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BankPayInforDao {

    public List getVersion(HibernateContext hiContext,String ordernum,String txAmount,String subBranchId) throws Exception {
        String hqlString ="from BnkPayInfor where ordernum= ? and TxAmount= ? and SubBranchId= ?";
        Object[] values = new Object[]{ordernum,txAmount,subBranchId};
        Type[] types = new Type[]{StandardBasicTypes.STRING,StandardBasicTypes.STRING,StandardBasicTypes.STRING};
        List list = hiContext.get(hqlString,types,values);
        return list;
    }

    //可能需要修改为只查询Ordernum
    public List getCheckOrderNum(HibernateContext hiContext,String orderNum) throws Exception {
        String hqlString = "from BnkPayInfor where ordernum=?";
        Object[] values = new Object[]{orderNum};
        Type[] types = new Type[]{StandardBasicTypes.STRING};
        List list = hiContext.get(hqlString,types,values);
        return list;
    }

    /**
     * 更新付款状态
     * 注意：对于最终状态"BANKFINAL",为支付最终状态,理论确定之后，不允许再修改，由于安全性考虑，仅可以将"失败"修改成"成功"，反之不允许
     */
    public boolean updateSystemSynXml(HibernateContext hiContext,String ordernum,String version,String PayProcess,String ProcessType,String RespCode,
                                      String RespMsg,String ProcessXml) throws Exception{
        String hqlString =null;
        Date nowDate = new java.sql.Timestamp(System.currentTimeMillis());
        if(RespCode.startsWith("E")){
            if(ProcessType.equals("ERP")){
                hqlString ="update BnkPayInfor set ErpCode=?,ErpMsg=?,ErpXml=?,PayProcess='0',Erpdate=? where ordernum=? and version=? and PayProcess='1'";
            }else if (ProcessType.equals("ATOM")){
                hqlString ="update BnkPayInfor set AtomCode=?,AtomMsg=?,AtomXml=?,Atomdate=? where ordernum=? and version=? and PayProcess='1'";
            }else if (ProcessType.equals("OSB")){
                hqlString ="update BnkPayInfor set OsbCode=?,OsbMsg=?,OsbXml=?,Osbdate=?  where ordernum=? and version=? and PayProcess='1'";
            }else if (ProcessType.equals("NBK")){
                hqlString ="update BnkPayInfor set NbkCode=?,NbkMsg=?,NbkXml=?,Nbkdate=?  where ordernum=? and version=? and PayProcess='1'";
            }else if (ProcessType.equals("BANKSYN")){
                hqlString ="update BnkPayInfor set BankSynCode=?,BankSynMsg=?,BankSynXml=?,BankSyndate=?  where ordernum=? and version=? and PayProcess='1'";
            }else if (ProcessType.equals("BANKFINAL")){
                hqlString ="update BnkPayInfor set BankFinalCode=?,BankFinalMsg=?,BankFinalXml=?,PayProcess='0',BankFinaldate=?  where ordernum=? and version=? and PayProcess='1'";
            }else if (ProcessType.equals("ERPSYN")){
                hqlString ="update BnkPayInfor set ERPSynCode=?,ERPSynMsg=?,ERPSynXml=?,ERPSyndate=? where ordernum=? and version=? and PayProcess='0'";
            }
        }else{
            if(ProcessType.equals("ERP")){
                hqlString ="update BnkPayInfor set ErpCode=?,ErpMsg=?,ErpXml=?,Erpdate=? where ordernum=? and version=? and PayProcess='1'";
            }else if (ProcessType.equals("ATOM")){
                hqlString ="update BnkPayInfor set AtomCode=?,AtomMsg=?,AtomXml=?,Atomdate=? where ordernum=? and version=? and PayProcess='1'";
            }else if (ProcessType.equals("OSB")){
                hqlString ="update BnkPayInfor set OsbCode=?,OsbMsg=?,OsbXml=?,Osbdate=?  where ordernum=? and version=? and PayProcess='1'";
            }else if (ProcessType.equals("NBK")){
                hqlString ="update BnkPayInfor set NbkCode=?,NbkMsg=?,NbkXml=?,Nbkdate=?  where ordernum=? and version=? and PayProcess='1'";
            }else if (ProcessType.equals("BANKSYN")){
                hqlString ="update BnkPayInfor set BankSynCode=?,BankSynMsg=?,BankSynXml=?,BankSyndate=?  where ordernum=? and version=? and PayProcess='1'";
            }else if (ProcessType.equals("BANKFINAL")){
                if(RespCode.startsWith("M")){
                    hqlString ="update BnkPayInfor set BankFinalCode=?,BankFinalMsg=?,BankFinalXml=?,PayProcess='0',BankFinaldate=?  where ordernum=? and version=?";
                }else{
                    hqlString ="update BnkPayInfor set BankFinalCode=?,BankFinalMsg=?,BankFinalXml=?,BankFinaldate=?  where ordernum=? and version=? and PayProcess='1'";
                }
            }else if (ProcessType.equals("ERPSYN")){
                hqlString ="update BnkPayInfor set ERPSynCode=?,ERPSynMsg=?,ERPSynXml=?,ERPSyndate=? where ordernum=? and version=? and PayProcess='0'";
            }
        }
        Object[] values=new Object[]{RespCode,RespMsg,hiContext.stringToClob(ProcessXml),nowDate,ordernum,version};
        Type[] types=new Type[]{StandardBasicTypes.STRING,StandardBasicTypes.STRING,StandardBasicTypes.CLOB,
                StandardBasicTypes.TIMESTAMP,StandardBasicTypes.STRING,StandardBasicTypes.STRING};
        return hiContext.update(hqlString,types,values);
    }

    public List getPayStatus(HibernateContext hiContext, String ordernum, String version, String agentid) throws Exception {
        List list=null;
        if(agentid==null || agentid.equals("")){
            String hqlString ="from BnkPayInfor where Ordernum=? and version=?";
            Object[] values = new Object[]{ordernum.trim(),version.trim()};
            Type[] types = new Type[]{StandardBasicTypes.STRING,StandardBasicTypes.STRING};
            list = hiContext.get(hqlString,types,values);
        }else{
            String hqlString ="from BnkPayInfor where Ordernum=? and version=? and agentid=?";
            Object[] values = new Object[]{ordernum.trim(),version.trim(),agentid.trim()};
            Type[] types = new Type[]{StandardBasicTypes.STRING,StandardBasicTypes.STRING,StandardBasicTypes.STRING};
            list = hiContext.get(hqlString,types,values);
        }
        return list;
    }

    public void  insertLogErpSendXml(HibernateContext hiContext, String ordernum, String version,
                                             String AgentId, String PersonFlag, String TxMoment,
                                             String SendXml, String subBranchId, String txAmount,String txCode) throws Exception {
        BnkPayInfor bnkpayinfor = new BnkPayInfor();
        bnkpayinfor.setOrdernum(ordernum);
        bnkpayinfor.setVersion(version);
        bnkpayinfor.setAgentid(AgentId);
        bnkpayinfor.setPersonflag(PersonFlag);
        bnkpayinfor.setTxmoment(TxMoment);
        bnkpayinfor.setTranxml(SendXml);
        bnkpayinfor.setPayprocess("1");
        bnkpayinfor.setSubbranchid(subBranchId);
        bnkpayinfor.setTxamount(txAmount);
        bnkpayinfor.setTrandate(new DateUtil().getSqlTime());
        bnkpayinfor.setSendatomtag("0");
        bnkpayinfor.setSendatomosbtag("0");
        bnkpayinfor.setTxcode(txCode);
        hiContext.add(bnkpayinfor);
    }

    public boolean updateSendAtomTag(HibernateContext hiContext,String ordernum,String version,String AgentId,String SendTag)throws Exception{
        String hqlString =null;
        if (SendTag.equals("ATOM")){
            hqlString ="update BnkPayInfor set SendAtomTag='1' where ordernum=? and version=? and AgentId=? and PayProcess='1'";
        }else if(SendTag.equals("ATOMOsb")){
            hqlString ="update BnkPayInfor set SendAtomOsbTag='1' where ordernum=? and version=? and AgentId=? and PayProcess='1'";
        }else {
           throw new Exception("SendTag错误");
        }
        Object[] values=new Object[]{ordernum,version,AgentId};
        Type[] types=new Type[]{StandardBasicTypes.STRING,StandardBasicTypes.STRING,StandardBasicTypes.STRING};
        return hiContext.update(hqlString,types,values);
    };

    public List getNbkStatus(HibernateContext hiContext) throws Exception {
        String nbkSql=" select  pay.ordernum , pay.version from  bnk_pay_infor pay where payprocess='1' and txcode='2000' and nbkcode is null";
        Session session = hiContext.getSession();
        SQLQuery sqlQuery = session.createSQLQuery(nbkSql);
        sqlQuery.addScalar("ordernum",StandardBasicTypes.STRING);
        sqlQuery.addScalar("version",StandardBasicTypes.STRING);
        sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> list=sqlQuery.list();
        session.close();
        return list;
    }

    public List getAtomStatus(HibernateContext hiContext) throws Exception {
        String atomSql="select * from bnk_pay_infor a where a.payprocess='1' and txcode='1000' and (agentid='W01' or agentid='Z01') Union all " +
                "select * from bnk_pay_infor a  where a.payprocess='1' and txcode='2000' and agentid='Z01' and nbkcode is not null and atomcode is null";
        List list=hiContext.get(atomSql);
        return list;
    }

    public List getOsbStatus(HibernateContext hiContext)throws Exception{
        String osbSql="from BnkPayInfor a  where a.payprocess='1' and txcode='1000' and agentid<>'Z01' and agentid<>'W01' Union all" +
                "from BnkPayInfor a  where a.payprocess='1' and txcode='2000' and agentid<>'Z01' and agentid<>'W01' and nbkcode is not null";
        return hiContext.get(osbSql);
    }
}
