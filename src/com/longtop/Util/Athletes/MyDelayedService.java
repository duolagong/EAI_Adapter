package com.longtop.Util.Athletes;

public interface MyDelayedService {
    //初始化
    void init();
    //插入任务队列
    void put(MyDelayedEvent myDelayedEvent);
    //移除任务队列
    boolean remove (MyDelayedEvent myDelayedEvent);
}
