package com.example.wwez.webview;

import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wwez.myapplication.R;

public class Webview_MainActivity extends AppCompatActivity implements JSBridge{

    private WebView mWebview;
    private TextView mTextview;
    private Button mBtn;
    private EditText mEditText;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview__main);
        initWidgets(savedInstanceState);
        Log.d("mainactivitry","mainactivity");
    }

    private void initWidgets(Bundle savedInstanceState) {
        mWebview = findViewById(R.id.id_webview);
        mTextview = findViewById(R.id.tv_result);
        mBtn = findViewById(R.id.button);
        mEditText = findViewById(R.id.edittext);
        mHandler = new Handler();

        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.addJavascriptInterface(new JSInterface(this),"imoocLauncher");
//        mWebview.loadUrl("file:///android_asset/index.html");
        mWebview.loadUrl("https://luckyzhangweiwei.github.io/TestDeploy/");

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = mEditText.getText().toString();
                mWebview.loadUrl("javascript:if(window.remote){window.remote('"+str+"');}");
            }
        });
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            mWebview.setWebContentsDebuggingEnabled(true);
        }
    }

    @Override
    public void setTextViewValue(final String str) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mTextview.setText(str);
            }
        });
    }
}
