package com.atguigu.myapplication.base;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2017/7/22.
 */

public class ViewDraw extends View {


    public ViewDraw(Context context) {
        super(context);
    }


    /*
    *
    * View 树的绘制流程
    *
    * measure -> layout -> draw
    *
    * 一 measure
    *   自上而下进行遍历
    *   1 viewGroup.LayoutParams 布局宽高
    *
    *   2 MeasureSpec测量规格 32位 前2位模式  后30位尺寸大小
    *
    * 二 测量的重要方法
    *
    *    测量从ViewGroup开始遍历子元素 结合子view layoutParams定义子类的测量规则
    *
    *
    * layout
    *
    * draw两个容易混淆的地方？
    * invalidate() 大小没变化不会触发layout方法 只会调用 draw方法
    * requestLayout() 尺寸大小改变后 不会调用draw方法会触发  measure layout
    *
    *
    *
    * */



    /*
    *
    *
    * 事件分发
    * 一为什么会有事件分发机制
    *   android上面的view是树形结构的，view可能会重叠在一起，当我们点击的地方有多个view都可以响应的时候，
    *   这个点击事件应该给谁呢？为了解决这一个问题，就有了事件分发机制
    *
    *
    * phonewindow window的实现类 decorView 背景显示 标题栏 事件分发
    *
    * 三个重要方法
    * dispatchTouchEvent
    * onInteceptTouchEvent
    * onTouchEvent
    *
    * 事件分发流程
    * Activity -> PhoneWindow -> DecorView -> ViewGroup ->View
    *
    *
    * */

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //让父方法调用setMeasuredDimension()
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }
}
