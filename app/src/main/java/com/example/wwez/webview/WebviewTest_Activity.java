package com.example.wwez.webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.wwez.myapplication.R;

public class WebviewTest_Activity extends AppCompatActivity {

    private WebView mWebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_test_);
        mWebview = findViewById(R.id.webviewTest);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.loadUrl("https://luckyzhangweiwei.github.io/cnode3/");

        mWebview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });

        mWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        mWebview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
               new HttpThread(url).start();
            }
        });

//        mWebview.setWebChromeClient(new WebChromeClient());
//        mWebview.loadUrl("file:///android_asset/cnode3/index.html");
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        mWebview.loadUrl("javascript:window.goback();");
    }
}


