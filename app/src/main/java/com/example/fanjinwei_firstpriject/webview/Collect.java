package com.example.fanjinwei_firstpriject.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.fanjinwei_firstpriject.R;

/**
 * Created by 范晋炜 on 2017/8/17 0017.
 * com.example.fanjinwei_firstpriject.webview
 * Collect
 */


public class Collect extends AppCompatActivity{

    private WebView collect_web;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //显示当前页面布局
        setContentView(R.layout.collect);
        collect_web = (WebView) findViewById(R.id.collect_web);

        //WebView加载web资源
        collect_web.loadUrl("http://www.nuoren.net/");
                    //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        collect_web.setWebViewClient(new WebViewClient(){
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            // TODO Auto-generated method stub
                            //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                            view.loadUrl(url);
                            return true;
                        }
                    });
    }
}
