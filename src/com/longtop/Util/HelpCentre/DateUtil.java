package com.longtop.Util.HelpCentre;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 获取dayNum前的时间
     * @param dayNum
     * @return
     */
    public static String getLastDate(int dayNum)
    {
        Calendar cal   =   Calendar.getInstance();
        cal.add(Calendar.DATE,   -dayNum);
        return new SimpleDateFormat( "yyyyMMdd").format(cal.getTime());
    }

    /**
     * 获取指定格式的时间
     * @param standard
     * @return
     */
    public static String getDate(String standard){
        SimpleDateFormat format = new SimpleDateFormat(standard);
        Calendar cal = Calendar.getInstance();
        return format.format(cal.getTime());
    }

    /**
     * 获取数据库时间
     * @return
     */
    public static Time getSqlTime(){
        return new Time(new Date().getTime());
    }

    /**
     * 获取与当前时间的时间差(秒)
     * @param time
     * @return
     * @throws ParseException
     */
    public static long getTimeInterval(String time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date nowDate = new Date();
        Date date=format.parse(time);
        long dateInterval=date.getTime()-nowDate.getTime();
        return dateInterval/(1000);
    }
}
