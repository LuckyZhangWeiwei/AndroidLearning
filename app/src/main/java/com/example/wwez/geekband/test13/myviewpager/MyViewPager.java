package com.example.wwez.geekband.test13.myviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

import com.example.wwez.myapplication.R;

public class MyViewPager extends ViewGroup {

    private Context mContext;

    private int[] images = {R.drawable.bg_2019_07_25_1,R.drawable.bg_2019_07_25_2,R.drawable.bg_2019_07_25_3,R.drawable.bg_2019_07_25_4};

    private GestureDetector mGestureDetector;

    private Scroller mScroller;

    private int position;

    private int scrollX;

    private int startX;

    private int endStartX;

    private int startY;

    public MyViewPager(Context context) {
        super(context);
        mContext = context;
        init();
    }
    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        for (int i = 0; i < images.length; i++) {
            ImageView iv = new ImageView(getContext());
            iv.setBackgroundResource(images[i]);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            addView(iv);
        }

        View testView = View.inflate(mContext, R.layout.test_viewpager_scrollview, null);
        addView(testView, 2);

        mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                scrollBy((int) distanceX, 0);
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
        });
        mScroller = new Scroller(mContext);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        int x = (int) event.getRawX();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                endStartX = x;
                scrollX = getScrollX();//相对于初始位置滑动的距离
                if (mOnPageScrollListener != null) {
                    mOnPageScrollListener.onPageScrolled((float) (getScrollX() * 1.0 / (getWidth())), position);
                }
                endStartX = x;
                break;
            case MotionEvent.ACTION_UP:
                int scrollDistance = getScrollX() % getWidth();
                if(startX > endStartX) {// 判断 左右方向
                    if(scrollDistance >= getWidth() / 4) {
                        if(position < getChildCount()) {
                            position = position + 1;
                        }
                    }
                } else if(startX <= endStartX) {
                    if(scrollDistance != 0 &&  scrollDistance < getWidth() * 3 / 4) {
                        if (position> 0) {
                            position = position - 1;
                        }
                    }
                }
                //滑到最后一张的时候，不能出边界
//                if (position >= images.length) {
//                    position = images.length - 1;
//                    return false;
//                }
                if (position >= images.length+1) {
                    position = images.length;
                }
                if (position < 0) {
                    position = 0;
                }
                mScroller.startScroll(scrollX, 0, -(scrollX - position * getWidth()), 0);
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public void scrollTo(int x, int y) {
        if(x < 0) {
            x = 0;
        }
        if(x > getMeasuredWidth() * (getChildCount() - 1)) {
            x = getMeasuredWidth() * (getChildCount() - 1);
        }
        super.scrollTo(x, y);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.layout(i * getWidth(), t, (i+1) * getWidth(), b);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), 0);
            postInvalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch  (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                mGestureDetector.onTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getX();
                int endY = (int) ev.getY();
                int dx = endX - startX;
                int dy = endY - startY;
                if (Math.abs(dx) > Math.abs(dy)) {
                    // 左右滑动
                    return true;// 中断事件传递, 不允许孩子响应事件了, 由父控件处理
                }
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View Child = getChildAt(i);
            measureChild(Child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    public interface OnPageScrollListener {
        void onPageScrolled(float offsetPercent, int position);

        void onPageSelected(int position);
    }

    private OnPageScrollListener mOnPageScrollListener;

    public void setonPageScrollListener(OnPageScrollListener onPageScrollListener) {
        mOnPageScrollListener = onPageScrollListener;
    }
}
