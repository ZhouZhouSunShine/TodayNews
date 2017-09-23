package com.example.fanjinwei_firstpriject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by 范晋炜 on 2017/8/16 0016.
 * com.example.fanjinwei_firstpriject
 * WebViewActivity
 */


public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView main_web;
    private ProgressBar web_progressBar;
    private ImageView web_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //显示当前页面布局
        setContentView(R.layout.webview);
        //获取资源ID
        main_web = (WebView) findViewById(R.id.web_view);
        web_back = (ImageView) findViewById(R.id.web_back);
        web_progressBar = (ProgressBar) findViewById(R.id.web_progress);
        //接收传过来的值
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        web_back.setOnClickListener(this);
        main_web.loadUrl(url);
        main_web.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                web_progressBar.setProgress(newProgress);
            }
        });
    }

    @Override
    public void onClick(View v) {
        this.finish();
    }
}
