package com.example.wwez.geekband.test13.viewpagerindicator.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wwez.myapplication.R;

import java.util.List;

public class ViewPagerIndicator extends LinearLayout {

    private Paint mPaint;

    private Path mPath;

    private int mTriangleWidth;

    private int mTriangleHeight;

    private static final float RADIO_TRIANGLE_WIDTH = 1/6F;

    private int mInitTranslationX, mTranslationX;

    private int mTabVisibleCount;

    private static int COUNT_DEFAULT_TAB = 4;

    private List<String> mTitles;

    private static final int COLOR_TEXT_NORMAL = 0x77FFFFFF;

    private static final int COLOR_TEXT_HIGH = 0xFFFFFFFF;

    private ViewPager mViewPager;

    public interface PageOnChangeListener {
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
        void onPageSelected(int position);
        void onPageScrollStateChanged(int state);
    }

    public PageOnChangeListener mPageOnChangeListener;

    public void setOnPageChangeListener(PageOnChangeListener listener) {
        mPageOnChangeListener = listener;

    }

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        mTabVisibleCount = ta.getInt(R.styleable.ViewPagerIndicator_visible_tab_count, COUNT_DEFAULT_TAB);
        if(mTabVisibleCount <= 0 ) {
            mTabVisibleCount = COUNT_DEFAULT_TAB;
        }
        ta.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#FFffffff"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Log.d("zww", "onSizeChanged");

        mTriangleWidth = (int) (w / mTabVisibleCount * RADIO_TRIANGLE_WIDTH);

        mInitTranslationX = w / mTabVisibleCount / 2 - mTriangleWidth / 2;

        initTriangle();

    }

    private void initTriangle() {

        mTriangleHeight = mTriangleWidth / 2;

        mPath = new Path();

        mPath.moveTo(0, 0);

        mPath.lineTo(mTriangleWidth, 0);

        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);

        mPath.close();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        Log.d("zww", "dispatchDraw");

        canvas.save();

        canvas.translate(mInitTranslationX + mTranslationX, getHeight());

        canvas.drawPath(mPath, mPaint);

        canvas.restore();

        super.dispatchDraw(canvas);
    }

    // 指示器跟随手指进行滚动
    public void scroll(int position, float positionOffset) {

        int tabWidth = getWidth() / mTabVisibleCount;

        mTranslationX = (int) (tabWidth *  positionOffset + position * tabWidth);

        // 容器移动 在tab处于移动到最后一个时
        if(
            position >= (mTabVisibleCount - 2)
                &&
            positionOffset > 0
                &&
            getChildCount() > mTabVisibleCount
                &&
            position < getChildCount() - 2
        ) {
            if(mTabVisibleCount != 1) {
                this.scrollTo((position - (mTabVisibleCount - 2)) * tabWidth + (int)(tabWidth * positionOffset), 0);
            } else {
                this.scrollTo(position * tabWidth + (int)(tabWidth * positionOffset) ,0);
            }

        }

        invalidate();
    }

    // XML 加载完成后调用
    @Override
    protected void onFinishInflate() {
        Log.d("zww", "onFinishInflate");

        super.onFinishInflate();

        int cCount = getChildCount();
        for(int i=0; i< cCount; i++) {
            View view =getChildAt(i);
            LinearLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
            lp.weight = 0;
            lp.width = getScreenWidth() / mTabVisibleCount;
            view.setLayoutParams(lp);
        }

        setTabItemClickEvent();

    }

    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public void setTabItemTitles(List<String> titles) {
        if(titles != null && titles.size() > 0) {
            this.removeAllViews();

            mTitles = titles;

            for(String title : mTitles) {
                addView(generateTextView(title));
            }

            setTabItemClickEvent();
        }
    }

    // 设置可见的tab 数量
    public void setVisibleTabCount(int count) {
        mTabVisibleCount = count;
    }


    // 根据 title 创建 tab
    private View generateTextView(String title) {
        TextView tv = new TextView(getContext());

        LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        lp.width = getScreenWidth() / mTabVisibleCount;

        tv.setText(title);

        tv.setGravity(Gravity.CENTER);

        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        tv.setTextColor(COLOR_TEXT_NORMAL);

        tv.setLayoutParams(lp);

        return tv;
    }

    // 设置关联的viewpager
    public void setViewPager(ViewPager viewPager, int currentPos) {

        mViewPager = viewPager;

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // tabwidth * positionOffset + position * tabwidth
                scroll(position, positionOffset);
                if(mPageOnChangeListener == null) return;
                mPageOnChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                if(mPageOnChangeListener == null) return;
                mPageOnChangeListener.onPageSelected(position);
                highLightTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(mPageOnChangeListener == null) return;
                mPageOnChangeListener.onPageScrollStateChanged(state);
            }
        });

        mViewPager.setCurrentItem(currentPos);
        highLightTitle(currentPos);
    }

    //重置TAB文本颜色
    private void resetTitleColor() {
        for(int i=0; i<getChildCount();i++) {
            setTitleColor(i, COLOR_TEXT_NORMAL);
        }
    }

    // 高亮 某个tab的文本
    private void highLightTitle(int pos) {
        resetTitleColor();
        setTitleColor(pos, COLOR_TEXT_HIGH);
    }

    private void setTitleColor(int pos, int color) {
        View view = getChildAt(pos);
        if(view instanceof TextView) {
            ((TextView) view).setTextColor(color);
        }
    }

    // 设置tab的点击事件
    private void setTabItemClickEvent() {
        int cCount = getChildCount();
        for(int i=0; i<cCount; i++) {
            final int j = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }

}
