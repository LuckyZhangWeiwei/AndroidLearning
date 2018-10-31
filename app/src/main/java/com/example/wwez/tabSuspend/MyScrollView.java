package com.example.wwez.tabSuspend;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
    private OnScrollListener listener;
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnScrollListener {
        void onScroll(int scrollY);
    }

    public void OnScrollListener(OnScrollListener listener) {
        this.listener = listener;
    }
    @Override
    public void computeScroll() {
        super.computeScroll();
        if(listener!=null) {
            listener.onScroll(getScrollY());
        }
    }
}
