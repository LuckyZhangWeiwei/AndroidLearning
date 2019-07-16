package com.example.wwez.geekband.test19;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.wwez.myapplication.R;

import java.lang.ref.WeakReference;

/*
* handler 主要处理 ui线程和其他线程 通讯 ，定时任务等
*
* */


public class Test19Activity extends AppCompatActivity {

//    Handler mHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            // 接收消息
//
//            switch (msg.what) {
//                case 888888:
//                    int value = (int) msg.obj;
//                    mTextView.setText(String.valueOf(value/1000));
//
//                    msg = Message.obtain();
//                    msg.arg1 = 0;
//                    msg.arg2 = 1;
//                    msg.what = 888888;
//                    msg.obj = value - 1000;
//
//                    if(value > 0) {
//                        sendMessageDelayed(msg, 1000);
//                    }
//
//                    break;
//            }
//        }
//    };

    TextView mTextView;
    TestHandler mTestHandler = new TestHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test19);

        mTextView = findViewById(R.id.tv_textview);

//        Message message = mHandler.obtainMessage();  // 相对于直接new 出message ，此种方法容易释放，推荐
//
//        message.arg1 = 0;
//        message.arg2 = 1;
//        message.what = 888888;
//        message.obj = 10000;
//
//        mHandler.sendMessageDelayed(message, 1000);


        Message message = mTestHandler.obtainMessage();  // 相对于直接new 出message ，此种方法容易释放，推荐

        message.arg1 = 0;
        message.arg2 = 1;
        message.what = 888888;
        message.obj = 10000;

        mTestHandler.sendMessageDelayed(message, 1000);
    }

    public TextView getmTextView() {
        return mTextView;
    }

    public static class TestHandler extends Handler {

        final WeakReference<Test19Activity> mActivity;

        TestHandler(Test19Activity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Test19Activity test19Activity = mActivity.get();
            // 接收消息
            if (msg.what == 888888) {
                int value = (int) msg.obj;
                test19Activity.getmTextView().setText(String.valueOf(value / 1000));

                // UI 线程 默认有mainlooper

                msg = Message.obtain();
                msg.arg1 = 0;
                msg.arg2 = 1;
                msg.what = 888888;
                msg.obj = value - 1000;
                if (value > 0) {
                    sendMessageDelayed(msg, 1000);
                }
            }
        }
    }
}
