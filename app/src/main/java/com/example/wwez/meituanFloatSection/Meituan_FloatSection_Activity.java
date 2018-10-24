package com.example.wwez.meituanFloatSection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.example.wwez.myapplication.R;

public class Meituan_FloatSection_Activity extends AppCompatActivity implements MeituanScrollView.OnScrollListener {

    private MeituanScrollView meituanScrollView;
    private LinearLayout mBuyLayout;
    private LinearLayout mTopBuyLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meituan__float_section_);
        meituanScrollView = findViewById(R.id.scrollview);
        mBuyLayout = findViewById(R.id.buy);
        mTopBuyLayout = findViewById(R.id.top_buy_layout);
        meituanScrollView.setOnScrollListener(this);
        findViewById(R.id.parent_layout).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                onScroll(meituanScrollView.getScrollY());
            }
        });
    }

    @Override
    public void onScroll(int scrollY) {
        int mBuyLayout2ParentTop = Math.max(scrollY, mBuyLayout.getTop());
        mTopBuyLayout.layout(0, mBuyLayout2ParentTop, mTopBuyLayout.getWidth(), mBuyLayout2ParentTop + mTopBuyLayout.getHeight());
    }
}
