package com.example.wwez.RecyclerView;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class SlideRecyclerView extends RecyclerView {
    private static final String TAG = "SlideRecyclerView";
    private static final int INVALID_POSITION = -1; // 触摸到的点不在子View范围内
    private static final int INVALID_CHILD_WIDTH = -1;  // 子ItemView不含两个子View
    private static final int SNAP_VELOCITY = 600;   // 最小滑动速度

    private VelocityTracker mVelocityTracker;   // 速度追踪器
    private int mTouchSlop; // 认为是滑动的最小距离（一般由系统提供）
    private Rect mTouchFrame;   // 子View所在的矩形范围
    private Scroller mScroller;
    private float mLastX;   // 滑动过程中记录上次触碰点X
    private float mFirstX, mFirstY; // 首次触碰范围
    private boolean mIsSlide;   // 是否滑动子View
    private ViewGroup mFlingView;   // 触碰的子View
    private int mPosition;  // 触碰的view的位置
    private int mMenuViewWidth;    // 菜单按钮宽度

    public SlideRecyclerView(Context context) {
        this(context, null);
    }

    public SlideRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e){
        int x = (int) e.getX();
        int y = (int) e.getY();
        obtainVelocity(e);
        switch(e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mFirstX = mLastX = x;
                mFirstY = y;
                mPosition = pointToPosition(x, y);
                if(mPosition != INVALID_POSITION) {
                    View view = mFlingView;
                    mFlingView = (ViewGroup) getChildAt(mPosition - ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition());
                    if (view != null && mFlingView != view && view.getScrollX() != 0) {
                        view.scrollTo(0, 0);
                    }
                    if (mFlingView.getChildCount() == 2) {
                        mMenuViewWidth = mFlingView.getChildAt(1).getWidth();
                    } else {
                        mMenuViewWidth = INVALID_CHILD_WIDTH;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                float yVelocity = mVelocityTracker.getYVelocity();
                if (
                        Math.abs(xVelocity) > SNAP_VELOCITY && Math.abs(xVelocity) > Math.abs(yVelocity)
                        ||
                        Math.abs(x - mFirstX) >= mTouchSlop && Math.abs(x - mFirstX) > Math.abs(y - mFirstY)
                ) {
                    mIsSlide = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                releaseVelocity();
                break;
        }
        return super.onInterceptTouchEvent(e);
    }

    private void releaseVelocity() {
        if (mVelocityTracker != null) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private int pointToPosition(int x, int y) {
        int firstPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        Rect frame = mTouchFrame;
        if (frame == null) {
            mTouchFrame = new Rect();
            frame = mTouchFrame;
        }

        final int count = getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            final View child = getChildAt(i);
            if (child.getVisibility() == View.VISIBLE) {
                child.getHitRect(frame);
                if (frame.contains(x, y)) {
                    return firstPosition + i;
                }
            }
        }
        return INVALID_POSITION;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(mIsSlide && mPosition != INVALID_POSITION) {
            float x = e.getX();
            obtainVelocity(e);
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mMenuViewWidth != INVALID_CHILD_WIDTH) {
                        float dx = mLastX - x;
                        if (
                                mFlingView.getScrollX() + dx <= mMenuViewWidth
                                &&
                                mFlingView.getScrollX() + dx > 0
                        ) {
                            mFlingView.scrollBy((int) dx, 0);
                        }
                        mLastX = x;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (mMenuViewWidth != INVALID_CHILD_WIDTH) {
                        int scrollX = mFlingView.getScrollX();
                        mVelocityTracker.computeCurrentVelocity(1000);
                        if (mVelocityTracker.getXVelocity() < -SNAP_VELOCITY) {
                            mScroller.startScroll(scrollX, 0, mMenuViewWidth - scrollX, 0, Math.abs(mMenuViewWidth - scrollX));
                        } else if (mVelocityTracker.getXVelocity() >= SNAP_VELOCITY) {
                            mScroller.startScroll(scrollX, 0, -scrollX, 0, Math.abs(scrollX));
                        } else if (scrollX >= mMenuViewWidth / 2) {
                            mScroller.startScroll(scrollX, 0, mMenuViewWidth - scrollX, 0, Math.abs(mMenuViewWidth - scrollX));
                        } else {
                            mScroller.startScroll(scrollX, 0, -scrollX, 0, Math.abs(scrollX));
                        }
                        invalidate();
                    }
                    mMenuViewWidth = INVALID_CHILD_WIDTH;
                    mIsSlide = false;
                    mPosition = INVALID_POSITION;
                    releaseVelocity();
                    break;
            }
            return true;
        } else {
            closeMenu();
            releaseVelocity();
        }
        return super.onTouchEvent(e);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mFlingView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }


    public void closeMenu() {
        if (mFlingView != null && mFlingView.getScrollX() != 0) {
            mFlingView.scrollTo(0, 0);
        }
    }

    private void obtainVelocity(MotionEvent e) {
        if(mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(e);
    }
}
