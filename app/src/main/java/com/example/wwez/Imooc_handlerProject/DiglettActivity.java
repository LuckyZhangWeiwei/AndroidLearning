package com.example.wwez.Imooc_handlerProject;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wwez.myapplication.R;

import java.lang.ref.WeakReference;
import java.util.Random;

public class DiglettActivity extends AppCompatActivity {
    TextView resultTextView;
    ImageView diglettImageView;
    Button startButton;

    public int[][] mPosition = new int[][]{
            {342, 180},{432, 880},
            {521, 256},{429, 780},
            {456, 976},{145, 665},
            {123, 678},{564, 567},
    };

    private int mTotalCount;
    private int mSuccessCount;
    public static final int MAX_COUNT = 10;

    private DiglettHandler mHandle = new DiglettHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diglett);

        resultTextView = findViewById(R.id.text_view);
        diglettImageView  =findViewById(R.id.imageView);
        setTitle("打地鼠");

        diglettImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setVisibility(View.GONE);
                mSuccessCount ++ ;
                resultTextView.setText("打到了"+mSuccessCount+"只，共"+mTotalCount+"只");
                return false;
            }
        });
        startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    private void start() {
        resultTextView.setText("开始啦");
        startButton.setText("游戏中。。。");
        startButton.setEnabled(false);
        next(0);
    }

    private void next(int delayTime) {
        int position = new Random().nextInt(mPosition.length);
        Message message = Message.obtain();
        message.what = 123;
        message.arg1 = position;

        mHandle.sendMessageDelayed(message, delayTime);
        mTotalCount ++;
    }

    public static class DiglettHandler extends Handler {
        public final WeakReference<DiglettActivity> mWeakReference;
        public DiglettHandler(DiglettActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DiglettActivity activity = mWeakReference.get();
            switch (msg.what) {
                case 123:
                    if(activity.mTotalCount > MAX_COUNT) {
                        activity.clear();
                        Toast.makeText(activity, "地鼠打完了", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int position = msg.arg1;
                    activity.diglettImageView.setX(activity.mPosition[position][0]);
                    activity.diglettImageView.setY(activity.mPosition[position][1]);
                    activity.diglettImageView.setVisibility(View.VISIBLE);

                    int randomTime = new Random().nextInt(500) + 500;
                    activity.next(randomTime);
                    break;
            }
        }
    }

    private void clear() {
        mTotalCount = 0;
        mSuccessCount = 0;
        diglettImageView.setVisibility(View.GONE);
        startButton.setTag("点击开始");
        startButton.setEnabled(true);
    }
}
