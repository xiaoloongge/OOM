package com.oomdemo.oom;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.oomdemo.R;

import java.lang.ref.SoftReference;

public class Main2Activity extends AppCompatActivity {


    private  MyHandler handler = new MyHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        handler.sendEmptyMessage(1000);
    }

    static class MyHandler extends Handler{

        private final SoftReference<Activity> activitySoftReference;

        public MyHandler(Activity activity){
            activitySoftReference = new SoftReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (activitySoftReference != null){

            }
        }
    }
}

