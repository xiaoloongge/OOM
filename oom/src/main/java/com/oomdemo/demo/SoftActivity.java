package com.oomdemo.demo;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oomdemo.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;

public class SoftActivity extends AppCompatActivity {


    private TextView tv;
    private InputStream open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft);

        initImageView();
    }


    private String strongref;
    private SoftReference<String> softReference;

//    public void soft() {
//        strongref = String.valueOf(Math.random());
//        //只要内存不够就可以被回收
//        softReference =
//                new SoftReference<String>(String.valueOf(Math.random()));
//    }

    /*
    *
    * 图片的问题
    * 注意临时bitmap对象的回收和浪费
    * 加载bitmap 注意缩放 解码格式 还有局部加载
    * */


    /*
    *
    * 初始化图片的大小
    *
    * */

    private ImageView iv;
    private ImageView image;
    private Bitmap bitmap = null;
    private File file = null;
    private int width, height;
    private int addpx = 0;

    private void initImageView() {
        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);
        //获取展示的尺寸
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        //获取屏幕的宽高像素
        width = dm.widthPixels;
        height = dm.heightPixels;

    }


    /*
    *
    *
    * 图片未压缩的大小
    *
    * */
    public void btn1(View view) {
        //tv.setText("图片大小有 ： " + getBitmap().getByteCount());
        setSize(getBitmap().getByteCount());
        iv.setImageBitmap(getBitmap());
    }

    /*
    * 读取Bitmap
    *
    * */
    public Bitmap getBitmap() {
        //获取图片
        AssetManager assets = getAssets();
        try {
            open = assets.open("123.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(open);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (open!=null){
                    open.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /*
    *
    * 图片压缩时的大小
    * */

    public void btn2(View view) {

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            //告诉系统暂时不读取边界
            options.inJustDecodeBounds = true;

            AssetManager manager = getAssets();
            open = manager.open("123.jpg");
            //通过这个方法以后options里面就有值了 第一个参数是输入流，第三个参数是选择参数
            BitmapFactory.decodeStream(open, null, options);

            //计算图片缩放比例
            int outWidth = options.outWidth; //拿到图片的宽高
            int outHeight = options.outHeight;


            //计算缩放比例
            int scale = 2;
            while (true){
                //图片的宽度和屏幕的宽度做比较
                if (outWidth / scale < width){
                    break;
                }
                scale *= 2;
            }
            scale /= 2;


            //第二个options对象
            options = new BitmapFactory.Options();
            //压缩比例
            options.inSampleSize = scale;
            //设置图片像素
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            //options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = BitmapFactory.decodeStream(open, null, options);
            //显示图片大小
            setSize(bitmap.getByteCount());
            //展示图片
            iv.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (open != null){
                try {
                    open.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void setSize(long number){
        tv.setText("图片大小"+Double.valueOf(number) / (1024*1024)+"m");
    }

    /*
    *
    * 更改像素
    *
    * */
    public void btn3(View view){
        try {
            //设置图片像素格式
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            //读取图片
            AssetManager assets = getAssets();
            open = assets.open("123.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(this.open, null, options);

            //显示图片大小
            setSize(bitmap.getByteCount());
            //展示图片
            iv.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (open != null){
                try {
                    open.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /*
    *
    * 局部加载
    *
    * */
    public void btn4(View view){
        try {
            part();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void btn5(View view){
        try {
            addpx += 20;
            part();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btn6(View view){
        try {
            addpx -= 20;
            part();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //局部加载图片
    public void part() throws IOException {

        AssetManager assets = getAssets();
        InputStream open = assets.open("123.jpg");
        //获得图片的宽高
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(open,null,options);


        //获取图片的宽高
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        //设置显示图片的中心区域
        //BitmapRegionDecoder用来加载图片的某块区域
        BitmapRegionDecoder bitmapRegionDecoder
                = BitmapRegionDecoder.newInstance(open, false);

        //设置加载区域范围
        options = new BitmapFactory.Options();
        Rect rect = new Rect();
        rect.left = outWidth / 2 - width/2+addpx;
        rect.top = outHeight / 2 - height/2;
        rect.right = outWidth / 2 + width / 2+addpx;
        rect.bottom = outHeight / 2 + height /2;
        //加载的区域大小
        bitmap = bitmapRegionDecoder.decodeRegion(rect,options);


        setSize(bitmap.getByteCount());
        iv.setImageBitmap(bitmap);
    }



    //当内存不够时回调的方法
    private static final String TAG = "memory";

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        Log.i(TAG, "onTrimMemory: " + level);

        /*
        * level的等级
        * TRIM_MEMORY_BACKGROUND  程序在后台
        * TRIM_MEMORY_COMPLETE    下次就会清理
        * TRIM_MEMORY_MODERATE    内存还行
        * TRIM_MEMORY_RUNNING_CRITICAL  内存还能用
        * TRIM_MEMORY_RUNNING_LOW   内存低了
        * TRIM_MEMORY_UI_HIDDEN  ui已经不可见了
        *
        * */
    }
}
