package com.example.wwez.geekband.test13.myrefresh;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wwez.myapplication.R;

public class RefreshWithHeader extends LinearLayout {

    View mHeader;

    TextView mState;

    int touchSlop;

    int childrenCount;

    RefreshListener mRefreshListener;

    RecyclerView mChild;

    float mLastInterceptY;

    float mLastY;

    int mFlags = -1;

    int REFRESHING = 0;

    int PULL_TO_REFRESH = 1;

    int RELEASE_TO_REFRESH = 2;

    float deltaY;

    public RefreshWithHeader(Context context) {
        super(context);
    }

    public RefreshWithHeader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshWithHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setWillNotDraw(true);
        mHeader = LayoutInflater.from(context).inflate(R.layout.header, this, false);
        mState = mHeader.findViewById(R.id.state);
        mState.setText("下拉刷新");
        addView(mHeader,0);
        touchSlop = ViewConfiguration.getTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = 0, measureHeight = 0;
        childrenCount = getChildCount();
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
        // 获取自己测量宽度用的测量模式
        int widthSpaceMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpaceMode = MeasureSpec.getMode(heightMeasureSpec);

        if(childrenCount == 0) {
            setMeasuredDimension(0,0);
        } else if(widthSpaceMode == MeasureSpec.AT_MOST && heightSpaceMode == MeasureSpec.AT_MOST) {
            for(int i = 0;i<childrenCount;i++){
                if(getChildAt(i)!=null) {
                    //我么设置当前ViewGroup的高度为所有子View的高度之和
                    measureHeight += getChildAt(i).getMeasuredHeight();
                    //我们设置当前ViewGroup的宽度为所有子View中最宽的
                    measureWidth = Math.max(measureWidth, getChildAt(i).getMeasuredWidth());
                }
            }
            //这里把我们的宽度和高度设置成默认尺寸
            setMeasuredDimension(measureWidth,measureHeight);
        } else if(widthSpaceMode == MeasureSpec.AT_MOST) {
            for (int i = 0;i < childrenCount;i++){
                if(getChildAt(i)!=null)
                    measureWidth = Math.max(measureWidth,getChildAt(i).getMeasuredWidth());
            }
            setMeasuredDimension(measureWidth,heightSpaceSize);

        } else if(heightSpaceMode == MeasureSpec.AT_MOST) {
            for(int i = 0; i<childrenCount;i++){
                if(getChildAt(i)!=null)
                    measureHeight += getChildAt(i).getMeasuredHeight();
            }
            setMeasuredDimension(widthSpaceSize,measureHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childrenTop = -mHeader.getMeasuredHeight();

        for (int i = 0; i < childrenCount; i++) {
            final View child = getChildAt(i);
            //确认子View是可见的
            if(child.getVisibility() != GONE){
                child.layout(0,childrenTop,child.getMeasuredWidth(),
                        childrenTop+child.getMeasuredHeight());
                childrenTop += child.getMeasuredHeight();
            }
        }
        mChild = (RecyclerView) getChildAt(childrenCount-1);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //设置一个是否拦截的标志位
        boolean intercepted = false;
        //计算竖直方向上的距离差

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //down事件默认不拦截
                mLastInterceptY = ev.getY();
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                deltaY = ev.getY() - mLastInterceptY;
                //判断竖直方向滑动大于默认最小滑动且子RecyclerView到达顶部
                if (deltaY > touchSlop && !mChild.canScrollVertically(-1)){
                    //上面讨论的第一种情况，需要拦截
                    intercepted = true;
                    //判断正在往下滑且刷新有还没有被隐藏
                }else if(deltaY < 0 && mHeader.getY()>-mHeader.getHeight()) {
                    //上面讨论的第二种情况，需要拦截
                    intercepted = true;
                }else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                // up事件默认不拦截
                intercepted = false;
                break;
        }
        mLastInterceptY = ev.getY();
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //我们不做多点触控
        if(event.getPointerCount()>1){
            //还原位置，及标志
            mFlags = -1;
            mLastY = 0;
            mHeader.layout(0, -mHeader.getHeight(), mHeader.getWidth(), 0);
            mChild.layout(0, 0, getWidth(), mChild.getHeight());
            return false;
        }
        float y = event.getY();
        //计算竖直方向的高度差
        float deltaX = y - mLastY;
        //设置一个标志位决定是否消费事件
        boolean result = false;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                result = false;
                break;
            case MotionEvent.ACTION_MOVE:
                //判断微小滑动或者往下滑
                if(mFlags == REFRESHING){
                    return false;
                }
                if(deltaX < touchSlop){
                    //刷新头隐藏或者没有完全拉出来
                    if(mHeader.getY() < 0){
                        mFlags = PULL_TO_REFRESH;
                        //设置刷新头的标题
                        updateHeaderTitle("下拉刷新");
                        result = false;
                    }
                    result = false;
                    //判断当前没有处于正在刷新状态且刷新头完全显示出来了
                }else  if(mFlags != REFRESHING && mHeader.getY()>0) {
                    //改变刷新头状态
                    updateHeaderTitle("松手刷新");
                    //设置释放刷新标志
                    mFlags = RELEASE_TO_REFRESH;
                }
                if(mLastY!=0 && event.getPointerCount()==1) {
                    //通过layout()方法让view随着用户的滑动而移动
                    mHeader.layout(
                            0,
                            (int) mHeader.getY() + (int) deltaX/2,
                            mHeader.getWidth(),
                            (int) mHeader.getY() + (int) deltaX/2 + mHeader.getHeight());

                    mChild.layout(0, (int) mChild.getY() + (int) deltaX/2, mChild.getWidth()
                            , (int) mChild.getY() + (int) deltaX/2 + mChild.getHeight());
                }
                mLastY = y;
                result = false;
                break;
            case MotionEvent.ACTION_UP:
                //判断刷新标志符
                if (mFlags == RELEASE_TO_REFRESH){
                    updateHeaderTitle("松手刷新");
                    //松手时处于RELEASE_TO_REFRESH状态就去刷新
                    refresh();
                    result = true;
                }
                if(!result) {
                    mHeader.layout(0, -mHeader.getHeight(), mHeader.getWidth(), 0);
                    mChild.layout(0, 0, getWidth(), mChild.getHeight());
                }
                //松手了就初始化标志符
                mFlags = -1;
                mLastY = 0;
                break;
        }
        return result;
    }

    private void refresh() {
        //刷新的动画
        mFlags = REFRESHING;
        refreshAnimation();

        if(mRefreshListener != null) {
            mRefreshListener.onRefresh();
        }

        updateHeaderTitle("正在刷新");
        //这是为了显示效果，用handler做一个延时操作
        @SuppressLint("HandlerLeak") Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1){
                    initState();
//                    Log.d("zww","refresh 正在刷新:" +distance);
                }
            }
        };
        handler.sendEmptyMessageDelayed(1,2000);
    }

    private void refreshAnimation(){
        // 这里用属性动画做一个平滑的过渡
        ObjectAnimator animator = ObjectAnimator.ofFloat(mHeader,"Y",
                mHeader.getY(),0);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mChild,"Y",
                mChild.getY(),mHeader.getHeight());
        AnimatorSet set = new AnimatorSet();
        set.play(animator).with(animator2);
        set.setDuration(500);
        set.start();
        // 属性动画没有真正改变View的位置，所以我们再手动调整一次位置
        mHeader.layout(0,0, mHeader.getWidth(), mHeader.getHeight());
        mChild.layout(0, mHeader.getHeight(),getWidth(),mChild.getHeight()+ mHeader.getHeight());
    }

    /**
     *初始化View的状态和一些标志符
     **/
    private void initState(){
        mFlags = -1;
        mLastY = 0;
        ObjectAnimator animator = ObjectAnimator.ofFloat(mHeader,"Y",
                mHeader.getY(),-mHeader.getHeight());
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mChild,"Y",
                mChild.getY(),0);
        AnimatorSet set = new AnimatorSet();
        set.play(animator).with(animator2);
        set.setDuration(500);
        set.start();
        mHeader.layout(0,-mHeader.getHeight(), mHeader.getWidth(),0);
        mChild.layout(0,0,getWidth(),mChild.getHeight());
    }

    private void updateHeaderTitle(String message) {
        mState.setText(message);
    }

    interface RefreshListener {
        void onRefresh();
    }

    public void setRefreshListener(RefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }
}
