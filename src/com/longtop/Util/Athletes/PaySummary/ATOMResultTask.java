package com.longtop.Util.Athletes.PaySummary;

import com.longtop.Util.HelpCentre.DateUtil;
import com.longtop.Util.LoadMotor.LoadEngine;
import com.longtop.Util.PostMan.HttpUtil;
import com.longtop.alsb.dao.HibernateContext;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class ATOMResultTask implements Task{

    private static Logger logger = Logger.getLogger(ATOMResultTask.class);

    @Test
    @Override
    public void executeTask() {
        logger.info("ATOMResultTask-开始执行的任务:"+DateUtil.getDate("yyyy-MM-dd HH:mm:dd")+"Start!");
        Session session=null;
        try {
            HibernateContext hiContext = LoadEngine.getDBContext("SQL");
            String atomSql="select pay.ordernum , pay.version , pay.subbranchid , pay.agentid from bnk_pay_infor pay " +
                    "where pay.payprocess='1' and pay.txcode='1000' and (pay.agentid='W01' or pay.agentid='Z01')" +
                    " Union all " +
                    "select pay.ordernum , '21' as version , pay.subbranchid , pay.agentid from bnk_pay_infor pay " +
                    "where pay.payprocess='1' and pay.txcode='2000' and pay.agentid='Z01' and pay.nbkcode is not null and pay.atomcode is null";
            session = hiContext.getSession();
            SQLQuery sqlQuery = session.createSQLQuery(atomSql);
            sqlQuery.addScalar("ordernum", StandardBasicTypes.STRING);
            sqlQuery.addScalar("version",StandardBasicTypes.STRING);
            sqlQuery.addScalar("subbranchid", StandardBasicTypes.STRING);
            sqlQuery.addScalar("agentid",StandardBasicTypes.STRING);
            sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<Map> list=sqlQuery.list();
            //为了迎合OSB，使用XML格式进行发送
            Document document= DocumentHelper.createDocument();
            Element root = document.addElement("Root");
            root.addAttribute("Target","ATOM");
            root.addAttribute("RecCount",list.size()+"");
            root.addElement("TxSerial").addText(DateUtil.getDate("yyyyMMddHHmmss"));
            for(Map map:list){
                Element record = root.addElement("Record");
                record.addElement("Version").addText(map.get("version").toString());
                record.addElement("OrderNum").addText(map.get("ordernum").toString());
                record.addElement("SubBranchId").addText(map.get("subbranchid").toString());
                record.addElement("AgentId").addText(map.get("agentid").toString());
            }
            String xmlString=document.asXML();
            logger.info(xmlString);
            //向网银osb发送xml报文，由网银OSB拼装地址
            String requestMessage = new HttpUtil().httpPost("http://10.10.10.120:8888/NBHTKG/TypeStrategy/Assemble1030", xmlString, 20);
            logger.info("ATOMResultTask-完成执行任务:"+ DateUtil.getDate("yyyy-MM-dd HH:mm:dd")+"End!");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
}
