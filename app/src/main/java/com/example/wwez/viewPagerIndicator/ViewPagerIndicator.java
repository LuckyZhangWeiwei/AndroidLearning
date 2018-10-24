package com.example.wwez.viewPagerIndicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
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
    private static final float RATE_TRANGLE_WIDTH = 1/6F;
    private int mInitTranslationX;
    private int mTransloationX;
    private int mTabVisibleCount;
    private static final int COUNT_DEFAULT_TAB=4;
    private List<String> mTitles;
    private static final int COLOR_TEXT_HIGHT = 0XFFFFFFFF;
    private static final float RADIO_TRANGLE_WIDTH = 1/6F;
    private final int DIMENSION_TRIANGLE_WIDTH_MAX =(int) (getScreenWidth() / 3 * RADIO_TRANGLE_WIDTH);

    public ViewPagerIndicator(Context context) {
        this(context,null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs){
        super(context, attrs);
        Log.d("viewpagerindicator:","ViewPagerIndicator");
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        mTabVisibleCount = a.getInt(R.styleable.ViewPagerIndicator_visible_tab_count, COUNT_DEFAULT_TAB);
        if(mTabVisibleCount<0){
            mTabVisibleCount=COUNT_DEFAULT_TAB;
        }
        a.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#FFffffff"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("viewpagerindicator:","onSizeChanged");
        mTriangleWidth =(int)( w / mTabVisibleCount * RATE_TRANGLE_WIDTH);
        mTriangleWidth = Math.min(DIMENSION_TRIANGLE_WIDTH_MAX, mTriangleWidth);
        mInitTranslationX = w/mTabVisibleCount /2 - mTriangleWidth/2;
        initTriangle();
    }

    private void initTriangle() {
        mTriangleHeight = mTriangleWidth / 2;

        mPath = new Path();
        mPath.moveTo(0,0);
        mPath.lineTo(mTriangleWidth,0);
        mPath.lineTo(mTriangleWidth/2, -mTriangleHeight);
        mPath.close();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.d("viewpagerindicator:","dispatchDraw");
        Log.d("viewpagerindicator:",getHeight()+"");
        canvas.save();
        canvas.translate(mInitTranslationX + mTransloationX, getHeight());
        canvas.drawPath(mPath, mPaint);
        canvas.restore();

        super.dispatchDraw(canvas);
    }

    public void scroll(int position, float Offset) {
        Log.d("viewpagerindicator", position+"");
        int tabWidth = getWidth() / mTabVisibleCount ;
        mTransloationX =(int)(tabWidth * (Offset + position));

        if(
           position >=(mTabVisibleCount-2)
           &&
           Offset >0
           &&
           getChildCount() > mTabVisibleCount
           &&
           position != getChildCount()-2
           ){
            if(mTabVisibleCount !=1)
                this.scrollTo(((position - (mTabVisibleCount-2))*tabWidth + (int)(tabWidth* Offset)) ,0);
            else
                this.scrollTo(position * tabWidth + (int)(tabWidth * Offset) ,0);
        }

        invalidate();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d("viewpagerindicator:","onFinishInflate");
        int cCount = getChildCount();
        if(cCount == 0) return;

        for(int i=0; i<cCount;i++){
            View view = getChildAt(i);
            LinearLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
            lp.weight=0;
            lp.width = getScreenWidth() / mTabVisibleCount;
            view.setLayoutParams(lp);
        }
        setItemClickEvent();
    }

    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public void setTabItemTitles(List<String> titles){
        if(titles!=null && titles.size()>0){
            this.removeAllViews();
            mTitles = titles;
            for(String title: mTitles){
                addView(generateTextView(title));
            }
            setItemClickEvent();
        }
    }

    private View generateTextView(String title) {
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.width = getScreenWidth() / mTabVisibleCount;
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setTextColor(0x77FFFFFF);
        tv.setLayoutParams(lp);
        return tv;
    }

    public void setVisibleTabCount(int count){
        mTabVisibleCount = count;
    }

    private ViewPager mViewPager;

    public interface PageOnChangeListener
    {
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
        void onPageSelected(int position);
        void onPageScrollStateChanged(int state);
    }

    private PageOnChangeListener mPageOnChangeListener;

    public void setPageOnChangeListener(PageOnChangeListener listener){
        mPageOnChangeListener = listener;
    }

    public void setViewPager(ViewPager viewPager, final int pos){
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scroll(position,positionOffset);
                if(mPageOnChangeListener != null){
                    mPageOnChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if(mPageOnChangeListener != null){
                    mPageOnChangeListener.onPageSelected(position);
                }
                resetTextViewColor();
                highlightTextView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(mPageOnChangeListener != null){
                    mPageOnChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
        mViewPager.setCurrentItem(pos);
        highlightTextView(pos);
    }

    private void highlightTextView(int pos){
        View view = getChildAt(pos);
        if(view instanceof TextView){
            ((TextView) view).setTextColor(COLOR_TEXT_HIGHT);
        }
    }

    private void resetTextViewColor(){
        for(int i=0; i<getChildCount();i++){
            View view = getChildAt(i);
            if(view instanceof TextView){
                ((TextView) view).setTextColor(0x77FFFFFF);
            }
        }
    }

    private void setItemClickEvent(){
        int count = getChildCount();
        for(int i =0; i<count;i++){
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
