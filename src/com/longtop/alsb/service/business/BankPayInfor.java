package com.longtop.alsb.service.business;

import com.longtop.Util.HelpCentre.StringUtil;
import com.longtop.alsb.dao.BankPayInforDao;
import com.longtop.alsb.dao.HibernateContext;
import com.longtop.alsb.entity.BnkPayInfor;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class BankPayInfor {
    private static Logger logger = Logger.getLogger(BankPayInfor.class);

    public static String getNBKPayment(HibernateContext hiContext, String ordernum) {
        try {
            List list = new BankPayInforDao().getCheckOrderNum(hiContext, ordernum);
            String nbkCode = (list == null || list.size() == 0) ? "" : ((BnkPayInfor) list.get(0)).getNbkcode();
            return StringUtil.isEmpty(nbkCode) ? "M" : "E";
        } catch (Exception e) {
            e.printStackTrace();
            return "E";
        }
    }

    public static String getVsrsion(HibernateContext hiContext, String ordernum) {
        String version = "";
        try {
            List list = new BankPayInforDao().getCheckOrderNum(hiContext, ordernum);
            version = (list == null || list.size() == 0) ? "" : ((BnkPayInfor) list.get(0)).getVersion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    public static String getCheckOrderNum(HibernateContext hiContext, String orderNum) {
        List list = null;
        String Result = "E";
        try {
            list = new BankPayInforDao().getCheckOrderNum(hiContext, orderNum);
            if (list != null && !list.isEmpty()) {
                Result = "M";//表示已存在
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return Result;
        }
    }

    public static boolean updateSystemSynXml(HibernateContext hiContext, String ordernum, String version, String PayProcess,
                                             String ProcessType, String RespCode, String RespMsg, String ProcessXml) {
        String queryCode = "";
        Boolean results = false;
        try {
            BankPayInforDao bankPayInforDao = new BankPayInforDao();
            List list = bankPayInforDao.getPayStatus(hiContext, ordernum, version, "");
            if (list != null && !list.isEmpty()) {//查询指定ordernum和version在表中的信息
                if (ProcessType.equals("ERP")) {
                    queryCode = ((BnkPayInfor) list.get(0)).getErpcode();
                    if (queryCode == null || queryCode.equals("")) {
                        String agentid = ((BnkPayInfor) list.get(0)).getAgentid();
                        if (agentid.equals("Z01") || agentid.equals("W01")) {
                            queryCode = ((BnkPayInfor) list.get(0)).getAtomcode();
                        }
                    }
                } else if (ProcessType.equals("ATOM")) {
                    queryCode = ((BnkPayInfor) list.get(0)).getAtomcode();
                } else if (ProcessType.equals("OSB")) {
                    queryCode = ((BnkPayInfor) list.get(0)).getOsbcode();
                } else if (ProcessType.equals("NBK")) {
                    queryCode = ((BnkPayInfor) list.get(0)).getNbkcode();//资管系统
                } else if (ProcessType.equals("BANKSYN")) {
                    queryCode = ((BnkPayInfor) list.get(0)).getBanksyncode();
                } else if (ProcessType.equals("BANKFINAL")) {
                    queryCode = ((BnkPayInfor) list.get(0)).getBankfinalcode();
                }
                if (queryCode == null || queryCode.equals("") ||
                        (ProcessType.equals("BANKFINAL") && queryCode.startsWith("P"))) {
                    StringBuffer systemAlert = new StringBuffer();
                    systemAlert.append("=========== 提示:准备提交交易号").append(ordernum);
                    systemAlert.append("的").append(ProcessType).append("状态,为").append(RespCode).append(RespMsg);
                    logger.info(systemAlert);
                    results = bankPayInforDao.updateSystemSynXml(hiContext, ordernum, version, PayProcess, ProcessType, RespCode, RespMsg, ProcessXml);
                    logger.info("=========== 提示:已受理交易号" + ordernum + "的" + ProcessType + "状态,结果:" + results);
                } else if (ProcessType.equals("BANKFINAL") && queryCode.startsWith("E")) {
                    logger.info("=========== 注意 :" + ordernum + "的BANKFINAL状态将由" + queryCode + "修改为" + RespCode);
                    StringBuffer systemAlert = new StringBuffer();
                    systemAlert.append("=========== 提示:准备提交交易号").append(ordernum);
                    systemAlert.append("的").append(ProcessType).append("状态,为").append(RespCode).append(RespMsg);
                    logger.info(systemAlert);
                    results = bankPayInforDao.updateSystemSynXml(hiContext, ordernum, version, PayProcess, ProcessType, RespCode, RespMsg, ProcessXml);
                    logger.info("=========== 交易号:" + ordernum + "的更新状态结果" + results);
                } else {
                    StringBuffer systemAlert = new StringBuffer();
                    systemAlert.append("=========== 提示:拦截交易号为").append(ordernum);
                    systemAlert.append("的").append(ProcessType).append("状态:");
                    systemAlert.append(RespCode + RespMsg);
                    logger.info(systemAlert);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("发生错误",e);
        }
        return results;
    }

    ;

    public static String insertLogErpSendXml(HibernateContext hiContext, String ordernum, String version, String AgentId,
                                             String PersonFlag, String TxMoment, String SendXml, String subBranchId,
                                             String txAmount, String txCode) {
        String InforMsg = "M00001|接收成功";
        try {
            new BankPayInforDao().insertLogErpSendXml(hiContext, ordernum, version, AgentId, PersonFlag, TxMoment, SendXml,
                    subBranchId, txAmount, txCode);
        } catch (Exception e) {
            String ErrorMsg = e.getMessage();
            if (ErrorMsg != null && ErrorMsg.indexOf("org.hibernate.exception") >= 0) {
                ErrorMsg = e.getCause().getMessage();
                ErrorMsg = "E00000|交易信息填写有误,请检查" + ErrorMsg;
            } else {
                ErrorMsg = "E00000|交易信息填写有误,请检查" + ErrorMsg;
            }
            if (ErrorMsg.length() > 100) {
                ErrorMsg = ErrorMsg.substring(0, 100);
            }
            InforMsg = ErrorMsg;
            e.printStackTrace();
        } finally {
            return InforMsg;
        }
    }

    ;

    public static boolean updateSendAtomTag(HibernateContext hiContext, String ordernum, String version, String AgentId, String SendTag) {
        boolean tag = false;
        try {
            tag = new BankPayInforDao().updateSendAtomTag(hiContext, ordernum, version, AgentId, SendTag);
        } catch (Exception e) {
            logger.error("发生错误:" + e.getMessage());
            e.printStackTrace();
        } finally {
            return tag;
        }
    }

    ;

    public static String getPayStatus(HibernateContext hiContext, String orderNum, String version, String agentId) {
        List list = null;
        String BankFinalCode = "";
        String BankFinalMsg = "";
        try {
            list = new BankPayInforDao().getPayStatus(hiContext, orderNum, version, agentId);
            if (list == null || list.size() == 0) {
                BankFinalCode = "E00001";
                BankFinalMsg = "未查询到交易,请确认填写信息是否有误";
            } else {
                try {
                    BankFinalCode = ((BnkPayInfor) list.get(0)).getBankfinalcode().trim();
                    BankFinalMsg = ((BnkPayInfor) list.get(0)).getBankfinalmsg().trim();
                } catch (NullPointerException e) {
                    if (BankFinalCode == null || BankFinalCode.equals("")) {
                        if (agentId.equals("Z01")) {
                            String ErpCode = ((BnkPayInfor) list.get(0)).getErpcode();
                            if (ErpCode == null || ErpCode.equals("")) {
                                String AtomCode = ((BnkPayInfor) list.get(0)).getAtomcode().trim();
                                if (AtomCode.startsWith("E")) {
                                    BankFinalCode = AtomCode;
                                    BankFinalCode = ((BnkPayInfor) list.get(0)).getAtommsg().trim();
                                } else {
                                    BankFinalCode = "P00001";
                                    BankFinalMsg = "交易正在处理中";
                                }
                            } else {
                                if (ErpCode.startsWith("E")) {
                                    BankFinalCode = ErpCode;
                                    BankFinalMsg = ((BnkPayInfor) list.get(0)).getErpmsg().trim();
                                } else {
                                    String AtomCode = ((BnkPayInfor) list.get(0)).getAtomcode().trim();
                                    if (AtomCode.startsWith("E")) {
                                        BankFinalCode = AtomCode;
                                        BankFinalCode = ((BnkPayInfor) list.get(0)).getAtommsg().trim();
                                    } else {
                                        BankFinalCode = "P00001";
                                        BankFinalMsg = "交易正在处理中";
                                    }
                                }
                            }
                        } else {
                            String ErpCode = ((BnkPayInfor) list.get(0)).getErpcode().trim();
                            if (ErpCode.startsWith("E")) {
                                BankFinalCode = ErpCode;
                                BankFinalMsg = ((BnkPayInfor) list.get(0)).getErpmsg().trim();
                            } else {
                                BankFinalCode = "P00001";
                                BankFinalMsg = "交易正在处理中";
                            }
                        }
                    } else {
                        BankFinalMsg = "状态信息异常,待确认";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BankFinalCode + "|" + BankFinalMsg;
    }

    public static void getPayStatus(HibernateContext hiContext){
        try {
            List<Map> nbkStatus = new BankPayInforDao().getNbkStatus(hiContext);
            System.out.println(nbkStatus);

            List atomStatus = new BankPayInforDao().getAtomStatus(hiContext);
            for (Object object:atomStatus){
                System.out.println(object);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
