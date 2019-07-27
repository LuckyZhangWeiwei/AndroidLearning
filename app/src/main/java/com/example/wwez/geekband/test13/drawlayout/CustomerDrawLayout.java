package com.example.wwez.geekband.test13.drawlayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class CustomerDrawLayout extends LinearLayout {

    int mLastX;

    Scroller mScroller = new Scroller(getContext());

    private static final int  MAX_WIDTH = 500;

    private VelocityTracker mVelocityTracker;

    private boolean isScrollLeft = false;

    public CustomerDrawLayout(Context context) {
        super(context);
        init(context);
    }

    public CustomerDrawLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomerDrawLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        obtainVelocity(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mLastX = x;
                if (x < 50) {
                    ((LinearLayout)this.getParent()).scrollBy(-50, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (x < 50) {
                    mScroller.startScroll(x, 0, 50, 0);
                } else {
                    if(-(((LinearLayout)this.getParent())).getScrollX() > MAX_WIDTH/2) {
                        mScroller.startScroll(x, 0, -MAX_WIDTH, 0);
                    } else {
                        mScroller.startScroll(x, 0, -10, 0);
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(-(((LinearLayout)this.getParent())).getScrollX() <= MAX_WIDTH) {
                    int offsetX = x - mLastX;
                    ((LinearLayout)this.getParent()).scrollBy(-offsetX, 0);
                    isScrollLeft = false;
                }
                else if((x - mLastX) <= 0) {
                    int offsetX = x - mLastX;
                    ((LinearLayout)this.getParent()).scrollBy(-offsetX, 0);
                    isScrollLeft = true;
                }
                mLastX = x;

                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if(-(((LinearLayout)this.getParent())).getScrollX() > MAX_WIDTH/2) {
                ((LinearLayout)this.getParent()).scrollTo(-MAX_WIDTH, 0);
            } else {
                ((LinearLayout)this.getParent()).scrollTo(-10, 0);
            }
//            ((LinearLayout)this.getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }


    private void obtainVelocity(MotionEvent e) {
        if(mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(e);
    }
}
