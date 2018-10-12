package com.example.wwez.webview;

import android.webkit.JavascriptInterface;

public class JSInterface {
    private JSBridge IJSBridge;
    public JSInterface(JSBridge ijsBridage) {
        IJSBridge = ijsBridage;
    }
    /*
    * 不在主线程执行
    * */
    @JavascriptInterface
    public void setValue(String value){
        IJSBridge.setTextViewValue(value);
    }
}
