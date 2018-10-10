package com.example.wwez.qqSliderMenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.wwez.myapplication.R;
import com.nineoldandroids.view.ViewHelper;

public class SlidingMenu extends HorizontalScrollView {
    private LinearLayout mWrapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    private int mScreenWidth;
    private int mMenuRightPadding;
    private boolean once = false;
    private int mMenuWidth;
    private boolean isOpen =false;


    public SlidingMenu(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context) {
        this(context, null);
    }

    /*
    * 当使用了自定义了属性时会调用此构造函数
    * */
    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a =context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenu,defStyleAttr,0);
        mMenuRightPadding = (int) a.getDimension(R.styleable.SlidingMenu_rightPadding, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics()));
        a.recycle();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
    }

    /*
    * 设置子view的宽高， 设置自己的宽高
    * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(!once){
            mWrapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWrapper.getChildAt(0);
            mContent = (ViewGroup) mWrapper.getChildAt(1);
            mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
            mContent.getLayoutParams().width = mScreenWidth;
            once = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    /**
     * 通过设置偏移量,将menu隐藏
     * */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed){
            this.scrollTo(mMenuWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX(); //隐藏在左边的宽度
                if(scrollX >= mMenuWidth / 2) {
                    this.smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;
                } else {
                    this.smoothScrollTo(0, 0);
                    isOpen = true;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    public void openMenu(){
        if(isOpen) return;
        this.smoothScrollTo(0, 0);
        isOpen= true;
    }
    public void closeMenu(){
        if(!isOpen) return;
        this.smoothScrollTo(mMenuWidth, 0);
        isOpen= false;
    }

    public void toggleMenu(){
        if(isOpen) closeMenu();
        else openMenu();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        float scale = l * 1.0f / mMenuWidth;  // 1 - 0
        ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.7f);

        float rightScale = 0.7f + 0.3f * scale;
        float leftSacle = 1.0f - scale * 0.3f;
        float leftAlpha = 0.6f + 0.4f * (1 - scale);

        ViewHelper.setScaleX(mContent, rightScale);
        ViewHelper.setScaleY(mContent, rightScale);
        ViewHelper.setPivotX(mContent, 0);
        ViewHelper.setPivotY(mContent,mContent.getHeight()/2);

        ViewHelper.setScaleX(mMenu, leftSacle);
        ViewHelper.setScaleY(mMenu, leftSacle);

        ViewHelper.setAlpha(mMenu, leftAlpha);
    }
}
