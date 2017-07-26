package com.atguigu.myapplication.base;

/**
 * Created by Administrator on 2017/7/22.
 */

public class Handler {

    /*
    * 一 什么是handler
    *   1 只能在主线程更新UI  android是线程不安全的
    *   handler通过发送和处理的message和Runnable对象来关联相对应线程的messageQueue
    *   1 可以让对应的message和Runnable在未来的某个时间点进行相应处理
    *   2 让自己想要处理的耗时操作放在子线程，让更新ui的操作放在主线程
    *
    * 二 使用
    * post(runnable)  (new Handler会绑定当前线程)
    * sendMessage(message)
    *
    * 1 从ThreadLoacal中获取looper 一个线程有一个looper
    *
    * 2 looper无限循环
    *
    * 三内存泄漏
    * handler持有activity引用对象
    * 一 设置成静态
    * 二 removeCallBack
    * 原因 ： 静态内部类持有外部类的匿名引用，导致外部activity无法释放
    *
    * 解决办法 ：handler内部持有外部activity的弱引用，并把handler改为静态内部类
    *           mhandler.removeCallBack();
    *
    *
    * handlerThread是什么？
    * 1 开启thread子线程进行耗时操作
    *
    * 2 多次创建和销毁线程是很消耗资源系统的
    *
    * 本质 handler + thread + looper
    * 是一个thread内部有looper
    *
    * handlerThread面试详解
    * 1 HandlerThread本质上是一个线程类，它继承了Thread
    * 2 HandlerThread有自己的内部Looper对象，可以进行looper循环
    * 3 通过获取HandlerThread的looper对象传递handler的值，可以在handlerMessage方法中执行异步任务
    * 4 优点是不会有堵塞，减少了对性能的消耗，缺点是不能同时进行多任务的处理，需要等待进行处理，处理效率低
    * 5 与线程池注重并发不同，HandlerThread是一个串行队列，HandlerThread背后只有一个线程
    *
    *
    * */
}
