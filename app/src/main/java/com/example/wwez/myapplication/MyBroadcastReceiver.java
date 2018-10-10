package com.example.wwez.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver{
    public MyBroadcastReceiver(){
        System.out.println("已执行。。。。");
    }
    @Override
    public void onReceive(Context context, Intent intent) {
//        String phoneNum = getResultData();
//        Log.d("phoneNum:",phoneNum);
//        setResultData("123123"+phoneNum);
        Toast.makeText(context,"收到广播", Toast.LENGTH_SHORT).show();
        Log.d("phoneNum:","asdf");
    }
}
