package com.longtop.Util.Athletes.PaySummary;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.longtop.Util.HelpCentre.DateUtil;
import com.longtop.Util.LoadMotor.LoadEngine;
import com.longtop.Util.PostMan.HttpUtil;
import com.longtop.alsb.dao.BankPayInforDao;
import com.longtop.alsb.dao.BankTaskStateDao;
import com.longtop.alsb.dao.HibernateContext;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * 向资管系统获取结果的状态
 * 状态除了"E"之外的所有状态都认为是中间状态，不做状态更新处理
 */
public class NBKResultTask implements Task {

    private static Logger logger = Logger.getLogger(NBKResultTask.class);
    @Test
    @Override
    public void executeTask() {
        logger.info("NbkResultTask-执行的任务:"+DateUtil.getDate("yyyy-MM-dd HH:mm:dd")+"Start!");
        Session session=null;
        String responsePost="";
        try{
            HibernateContext hiContext = LoadEngine.getDBContext("SQL");
            String nbkSql=" select  pay.ordernum , pay.version ,pay.trandate from  bnk_pay_infor pay " +
                    "where payprocess='1' and txcode='2000' and nbkcode is null";
            session = hiContext.getSession();
            SQLQuery sqlQuery = session.createSQLQuery(nbkSql);
            sqlQuery.addScalar("ordernum", StandardBasicTypes.STRING);
            sqlQuery.addScalar("version",StandardBasicTypes.STRING);
            sqlQuery.addScalar("trandate",StandardBasicTypes.TIMESTAMP);
            sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<Map> list=sqlQuery.list();
            String jsonString = JSON.toJSONString(list);
            logger.info(jsonString);
            for(Map map:list){
                String json = JSONObject.toJSONString(map);
                logger.info("发送的数据"+json);
                responsePost = new HttpUtil().httpPost("http://10.10.10.120:10080/ebank/platformPaymentResultAction!getPlatformPaymentStatus.do", json, 20);
                logger.info("查询返回:"+responsePost);
            }
        }catch (Exception e){
            logger.error("NbkResultTask-整理或发送数据时发生错误:"+e.getMessage());
            e.printStackTrace();
            return;
        }finally {
            session.close();
        }
        try {
            JSONObject jsonObject = JSONObject.parseObject(responsePost);
            List<Map<String,Object>> listMap = (List<Map<String,Object>>)jsonObject;
            for(Map map:listMap){
                String ordernum = map.get("ordernum").toString();
                String version = map.get("version").toString();
                String subbranchid = map.get("subbranchid").toString();
                String respcode = map.get("respcode").toString();
                String respmsg = map.get("respmsg").toString();
                HibernateContext hiContext = LoadEngine.getDBContext("");
                //存入task表
                if(respcode.startsWith("M")){
                    //证明网银已经审核通过，不进行任何处理，将此条信息写入bnk_task_state日志表,状态为交易对私
                    new BankTaskStateDao().insertTaskStateXml(hiContext,ordernum,version,subbranchid,respcode,respcode);
                }else if("nbkcode".startsWith("E")){
                    //将库里状态改成失败，同时写入日志表
                    new BankPayInforDao().updateSystemSynXml(hiContext,ordernum,version,"","BANKFINAL",
                            respcode,respmsg,JSONObject.toJSONString(map));
                    new BankTaskStateDao().insertTaskStateXml(hiContext,ordernum,version,subbranchid,respcode,respcode);
                }else {
                    //不进行任何操作，需要协商处理办法
                }
            }
        }catch (Exception e){
            logger.error("NbkResultTask-处理返回数据时发生错误:"+e.getMessage());
            e.printStackTrace();
        }finally {
            logger.info("NbkResultTask-执行的任务:"+DateUtil.getDate("yyyy-MM-dd HH:mm:dd")+"End!");
        }
    }
}
