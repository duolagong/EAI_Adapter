package com.longtop.Util.Athletes;

import com.longtop.Util.HelpCentre.DateUtil;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class MyDelayedWorkshop {

    /**
     *
     * 获取时间，在22:50-23:40之间，准备向队列里放值
     * 分为3个任务：
     *  发送给网银——使用json
     *  发送给核心——使用xml,task拟好报文发送给网银osb的接收队列，组织好1030查询
     *  发送给OSB——使用json——半小时执行一次
     */
    public Map getTimeRule() throws ParseException {
        HashMap<String, Long> hashMap = new HashMap<String, Long>();
        String nowDay = DateUtil.getDate("yyyy-MM-dd");//当前时间
        String tomDay = DateUtil.getLastDate(-1);//明日时间
        hashMap.put("timeNBK",DateUtil.getTimeInterval(nowDay+ " "+"23:00:00"));
        hashMap.put("timeATOM",DateUtil.getTimeInterval(nowDay+" "+"23:20:00"));
        hashMap.put("timeOSB",DateUtil.getTimeInterval(nowDay+ " "+"23:40:00"));
        hashMap.put("timeSYS",DateUtil.getTimeInterval(tomDay+ " "+"22:50:00"));
        return hashMap;
    }
}
