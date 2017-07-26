package com.oomdemo.demo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.oomdemo.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.memory);
        //获取系统内存大小
        //getSystemMomery();

    }


    /*
    * 获取内存大小
    * */
    private void getSystemMomery() {
        //系统分配的APP的大小
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int memoryClass = manager.getMemoryClass();//以m为单位
        int largeMemoryClass = manager.getLargeMemoryClass();
        //所用的内存数量
        float totalMeo = Runtime.getRuntime().totalMemory() * 1.0f / (1024 * 1024);
        float freeMeo = Runtime.getRuntime().freeMemory() * 1.0f / (1024 * 1024);
        float totMeO = Runtime.getRuntime().maxMemory() * 1.0f / (1024 * 1024);
        textView.setText(memoryClass+"   totalMeo="+totalMeo +" =="+freeMeo + "  totmeo"+totalMeo);
    }




    /*
    *
    * 比校字符串
    *
    * */
    public void strorbuilder(View view){
        init();
        strdemo1();
        strdemo2();
    }

    private int rowlength = 20;
    private int length = 300;
    private int[][] intMatrix = new int[rowlength][length];
    private Random random = new Random();

    public void init(){
        for (int i = 0; i < rowlength; i++) {
            for (int j = 0; j < length; j++) {
                intMatrix[i][j] = random.nextInt();
            }
        }
    }
    public void strdemo1(){

        String rowStr = "";
        long time = System.currentTimeMillis();
        Log.i("TAG", "String　：　start: " );
        for (int i = 0; i < rowlength; i++) {
            for (int j = 0; j < length; j++) {
                rowStr = rowStr + intMatrix[i][j];
                rowStr = rowStr + ",";
            }
            Log.i("TAG", "String: ");
        }
        Log.i("TAG", "String　：strdemo: "+(System.currentTimeMillis()-time));
    }


    public void strdemo2(){
        StringBuilder str = new StringBuilder();
        long time = System.currentTimeMillis();
        Log.i("TAG", "StringBuilder : start: ");
        for (int i = 0; i < rowlength; i++) {
            for (int j = 0; j < length; j++) {
                str.append(intMatrix[i][j]);
                str.append(",");
            }
            Log.i("TAG", "StringBuilder: ");
        }
        Log.i("TAG", "StringBuilder :strdemo: "+(System.currentTimeMillis()-time));
    }


    /*
    *
    * 内存抖动
    *
    * */

    private int memLength = 10;
    private int len = 420000;

    private static final String TAG = "TAG";
    public void memoryShake(View view){

        Log.i(TAG, "memoryShake: start");
        for (int i = 0; i < memLength; i++) {
            String[] str = new String[len];
            for (int j = 0; j < len; j++) {
                str[j] = String.valueOf(random.nextDouble());
            }
            Log.i(TAG, "memoryShake: "+i);
        }
        Log.i(TAG, "memoryShake: end");
    }

    /*
    *
    * 内存泄露
    *
    * */

    public void memoryLeak(View view){

        startActivity(new Intent(this,SencodActivity.class));
    }


    /*
    * 软引用
    * */

    public void soft(View v){
        startActivity(new Intent(this,SoftActivity.class));
    }

}
