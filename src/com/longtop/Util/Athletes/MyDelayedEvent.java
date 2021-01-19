package com.longtop.Util.Athletes;

import com.longtop.Util.Athletes.PaySummary.Task;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class MyDelayedEvent implements Delayed {

    private Task task;
    private long time;//秒

    MyDelayedEvent(Task task, Long time, TimeUnit unit) {
        this.task = task;
        this.time = System.currentTimeMillis()/1000 + (time > 0? unit.toSeconds(time): 0);//时间颗粒度转换——>秒
    }

    /**
     * 用来判断是否到了截止时间
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return time - System.currentTimeMillis()/1000;
    }

    /**
     * 相互比较排序
     */
    @Override
    public int compareTo(Delayed o) {
        MyDelayedEvent myDelayedEvent = (MyDelayedEvent) o;
        long diff = this.time - myDelayedEvent.time;
        if (diff <= 0) {
            return -1;
        }else {
            return 1;
        }
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
