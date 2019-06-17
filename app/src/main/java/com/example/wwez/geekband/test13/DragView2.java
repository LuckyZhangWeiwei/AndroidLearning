package com.example.wwez.geekband.test13;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

public class DragView2 extends View {

    private int mLastX;
    private int mLastY;
    //声明Scroller变量
    private Scroller mScroller;

    public DragView2(Context context) {
        this(context, null);
    }

    public DragView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //在构造方法中初始化Scroller变量
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - mLastX;
                int offsetY = y - mLastY;
                //实现View跟随手指移动的效果
                ((View)getParent()).scrollBy(-offsetX, -offsetY);
                //重新设置初始坐标
                mLastX = x;
                mLastY = y;
                Log.d("zww", "x: " + String.valueOf(x) + "y: "+ String.valueOf(y) + "mlastX: " + String.valueOf(mLastX) + "mLastY: " + String.valueOf(mLastY));
                break;
            case MotionEvent.ACTION_UP:
                //当手指抬起时执行滑动过程
                View view = (View) getParent();
                mScroller.startScroll(view.getScrollX(), view.getScrollY(),
                        -view.getScrollX(), -view.getScrollY(), 5000);
                //调用重绘来间接调用computeScroll()方法
                invalidate();
                break;
        }
        return true;


//        int x = (int) event.getX();
//        int y = (int) event.getY();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                lastX = (int) event.getX();
//                lastY = (int) event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int offsetX = x - lastX;
//                int offsetY = y - lastY;
//                ((View) getParent()).scrollBy(-offsetX, -offsetY);
//                break;
//            case MotionEvent.ACTION_UP:
//                // 手指离开时，执行滑动过程
//                View viewGroup = ((View) getParent());
//                mScroller.startScroll(
//                        viewGroup.getScrollX(),
//                        viewGroup.getScrollY(),
//                        -viewGroup.getScrollX(),
//                        -viewGroup.getScrollY());
//                invalidate();
//                break;
//        }
//        return true;

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()){
            ((View)getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //通过重绘来不断调用computeScroll()方法
            invalidate();
        }
    }
}
