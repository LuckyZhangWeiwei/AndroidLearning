package com.example.wwez.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Avtivity_8_5 extends AppCompatActivity {

    private boolean flag = true;
    Handler handler = new Handler();
    TextView tv ;
    private MyRunnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avtivity_8_5);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv=findViewById(R.id.txt_view);
        runnable = new MyRunnable();
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        new MyTh().start();
    }

    class MyTh extends Thread{
        @Override
        public void run() {
            for(int i=1;i<=100;i++){
                runnable.setI(i);
                handler.post(runnable);

                if(!flag) break;
                try{
                    Thread.sleep(3000);
                    Log.d("wwez", "打开了"+ i + "%");
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    class MyRunnable implements Runnable{
        public void setI(int i) {
            this.i = i;
        }
        public int i;
        @Override
        public void run() {
            boolean result = Looper.getMainLooper() == Looper.myLooper();
            Log.d("wwez", "runnable..."+result);
            tv.setText("打开了"+ i + "%");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag = false;
        handler.removeCallbacks(runnable);
    }
}
