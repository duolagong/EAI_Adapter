package com.longtop.Util.Athletes;

import com.longtop.Util.Athletes.PaySummary.ATOMResultTask;
import com.longtop.Util.Athletes.PaySummary.NBKResultTask;
import com.longtop.Util.Athletes.PaySummary.OSBResultTask;
import com.longtop.Util.Athletes.PaySummary.Task;
import com.longtop.Util.HelpCentre.DateUtil;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class MyDelayedServiceImpl implements  MyDelayedService {

    private static Logger logger = Logger.getLogger(MyDelayedServiceImpl.class);

    DelayQueue<MyDelayedEvent> queue = new DelayQueue<MyDelayedEvent>();


    MyDelayedServiceImpl() {
        init();
    }

    /**
     * 向队列中存放定时任务
     */
    public void motionSet() {
        for (; ; ) {
            try {
                //计算间隔时间
                logger.info("网银OSB定时队列——启动");
                Map<String,Long> timeRule = new MyDelayedWorkshop().getTimeRule();
                //依据任务类型放置任务
                logger.info("网银OSB定时队列——准备存放关于NBK的时间任务");
                Task nbkResultTask = new NBKResultTask();
                MyDelayedEvent nbkDelayedEvent = new MyDelayedEvent(nbkResultTask, timeRule.get("timeNBK"),TimeUnit.SECONDS);
                queue.put(nbkDelayedEvent);
                logger.info("网银OSB定时队列——准备存放关于ATOM的时间任务");
                Task atomResultTask = new ATOMResultTask();
                MyDelayedEvent atomDelayedEvent = new MyDelayedEvent(atomResultTask,timeRule.get("timeATOM"),TimeUnit.SECONDS);
                queue.put(atomDelayedEvent);
                logger.info("网银OSB定时队列——准备存放关于OSB的时间任务");
                Task osbResultTask = new OSBResultTask();
                MyDelayedEvent osbDelayedEvent = new MyDelayedEvent(osbResultTask, timeRule.get("timeOSB"),TimeUnit.SECONDS);
                queue.put(osbDelayedEvent);
                logger.info("网银OSB定时队列——时间任务分配完成，进入休眠状态");
                TimeUnit.SECONDS.sleep(timeRule.get("timeSYS"));//继续休眠
            }catch (Exception e){
                logger.error("发生错误",e);
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理队列中自动任务
     */
    public void motionGet() {
        for (; ; ) {
            try {
                MyDelayedEvent myDelayedEvent = queue.take();
                String className = myDelayedEvent.getTask().getClass().getName();
                myDelayedEvent.getTask().executeTask();
                logger.info(className+"-任务已成为");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void init() {
        logger.info("网银OSB定时队列——初始化线程！");
        Thread damonSet = new Thread(new Runnable() {
            @Override
            public void run() {
                motionSet();
            }
        });
        Thread damonGet = new Thread(new Runnable() {
            @Override
            public void run() {
                motionGet();
            }
        });
        damonSet.setDaemon(true);
        damonGet.setDaemon(true);
        damonSet.setName("OSBSetMan");
        damonGet.setName("OSBGetMan");
        damonSet.start();  //启动线程
        damonGet.start();  //启动线程
        logger.info("网银OSB定时队列——初始化线程已完成！");
    }

    @Override
    public void put(MyDelayedEvent myDelayedEvent) {
        logger.info("放入队列！");
        queue.put(myDelayedEvent);
    }

    @Override
    public boolean remove(MyDelayedEvent myDelayedEvent) {
        logger.info("移除队列！");
        return queue.remove(myDelayedEvent);
    }

    public static void main(String[] args) throws ParseException {
//          new MyDelayedServiceImpl();
        System.out.println(DateUtil.getLastDate(-1));
//        System.out.println(System.nanoTime());
//        System.out.println(System.currentTimeMillis());;
//        System.out.println(TimeUnit.MINUTES.toMillis(1));
    }
}
