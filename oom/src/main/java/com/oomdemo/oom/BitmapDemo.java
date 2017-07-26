package com.oomdemo.oom;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Administrator on 2017/7/24.
 */

public class BitmapDemo {


    public void say(){
        MyDemo demo = new MyDemo();
    }

    public void setName(){

    }

    static class MyDemo{
        public void say(){
            //setName();
        }
    }

    /*
    * 一 recycle
    *
    * 二 LRU
    *
    * 三 insampleSize
    *
    * 四 缩略图
    *
    * 五 三级缓存
    *
    *
    * */

    /*
    *
    * UI卡顿
    * 60fps - 16ms  1000/60 丢帧  GC所有线程会暂停
    * overDraw 重叠部分   绘制多次
    *
    * 1 人为在UI线程中做轻微耗时操作，导致UI线程卡顿
    * 2 布局Layout过于复杂，无法在16ms内完成常渲染
    * 3 同一时间动画执行的次数过多，导致CPU或GPU负载过重
    * 4 view过度绘制，导致某些像素在同一帧时间内被绘制多次，CPU或GPU负载过重
    * 5 view频繁的触发measure layout 导致measure layout累计耗时过多及整个view频繁的重新渲染
    * 6 内存频繁触发GC过多，导致暂时阻塞渲染操作
    * 7 冗余资源及逻辑等导致加载和执行缓慢
    * 8 ANR
    *
    * 优化
    * 1 布局优化  不要嵌套布局 gone不会绘制 weigth替换宽高
    * 2 列表及adapter优化 滑动停止再加载图片
    * 3 背景和图片等内存分配优化
    * 4 避免ANR
    *
    * */


    /*
    * oom
    * 一 java内存泄漏
    *   内存泄漏是指无用对象（不再使用的对象）持续占有内存或无用对象的内存得不到及时释放，
    *   从而造成的内存空间的浪费称为内存泄漏
    *
    *   java内存分配策略
    *   1 静态存储区（方法区） 整个程序运行都存在 全局变量 静态数据
    *   2 栈区（局部变量 引用变量）  速度块 容量有限   局部变量自动释放
    *   3 堆区  垃圾回收 new出来对象还有数组
    *
    *   java如何管理内存的

    * 二 android内存泄漏
    *   生命周期很长 （Context context）{context,getApplicationContext()}
    *   1 匿名内部类 ：非静态内部类会持有外部类的引用
    *   //加上static就可以避免内存泄漏
    *   static class Demo{
    *       public static Demo demo = new Demo();
    *   }
    *   去除隐式引用（通过静态内部类来去除隐式引用）
        手动管理对象引用（修改静态内部类的构造方式，手动引入其外部类引用）
        当内存不可用时，不执行不可控代码（Android可以结合智能指针，WeakReference包裹外部类实例）
    *
    *   2 handler内存泄漏问题
    *   一 非静态内部类持有外部引用
    *   二 message会放在messageQueue里有可能退出的时候还有任务执行 message会持有handler引用
    *   解决办法自定义一个类继承handler类声明成静态类 同时持有弱引用上下文
    *
    *   避免使用static变量
    *
    *   资源没有关闭造成的内存泄漏
    *
    *   asy
    *
    * */

    /*
    * 内存管理机制
    * 一 分配机制
    * 二 回收机制
    * android内存管理机制
    *   一 给每个app分配一个小的量，随着app不断运行会分配额外的内存
    *   二 回收机制 主要是分为五种进程
    *
    * 内存管理机制的特点
    *    1内存占用更少
    *    2合理的时间释放合理的系统资源
    *    3在系统内存紧张的情况下，能释放掉大部分不重要的资源，来为android系统提供可用的内存
    *    4能够很合理的在特殊生命周期中，保存或者还原重要的数据，以至于系统能够正确的恢复该应用
    *
    * 内存优化方法
    *     1 当service完成后，尽量关闭它
    *     2 在UI不可见的时候，释放掉一些只有UI使用的资源
    *     3 在系统内存紧张的时候，尽可能多的释放掉一些非重要资源
    *     4 避免滥用Bitmap导致的内存泄漏
    *     5 使用针对内存优化的数据容器
    *     6 避免使用依赖注入的框架
    *     7 使用zip对齐的apk
    *     8 使用多进程
    *
    */


    /*
    *
    * 冷启动的优化
    * 冷启动 ：就是启动应用前，系统中没有该应用的任何进程信息
    * （理解成第一次启动应用，或者应用被杀死再次启动）
    * 热启动　：用户使用返回键退出应用，然后马上又重新启动应用（进程是保留在后台的）
    *
    * 冷启动时间的计算：
    *   这个时间值从应用启动（创建进程）开始计算，到完成视图的第一次绘制（即Activity对用户可见内容）
    *
    *冷启动流程
    *  Zygote进程中fork创建出一个新的进程
    *  创建和初始化Application类，创建MainActivity类
    *  inflate布局，当OnCreate/onStart/onResume方法都走完contentView的measure/layout
    *  /draw显示在界面上
    *
    *   总结：Application的构造器方法 --> attachBaseContext() --> onCreate()
    *   --> Activity的构造方法 --> oncreate() --> 配置主题背景等属性 --> onStart() --> onResume()
    *   --> 测量布局绘制显示在界面上
    *
    *   冷启动优化
    *       1 减少onCreate方法的工作量 第三方在onCreate初始化懒加载（）
    *       2 不要让application参与业务的操作
    *       3 不要在Application进行耗时操作
    *       4 不要以静态变量的方式在application中保存数据
    *       5 布局 / mainThread
    *
    *
    * */

    /*
    *
    * 其它优化
    * 一 android不用静态变量存储数据
    *    原因 应用进程不安全
    *    1 静态变量等数据由于进程已经被杀死而被初始化
    *    2 使用其他数据传输方式 文件 sp contentProvider
    *
    * 二 sp问题
    *   1 不能跨进程同步  每个进程都有一个副本
    *   2 存储sp的文件过大问题
    *
    * 三内存对象序列化
    *   序列化 ：将对象的状态信息转为可以存储或传输的形式的过程
    *   １serializeble 大量的临时内存
    *   2 Parcelable  不能序列化硬盘上的文件
    *   总结：
    *       1 Serializeble是java的序列化方式，Parcelable是android特有的序列化方式
    *       2 在使用内存的时候，Parcelable比Serializable性能高
    *       3 Serializable在序列化的时候会产生大量的临时变量，从而引起频繁的GC
    *       4 parcelable不能使用在要将数据将存储在磁盘上的情况
    *
    * */
}
