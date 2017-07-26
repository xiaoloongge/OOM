package com.oomdemo.demo;

import android.app.Application;
import android.content.Context;


import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Administrator on 2017/3/31.
 */

public class MyApplication extends Application {

    private  static RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(){
        return refWatcher;
    }


//    public static Context getContext(){
//        return
//    }
}
