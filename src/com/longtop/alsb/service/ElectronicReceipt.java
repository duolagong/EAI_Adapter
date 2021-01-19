package com.longtop.alsb.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.longtop.Util.LoadMotor.LoadDBSource;
import com.longtop.alsb.dao.BankGSBHDao;
import com.longtop.alsb.dao.BankReceiptRecordDao;
import com.longtop.alsb.dao.HibernateContext;
import com.longtop.alsb.entity.BnkGsbh;
import com.longtop.alsb.entity.BnkReceiptRecord;
import com.longtop.alsb.entity.BnkReceiptRule;
import com.longtop.alsb.service.business.BankReceiptRule;
import org.apache.log4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ElectronicReceipt {

    private static Logger logger = Logger.getLogger(ElectronicReceipt.class);
    /**
     * 校验是否可查询（支付通）
     *
     * @param version
     * @param subBranchId
     * @param agentId
     * @param queryAcctId
     * @param queryDate
     * @return
     */
    public static synchronized String checkBankReceipt(HibernateContext hiContext, String version, String subBranchId, String agentId,
                                                       String queryAcctId, String queryDate, String zipFilePath) {

        String returnTag = "";
        try {
            List ruleList = new BankReceiptRule().getReceiptInfo(hiContext, agentId);// 校验客户代码
            if (ruleList == null || ruleList.size() == 0) {
                logger.info("暂时未开展关于客户代码为" + agentId + "的电子回单业务");
                returnTag = "E00001|暂时未开展关于客户代码为" + agentId + "的电子回单业务";
            } else {
                List sftplist = new BankGSBHDao().checkVersion(hiContext, version);
                String sftpIp = (sftplist == null || sftplist.size() == 0) ? "" : ((BnkGsbh) sftplist.get(0)).getfSftpIp();
                if (sftpIp == null || sftpIp.equals("")) {
                    returnTag = "E00001|未查询到SFTP地址，请联系管理员维护";
                } else {
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                    Calendar cal = Calendar.getInstance();
                    String newDate = format.format(cal.getTime());
                    List list = new BankReceiptRecordDao().getBankReceipt(hiContext, version, subBranchId, agentId, queryAcctId, queryDate, newDate);
                    logger.info("==============  list信息 start  ================");
                    logger.info("==============" + list.toString() + "================");
                    logger.info("==============  list信息   end   ================");
                    if (list != null && !list.isEmpty()) {
                        String lookCount = ((BnkReceiptRule) ruleList.get(0)).getLookcount();
                        if (list.size() > 0 && list.size() < Integer.parseInt(lookCount)) {
                            String intervalTime = ((BnkReceiptRule) ruleList.get(0)).getIntervaltime();
                            Date inputdate = ((BnkReceiptRecord) list.get(0)).getInputdate();
                            Date now = new Date();
                            long timeDiff = (now.getTime() - inputdate.getTime()) / (1000 * 60);
                            logger.info("============ 设置的可查询时间间隔:" + intervalTime);
                            logger.info("============ 上次有效获取电子回单时间:" + inputdate);
                            logger.info("============ 距离有效时间间隔:" + timeDiff);
                            if (timeDiff < Long.valueOf(intervalTime).longValue()) {
                                returnTag = "E00001|" + intervalTime + "分钟内不允许多次查询";
                            } else {
                                if (judgeFile(queryDate, zipFilePath)) {
                                    returnTag = "M00001|电子回单已发出，请注意查收";
                                } else {
                                    returnTag = "E00001|暂无电子回单，请稍后再试";
                                }
                            }
                        } else {
                            returnTag = "E00001|当日申请已达上限，请求被拒绝";
                        }
                    } else {
                        if (judgeFile(queryDate, zipFilePath)) {
                            returnTag = "M00001|电子回单已发出，请注意查收";
                        } else {
                            returnTag = "E00001|暂无电子回单，请稍后再试";
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnTag="E9999|获取电子回单异常"+e.getMessage();
        }
        return returnTag;
    }


    /**
     * 判断电子回单是否存在，因为网银问题，没有带agentid，所有现在路径里面先不带，等以后银行电子回单上线后再修改
     * 路径格式：/fileUrl/EleReceipt/queryAcctId/newDate
     *
     * @param newDate
     * @param zipFilePath
     * @return
     */
    public static boolean judgeFile(String newDate, String zipFilePath) {
//		LoadDBSource.loadFile("database.properties");
//		String zipFilePath = LoadDBSource.getPropertyValue("fileUrl")
//				+ "/EleReceipt/"+newDate+"/"+queryAcctId;
        File zipFile = new File(zipFilePath);
        if (!zipFile.exists()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 内部账户
     * 获取电子回单压缩包文件信息
     * 格式：文件名|子文件个数
     */
    public static String getZipFileInfo(String newDate, String agentId, String queryAcctId) {
        String ZipFileInfo = "";
        LoadDBSource.loadFile("database.properties");
        try {
//			String zipFilePath = LoadDBSource.getPropertyValue("fileUrl")
//					+ "/EleReceipt/" + agentId + "/" + newDate + "/"+ queryAcctId;
            String zipFilePath = LoadDBSource.getPropertyValue("fileUrl")
                    + "/EleReceipt/" + newDate + "/" + queryAcctId;
//			String zipFilePath = "E:/工作项目/航天科工/工行测试报文/" + agentId;
            File zipFile = new File(zipFilePath);
            if (zipFile.exists()) {
                File[] tempList = zipFile.listFiles();
                for (int i = 0; i < tempList.length; i++) {
                    if (tempList[i].isFile()) {
                        String zipFileName = tempList[i].getName().replaceAll(".zip", "");
                        String[] zipFileInfo = zipFileName.split("_", 3);
                        String pdfFileNum = zipFileInfo[2];
                        ZipFileInfo = zipFileName + "|" + pdfFileNum;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ZipFileInfo;
    }

    /**
     * 核心osb处理支付通发来的json数据
     *
     * @param jsonString 发来的json数据
     * @return
     */
    public static String manageBankReceipt(String jsonString) {
        String resultMsg = "";
        try {
//			BankReceiptResult bankReceiptResult=JSON.parseObject(jsonString, BankReceiptResult.class);
            JSONObject jsonObject = JSON.parseObject(jsonString);
            String agentId = jsonObject.get("agentId").toString();
            String queryDate = jsonObject.get("queryDate").toString();
            String respCode = jsonObject.get("respCode").toString();
            String respMsg = jsonObject.get("respMsg").toString();
            resultMsg = WriteLogWrapGeneral.insertBankReceiptSwitch(agentId, queryDate, respCode, respMsg);
        } catch (Exception e) {
            e.printStackTrace();
            resultMsg = "E9999|失败" + e.getMessage();
        }
        return resultMsg;
    }

    public static String manageBocDownload(String jsonString) {
        try {
            JSONObject jsonObject = JSON.parseObject(jsonString);
            String reqCode = jsonObject.get("reqCode").toString();
            String reqMsg = jsonObject.get("reqMsg").toString();
            return reqCode + "|" + reqMsg;
        } catch (Exception e) {
            e.printStackTrace();
            return "E9999|" + e.getMessage();
        }

    }
}
