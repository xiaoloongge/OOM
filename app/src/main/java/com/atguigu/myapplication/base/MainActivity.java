package com.atguigu.myapplication.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.atguigu.myapplication.R;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "xiaoloongge";
    private WebView wv;

    /*
    * 常见的坑
    * ① Android API 16 没有正确限制使用webview.addJavaScripteInterface方法，远程攻击者
    * 使用JavaReflectionAPI利用执行任意java对象的方法
    *
    * ② webview在布局文件中的使用：webView写在其它容器中时　（先销毁webview再销毁activity）
    * ③ jsbridge
    * ④ webviewClient.onPageFinished->WebChromeClient.onProgressChanged
    * ⑤ 后台耗电 ：
    * ⑥ webview硬件加速导致页面渲染问题－白屏展示（关闭硬件加速）
    *
    * 内存泄漏
    * ① 独立进程，简单暴力，不过可能涉及到进程间通信
    * ② 动态添加webview，对传入webview中使用的context使用弱引用，动态添加webview意思在布局
    * 创建个viewGroup用来放置webview，Activity创建时add进来，在Activity停止时remove掉
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        initWebView();


    }



    /*
    	//创建CookieSyncManager
	    CookieSyncManager.createInstance(context);
	    //得到CookieManager
	    CookieManager cookieManager = CookieManager.getInstance();
	    //得到向URL中添加的Cookie的值
	    String cookieString = LoginFragment.requestHead.get("Set-Cookie");
	    //使用cookieManager..setCookie()向URL中添加Cookie
	    cookieManager.setCookie(url, cookieString);
	    CookieSyncManager.getInstance().sync();
    * */

    private void initWebView() {
        //初始化button控件
        Button btn1 = (Button) findViewById(R.id.btn1);

        btn1.setOnClickListener(this);

        Button btn2 = (Button) findViewById(R.id.btn2);

        btn2.setOnClickListener(this);

        wv = (WebView) findViewById(R.id.wv);


        //在API 21之后，WebView实现了自动同步Cookie


        WebSettings settings = wv.getSettings();
        //支持JS
        settings.setJavaScriptEnabled(true);


        //加载本地assets目录下的静态网页
        wv.loadUrl("file:///android_asset/123.html");
        //第一个参数把自身传给js 第二个参数是当前对象的一个名字
        wv.addJavascriptInterface(this, "android");

        wv.setWebViewClient(new WebViewClient() {

//            //此方法没有触发
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view,
//                                                    WebResourceRequest request) {
//                //return true 表示当前url即使是重定向url也不会再执行（除了在return true之前使用webview.loadUrl(url)除外，因为这个会重新加载）
//                //return false  表示由系统执行url，直到不再执行此方法，即加载完重定向的ur（即具体的url，不再有重定向）
//                Log.i(TAG, "shouldOverrideUrlLoading: " + view.getUrl());
//                return sup;
//            }


            @Override
            public void onPageFinished(WebView view, String url) {
                //获取Cookin
                super.onPageFinished(view, url);
                Log.i(TAG, "onPageFinished: ");

                //通过scheme跳转activity

            }



            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i(TAG, "onPageStarted: " + url);

//                if (url.contains("aa")) {
//                    //Uri uri = Uri.parse(url);
//                    Intent intent = new Intent(Intent.ACTION_VIEW,
//                            Uri.parse(url));
//                    startActivity(intent);
//                }
//
//                CookieManager instance = CookieManager.getInstance();
//                String cookie = instance.getCookie(url);
//                Log.i(TAG, "onPageStarted: "+cookie);
            }

//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request,
//                                        WebResourceError error) {
//                super.onReceivedError(view, request, error);
//
//                Log.i(TAG, "onReceivedError: "
//                        +error.getErrorCode()+error.getDescription());
//
//            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.i(TAG, "onReceivedError: " + errorCode + "  " + description);
                if (errorCode == ERROR_UNSUPPORTED_SCHEME) {
                    Log.i(TAG, "onReceivedError: " + "true");
                    //startActivity(new Intent(MainActivity.this,SecondActivity.class));
                    //停止加载错误页面，再次加载成功页面
                    view.stopLoading();
                    view.loadUrl("file:///android_asset/error.html");
                }
            }
        });
    }


    private String name = "小龙哥棒棒哒";

    @Override
    public void onClick(View v) {

        //这个是button的点击事件
        switch (v.getId()) {
            case R.id.btn1:
                wv.loadUrl("javascript:message()");
                break;
            case R.id.btn2:
//                在android调用js有参的函数的时候参数要加单引号
                wv.loadUrl("javascript:message2('" + name + "')");
                break;
        }
    }



    /*
    *
    * 下面的两个方法是让网页来调的
    *
    * */

    //这个注解必须加 因为 兼容问题
    @JavascriptInterface
    public void setMessage() {
        Toast.makeText(this, "我弹", Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void setMessage(String name) {
        Toast.makeText(this, "我弹弹" + name, Toast.LENGTH_SHORT).show();
    }
}
