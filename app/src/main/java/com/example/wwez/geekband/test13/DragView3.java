package com.example.wwez.geekband.test13;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class DragView3 extends FrameLayout {

    private ViewDragHelper mViewDragHelper;

    private View mMenuView, mMainView;

    public DragView3(@NonNull Context context) {
        this(context, null);
    }

    public DragView3(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DragView3(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback(){

            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return mMainView == child;
            }

            // 处理水平滑动
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }
            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (mMainView.getLeft() < 500) {
                    //关闭菜单
                    //相当于Scroller的startScroll方法
                    mViewDragHelper.smoothSlideViewTo(mMainView, 0, 0);
                    ViewCompat.postInvalidateOnAnimation(DragView3.this);
                } else {
                    //打开菜单
                    mViewDragHelper.smoothSlideViewTo(mMainView, 300, 0);
                    ViewCompat.postInvalidateOnAnimation(DragView3.this);
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView = getChildAt(0);
        mMainView = getChildAt(1);
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
