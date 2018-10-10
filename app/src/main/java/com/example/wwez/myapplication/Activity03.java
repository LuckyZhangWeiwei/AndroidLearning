package com.example.wwez.myapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Activity03 extends AppCompatActivity {

    private TextView tv_txt;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv_txt.setText((String)msg.obj);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_03);
        tv_txt = findViewById(R.id.tv_txt);
        MyThread mythread = new MyThread();
        mythread.start();
    }
    class MyThread extends Thread{
        @Override
        public void run(){
          try{
              Thread.sleep(6000);
              String str = "我是网络数据";
              Message msg = new Message();
              msg.obj = str;
              handler.sendMessage(msg);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
        }
    }
}
