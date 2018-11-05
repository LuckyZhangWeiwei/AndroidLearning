package com.example.wwez.IndexList;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListAdapter;
import android.widget.ListView;

public class IndexableListView extends ListView {
    private boolean mIsFastScrollEnabled = false;
    private IndexScroller mScroller = null;
    private GestureDetector mGestureDetector = null;

    public IndexableListView(Context context) {
        super(context);
    }

    public IndexableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IndexableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isFastScrollEnabled(){
        return mIsFastScrollEnabled;
    }

    public void setFastScrollEnabled(boolean mIsFastScrollEnabled) {
        this.mIsFastScrollEnabled = mIsFastScrollEnabled;
        if(mIsFastScrollEnabled) {
            if(mScroller == null) {
                mScroller = new IndexScroller(getContext(),this);
            }
        } else {
            if(mScroller != null ){
                mScroller.hide();
                mScroller = null;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(mScroller != null) {
            mScroller.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //如果mScroller 自己来处理触摸事件，该方法返回true
        //处理触摸索引条的事件
        if(mScroller != null && mScroller.onTouchEvent(ev)) {
            return true;
        }
        if(mGestureDetector == null) {
            //使用手势处理触摸事件
            mGestureDetector = new GestureDetector(getContext(),
                    new GestureDetector.SimpleOnGestureListener(){
                        @Override
                        public boolean onFling(MotionEvent e1, MotionEvent e2,
                                       float velocityX, float velocityY) {
                            mScroller.show();
                            return super.onFling(e1, e2, velocityX, velocityY);
                        }
            });
        }
        mGestureDetector.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        if(mScroller != null) {
            mScroller.setAdapter(adapter);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  // 屏幕方向变化后， 索引条调整
        super.onSizeChanged(w, h, oldw, oldh);
        if(mScroller != null) {
            mScroller.onSizeChanged(w, h, oldw, oldh);
        }
    }
}
