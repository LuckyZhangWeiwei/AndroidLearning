package com.example.wwez.tabSuspend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wwez.myapplication.R;

public class tabSuspend_MainActivity extends AppCompatActivity implements MyScrollView.OnScrollListener{

    private MyScrollView mScrollView;
    private LinearLayout ll_tab;
    private ImageView iv_pic;
    private int picBottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_suspend__main);

        mScrollView = findViewById(R.id.mScrollView);
        mScrollView.OnScrollListener(this);

        ll_tab = findViewById(R.id.ll_tab);
        iv_pic = findViewById(R.id.iv_pic);

        findViewById(R.id.ll_main).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                onScroll(mScrollView.getScrollY());
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) {
            picBottom = iv_pic.getBottom();
        }
    }

    @Override
    public void onScroll(int scrollY) {
        int top = Math.max(scrollY, picBottom);
        ll_tab.layout(0, top, ll_tab.getWidth(), top + ll_tab.getHeight());
    }
}
