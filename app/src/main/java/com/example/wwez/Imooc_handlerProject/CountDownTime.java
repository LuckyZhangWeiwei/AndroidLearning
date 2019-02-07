package com.example.wwez.Imooc_handlerProject;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.wwez.myapplication.R;

import java.lang.ref.WeakReference;

public class CountDownTime extends AppCompatActivity {

    TextView countdownTimeTextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down_time);
        countdownTimeTextview = findViewById(R.id.countdownTimeTextView);
        CountDownTimeHandler handler = new CountDownTimeHandler(this);
        Message msg = Message.obtain();
        msg.what = 100001;
        msg.arg1 = 10;
        handler.sendMessageDelayed(msg, 1000);
    }

    public static class CountDownTimeHandler extends Handler {
        final WeakReference<CountDownTime> mWeakReference;

        public CountDownTimeHandler(CountDownTime activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CountDownTime activity = mWeakReference.get();

            switch (msg.what) {
                case 100001:
                    int value = msg.arg1;
                    activity.countdownTimeTextview.setText(String.valueOf(value--));
                    if (value > 0) {
                        Message message = Message.obtain();
                        message.what = 100001;
                        message.arg1 = value;
                        sendMessageDelayed(message, 1000);
                    }
                    break;
            }
        }
    }
}
