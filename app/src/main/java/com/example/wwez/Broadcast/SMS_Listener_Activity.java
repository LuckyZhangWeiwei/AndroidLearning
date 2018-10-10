package com.example.wwez.Broadcast;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.wwez.myapplication.R;

public class SMS_Listener_Activity extends AppCompatActivity {

    private SMSReceiver Receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms__listener_);
        Receiver = new SMSReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");

        registerReceiver(Receiver,filter);
    }
}
