package com.example.wwez.Broadcast;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.wwez.myapplication.R;

public class BoardcastActivity extends AppCompatActivity {

    private MyBroadcastReceiver myBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myBroadcastReceiver = new MyBroadcastReceiver();
        setContentView(R.layout.activity_boardcast);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.wwez.myreceiver");
        registerReceiver(myBroadcastReceiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }
    public void onClick(View view){
        Intent intent = new Intent();
        intent.setAction("com.wwez.myreceiver");
        sendBroadcast(intent);
    }
}
