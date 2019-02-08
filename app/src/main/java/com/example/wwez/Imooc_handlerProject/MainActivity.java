package com.example.wwez.Imooc_handlerProject;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.wwez.myapplication.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_3);

        final TextView textView = findViewById(R.id.textView);

        findViewById(R.id.Button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, com.example.wwez.Imooc_Listview.RequestDataActivity.class));
            }
        });

        final Handler hander = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                /*
                * 主线程接到子线程发出来的消息，进行处理
                * */
                if(msg.what == 1001) {
                    textView.setText("immoc");
                } else if(msg.what == 1002) {
                    int arg1 = msg.arg1;
                    int arg2 = msg.arg2;
                    Object o = msg.obj;
                }
            }
        };

        findViewById(R.id.Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(2000);
                            /**
                             * 通知UI 更新
                             * */
                            hander.sendEmptyMessage(1001);

                            Message message = Message.obtain();
                            message.what = 1002;
                            message.arg1 = 1003;
                            message.arg2 = 1004;
                            message.obj = MainActivity.this;
                            hander.sendMessage(message);

//                            hander.sendMessageAtTime(message, SystemClock.uptimeMillis() + 3000);

                            hander.post(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText("immoc2");
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}
