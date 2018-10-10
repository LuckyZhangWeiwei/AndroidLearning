package com.example.wwez.Broadcast;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.wwez.myapplication.R;

public class Order_UnOrder_Broadcast_Activity extends AppCompatActivity {

    private Intent intent;
    private Receiver1 receiver1;
    private Receiver2 receiver2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__un_order__broadcast_);
        intent = new Intent();
        receiver1 = new Receiver1();
        receiver2 = new Receiver2();
        intent.setAction("com.wwez.guangbo");

        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("com.wwez.guangbo");
        filter1.setPriority(1);

        IntentFilter filter2 = new IntentFilter();
        filter2.addAction("com.wwez.guangbo");
        filter2.setPriority(2);

        registerReceiver(receiver1, filter1);
        registerReceiver(receiver2, filter2);
    }

    public void ordered_onClick(View view) {
        sendOrderedBroadcast(intent,null);
    }

    public void unordered_onClick(View view) {
        sendBroadcast(intent);
    }
}
