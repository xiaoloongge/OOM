package com.oomdemo.demo;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.oomdemo.R;

import java.lang.ref.SoftReference;

public class SencodActivity extends AppCompatActivity {

    private Thread1 t;

//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//        }
//    };

    private Activity activity;

     static class MyHanddler extends Handler{

         private final SoftReference<Activity> activitySoftReference;

         public MyHanddler(Activity activity){
             activitySoftReference = new SoftReference<>(activity);
         }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (activitySoftReference != null){

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sencod);

        t = new Thread1();
        t.start();
        /*Thread2 t2 = new Thread2();
        t2.start();*/
    }



    @Override
    protected void onPause() {
        super.onPause();
        //t.stop();
        //t.interrupt();

    }

    public void say(){

    }

    static class Thread1 extends Thread{
        @Override
        public void run() {
            super.run();

                try {
                   SystemClock.sleep(1000*60*5);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}
