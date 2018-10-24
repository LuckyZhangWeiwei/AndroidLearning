package com.example.wwez.meituanFloatSection;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MeituanScrollView extends ScrollView {

    private OnScrollListener onScrollListener;
    public MeituanScrollView(Context context) {
        super(context);
    }

    public MeituanScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeituanScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnScrollListener(OnScrollListener mOnScrollListener) {
        this.onScrollListener = mOnScrollListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(onScrollListener != null) {
            onScrollListener.onScroll(t);
        }
    }

    public interface OnScrollListener {
        void onScroll(int scrollY);
    }
}
