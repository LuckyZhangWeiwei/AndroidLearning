package com.example.wwez.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("wwez","收到短信");
        Bundle bundle = intent.getExtras();
        Object[] objs = (Object[])bundle.get("pdus");
        for(Object obj : objs){
          SmsMessage message = SmsMessage.createFromPdu((byte[])obj);
          String address = message.getOriginatingAddress();
            Toast.makeText(context,"收到短信:"+address, Toast.LENGTH_SHORT).show();
            Log.d("wwez","收到短信:"+address);
        }
    }
}
