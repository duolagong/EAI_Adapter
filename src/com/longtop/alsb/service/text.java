package com.longtop.alsb.service;

import com.longtop.Util.PostMan.HttpUtil;
import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;

public class text {
    private static Logger logger = Logger.getLogger(text.class);
    public static void main(String[] args) throws Exception {
//        WriteLogWrapGeneral writeLogWrapGeneral = new WriteLogWrapGeneral();
/*        System.out.println(writeLogWrapGeneral.getPayStatus("411930310013","63","102"));*/
        /*System.out.println(writeLogWrapGeneral.updateSystemSynXml("201907031000020","21","",
                "BANKFINAL","M0001","失败",
                "<Root><Head><Version>02</Version></Head><Body><CurCode>RMB</CurCode></Body></Root>"));*/
        /*System.out.println(writeLogWrapGeneral.insertLogErpSendXml("123123","41","102","1",
                "20200313161100","<Root><Head>111</Head></Root>","CS-0054","2.40"));*/
        /*System.out.println(WriteLogWrapGeneral.insertNBKInfor("40","123","123123",
                "20200316102000","102","1","","<Root><Head>123</Head></Root>"));*/
        /*System.out.println(WriteLogWrapGeneral.confirmAdminInfor("21"));
        System.out.println(WriteLogWrapGeneral.getAdminInfor("50","CS-0085","2","陈欣"));
        System.out.println(WriteLogWrapGeneral.judgeAdminInfor("041@N911100007109288907@ASIFC@00000714",
                "371521198410230029","50","CS-0085","2"));
        System.out.println(WriteLogWrapGeneral.addAdminInfor("50","CS-0085","2","陈欣",
                "371521198410230029","041@N911100007109288907@ASIFC@00000714"));
        System.out.println(WriteLogWrapGeneral.delectAdminInfor("50","CS-0085","2","陈欣"));*/
/*        logger.info(WriteLogWrapGeneral.confirmAcctPower("41"));
        logger.info(WriteLogWrapGeneral.getAcctIdPower("301","110060587018170042654","1000","41"));*/
        /*logger.info(WriteLogWrapGeneral.getAcctPower("301","110060587018170042654","41"));*/
//        logger.info(WriteLogWrapGeneral.delectAcctPower("301","110060587018170042654","50"));
//        logger.info(WriteLogWrapGeneral.addAcctPower("301","110060587018170042654","1","1","1","50","CS-0001"));
        /*logger.info(WriteLogWrapGeneral.delectAcctPower("301","110060587018170042654","50"));*/
//        logger.info(WriteLogWrapGeneral.getPaymentTime("301"));
//        logger.info(WriteLogWrapGeneral.getPaymentTime("302"));
//        logger.info(WriteLogWrapGeneral.getAcctSubBranchId("301","110060587018170042654","50"));
//        logger.info(WriteLogWrapGeneral.updateAcctPower("301","110060587018170042654","1","0","0","50","CS-9999"));
//        logger.info(WriteLogWrapGeneral.getAcctSubBranchId("301","110060587018170042654","50"));
//        logger.info(WriteLogWrapGeneral.getPaymentTime("111"));
//        logger.info(WriteLogWrapGeneral.getPayQuota("ideName"));
//        logger.info(WriteLogWrapGeneral.getSpecialAcctId("123"));
//        logger.info(WriteLogWrapGeneral.checkVersion("41"));
//        logger.info(WriteLogWrapGeneral.getTargetAddress("41"));
//        logger.info(WriteLogWrapGeneral.updateSystemParameter("66","123","123","123","123"));
//          logger.info(WriteLogWrapGeneral.updateSystemParameter("","309","3","063000","230000",
//                "063000","230000","targetAddress"));
//        logger.info(WriteLogWrapGeneral.delectSystemParameter(""));
//        logger.info(WriteLogWrapGeneral.getVsrsion("631931600009","101.00","CS-0117"));
//        logger.info(WriteLogWrapGeneral.getCheckOrderNum("631931600009","23"));
//        logger.info(WriteLogWrapGeneral. updateSystemSynXml("123123","41","","BANKFINAL","M0001","测试成功","<Root><Head>123</Head></Root>"));
//        logger.info(WriteLogWrapGeneral.getPayStatus("123123","41","102"));
//        String Map1="{FindType=3,txmoment_start=2018-07-19,txmoment_end=2018-07-19,version=50|51|52|53|54|55|56|57|58|59|60|61|62|63|64|65|66|67|68|69,agentId=102}";
////        String Map2="{FindType=1,version=41,txmoment_start=20200318,txmoment_end=20200325,state=P,startIndex=1,endIndex=3}";
////
////        String sql=new MonitorPlatform().getSuperNetClob(Map2);
//        for(int i=1;i<99;i++){
//            logger.info(writeLogWrapGeneral.insertLogErpSendXml("4120200427"+i,"41","102","1",
//                    "20200313161100","<Root><Head>111</Head></Root>","CS-0054","2.40","1000"));
//        }

//        Thread thread1 =new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i=1;i<99;i++){
//                    logger.info(WriteLogWrapGeneral. updateSystemSynXml("4120200427"+i,"41","","ERP",
//                            "M0001","ERP测试成功","<Root><Head>123</Head></Root>"));
//                }
//                logger.info("\n进行的是第1个");
//                logger.info(WriteLogWrapGeneral.insertLogErpSendXml("4120200428"+1,"41","102","1",
//                        "20200313161100","<Root><Head>111</Head></Root>","CS-0054","2.40","1000"));
//                logger.info("\n进行的是第1个~");
//            }
//        });
//        Thread thread2 =new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for(int i=1;i<99;i++){
//                    logger.info(WriteLogWrapGeneral. updateSystemSynXml("4120200427"+i,"41","","BANKSYN",
//                            "M0001","BANKSYN测试成功","<Root><Head>123</Head></Root>"));
//                }
//                logger.info("\n进行的是第2个");
//                logger.info(WriteLogWrapGeneral.insertLogErpSendXml("4120200428"+2,"41","102","1",
//                        "20200313161100","<Root><Head>111</Head></Root>","CS-0054","2.40","1000"));
//                logger.info("\n进行的是第2个~");
//            }
//        });
//        Thread thread3 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 1; i < 99; i++) {
//                    logger.info(WriteLogWrapGeneral.updateSystemSynXml("4120200427" + i, "41", "", "BANKFINAL",
//                            "M0001", "BANKFINAL测试成功", "<Root><Head>123</Head></Root>"));
//                }
//                logger.info("\n进行的是第3个");
//                logger.info(WriteLogWrapGeneral.insertLogErpSendXml("4120200428"+3,"41","102","1",
//                        "20200313161100","<Root><Head>111</Head></Root>","CS-0054","2.40","1000"));
//                logger.info("\n进行的是第3个~");
//            }
//        });
//        thread1.start();
//        thread2.start();
//        thread3.start();
        String json="{\"trandate\":1586945449000,\"version\":\"58\",\"ordernum\":\"582018101581\"}";
        logger.info("发送的数据"+json);
        String responsePost = new HttpUtil().httpPost("http://10.10.10.120:10080/ebank/platformPaymentResultAction!getPlatformPaymentStatus.do", json, 20);
        logger.info("查询返回:"+responsePost);
    }
}
