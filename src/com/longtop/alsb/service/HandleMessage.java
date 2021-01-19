package com.longtop.alsb.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.longtop.alsb.entity.BnkBankreceiptswitch;
import org.apache.log4j.Logger;

public class HandleMessage {

    private static Logger logger = Logger.getLogger(HandleMessage.class);
    /**
     * 将处理银行电子回单的结果信息组织成json
     * @param agentId
     * @param queryDate
     * @param respCode
     * @param respMsg
     * @return
     */
    public static String jsonMessage(String agentId,String queryDate,String respCode,String respMsg){
        BnkBankreceiptswitch bankReceiptSwitch = new BnkBankreceiptswitch();
        bankReceiptSwitch.setAgentid(agentId);
        bankReceiptSwitch.setQuerydate(queryDate);
        bankReceiptSwitch.setRespcode(respCode);
        bankReceiptSwitch.setRespmsg(respMsg);
        String jsonBankReceiptResult = JSON.toJSONString(bankReceiptSwitch);
        logger.info("jsonBankReceiptSwitch数据:"+jsonBankReceiptResult);
        return jsonBankReceiptResult;
    }

    public static String getJsonValue(String jsonString,String jsonName){
        JSONObject json=JSON.parseObject(jsonString);
        return json.get(jsonName).toString();
    }

    public static String getJsonTree(String stringName,String stringValue){
        try {
            String[] nameArray=stringName.split("\\|");
            String[] valueArray=stringValue.split("\\|");
            JSONObject json=new JSONObject();
            if(nameArray.length==valueArray.length){
                for(int i=0;i<nameArray.length;i++){
                    json.put(nameArray[i], valueArray[i]);
                }
                return json.toJSONString();
            }else{
                return	"E9999|键值对数量不一致";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "E9999|生成JSON异常:"+e.getMessage();
        }
    }

}