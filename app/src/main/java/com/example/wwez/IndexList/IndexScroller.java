package com.example.wwez.IndexList;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SectionIndexer;

import static android.support.design.widget.BottomSheetBehavior.STATE_HIDDEN;

public class IndexScroller {
    private float mIndexbarWidth;
    private float mIndexbarMargin;
    private float mPreviewPadding;
    private float mDensity;   //当前屏幕密度除以 160
    private float mScaledDensity;  //当前屏幕密度除以 160（设置字体的尺寸）
    private float mAlphaRate;    //透明度（显示或隐藏索引条） 0 - 1
    private int mState = STATE_HIDDEN;  //索引条的状态
    private int mListViewWidth;
    private int mListViewHeight;
    private int mCurrentSection = -1;
    private boolean mIsIndexing = false;
    private ListView mListView = null;
    private SectionIndexer mIndexer = null;
    private String[] mSections = null;
    private RectF mIndexbarRect;

    private static final int STATE_HIDDEN = 0;
    private static final int STATE_SHOWING = 1;
    private static final int STATE_SHOWN = 2;
    private static final int STATE_HIDING = 3;

    public IndexScroller(Context context, ListView lv){
        mDensity = context.getResources().getDisplayMetrics().density;
        mScaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        mListView = lv;
        setAdapter(mListView.getAdapter());

        mIndexbarWidth = 20 * mDensity;
        mIndexbarMargin = 10 * mDensity;
        mPreviewPadding = 5 * mDensity;
    }

    public void setAdapter(Adapter adapter) {
        if(adapter instanceof SectionIndexer){
            mIndexer = (SectionIndexer) adapter;
            mSections =(String[])mIndexer.getSections();
        }
    }
    //绘制索引条和预览文本
    public void draw(Canvas canvas) {
        // 1. 绘制索引条，包括索引条的背景和文本
        // 2. 绘制预览文本和背景
        //如果索引条隐藏， 不进行绘制
        if(mState == STATE_HIDDEN) {
            return;
        }
        //设置索引条背景的绘制属性
        Paint indexbarPaint = new Paint();
        indexbarPaint.setColor(Color.BLACK);
        indexbarPaint.setAlpha((int)(64 * mAlphaRate));
        //绘制索引条 (4个角都是圆角的矩形区域)
        canvas.drawRoundRect(mIndexbarRect, 5 * mDensity, 5 * mDensity, indexbarPaint);
        //绘制sections
        if(mSections != null && mSections.length > 0) {
            // 绘制预览文本和背景
            if(mCurrentSection >= 0) {
                Paint previewPaint = new Paint();
                previewPaint.setColor(Color.BLACK);
                previewPaint.setAlpha(96);

                Paint previewTextPaint = new Paint();
                previewTextPaint.setColor(Color.WHITE);
                previewTextPaint.setTextSize(50 * mScaledDensity);
                float previewTextWidth = previewTextPaint.measureText(mSections[mCurrentSection]);
                float previewSize = 2 * mPreviewPadding + previewTextPaint.descent() - previewTextPaint.ascent();
                // 预览文本背景区域
                RectF previewRect = new RectF(
                        (mListViewWidth - previewSize) / 2,
                        (mListViewHeight - previewSize) / 2,
                        (mListViewWidth - previewSize) / 2 + previewSize,
                        (mListViewHeight - previewSize) / 2 + previewSize
                );
                //绘制背景
                canvas.drawRoundRect(previewRect, 5 * mDensity, 5 * mDensity, previewPaint);
                //绘制预览文本
                canvas.drawText(
                        mSections[mCurrentSection],
                        previewRect.left + (previewSize - previewTextWidth) / 2 - 1,
                        previewRect.top + mPreviewPadding - previewTextPaint.ascent() + 1 ,
                        previewTextPaint
                        );
            }
        }
        Paint indexPaint = new Paint();
        indexPaint.setColor(Color.WHITE);
        indexPaint.setAlpha((int)(255 * mAlphaRate));
        indexPaint.setTextSize(12 * mScaledDensity);
        float sectionHeight = (mIndexbarRect.height() - 2 * mIndexbarMargin) / mSections.length;
        float paddingTop = (sectionHeight - (indexPaint.descent() - indexPaint.ascent())) / 2 ;
        for(int i=0; i<mSections.length; i++ ) {
            float paddingLeft = (mIndexbarWidth - indexPaint.measureText(mSections[i])) / 2;
            // 绘制索引条上的文字
            canvas.drawText(
                    mSections[i],
                    mIndexbarRect.left + paddingLeft,
                    mIndexbarRect.top + mIndexbarMargin + sectionHeight * i + paddingTop - indexPaint.ascent(),
                    indexPaint
                    );
        }
    }
    public void onSizeChanged(int w, int h, int oldw, int oldh){
        mListViewWidth = w;
        mListViewHeight = h;
        mIndexbarRect = new RectF(
                w - mIndexbarMargin - mIndexbarWidth,
                mIndexbarMargin,
                w - mIndexbarMargin,
                h - mIndexbarMargin
        );
    }

    private void fade(long delay) {
        mHandler.removeMessages(0);
        mHandler.sendEmptyMessageAtTime(0, SystemClock.uptimeMillis() + delay);
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (mState) {
                case STATE_SHOWING:
                    mAlphaRate +=(1-mAlphaRate) * 0.2;
                    if(mAlphaRate > 0.9) {
                        mAlphaRate = 1;
                        setState(STATE_SHOWN);
                    }
                    mListView.invalidate();
                    fade(10);
                    break;
                case STATE_SHOWN:
                    setState(STATE_HIDING);
                    break;
                case STATE_HIDING:
                    mAlphaRate -= mAlphaRate * 0.2;
                    if(mAlphaRate < 0.1) {
                        mAlphaRate = 0;
                        setState(STATE_HIDDEN);
                    }
                    mListView.invalidate();
                    fade(10);
                    break;
            }
        }
    };

    private void setState(int state) {
        if(state < STATE_HIDDEN || state > STATE_HIDING)
            return;
        mState = state;
        switch (mState){
            case STATE_HIDDEN:
                mHandler.removeMessages(0);
                break;
            case STATE_SHOWING:
                mAlphaRate = 0;
                fade(0);
                break;
            case STATE_SHOWN:
                mHandler.removeMessages(0);
                break;
            case STATE_HIDING:
                mAlphaRate = 1;
                fade(3000);
                break;
        }
    }

    private boolean contains(float x, float y) {
// Determine if the point is in index bar region, which includes the
        // right margin of the bar
        return (x >= mIndexbarRect.left && y >= mIndexbarRect.top && y <= mIndexbarRect.top
                + mIndexbarRect.height());
    }

    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: // 按下，开始索引
                // If down event occurs inside index bar region, start indexing
                if (mState != STATE_HIDDEN && contains(ev.getX(), ev.getY())) {
                    setState(STATE_SHOWN);

                    // It demonstrates that the motion event started from index bar
                    mIsIndexing = true;
                    // Determine which section the point is in, and move the list to
                    // that section
                    mCurrentSection = getSectionByPoint(ev.getY());
                    mListView.setSelection(mIndexer
                            .getPositionForSection(mCurrentSection));
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE: // 移动
                if (mIsIndexing) {
                    // If this event moves inside index bar
                    if (contains(ev.getX(), ev.getY())) {
                        // Determine which section the point is in, and move the
                        // list to that section
                        mCurrentSection = getSectionByPoint(ev.getY());
                        mListView.setSelection(mIndexer
                                .getPositionForSection(mCurrentSection));
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP: // 抬起
                if (mIsIndexing) {
                    mIsIndexing = false;
                    mCurrentSection = -1;
                }
                if (mState == STATE_SHOWN)
                    setState(STATE_HIDING);
                break;
        }
        return false;

    }

    // 显示
    public void show() {
        if (mState == STATE_HIDDEN)
            setState(STATE_SHOWING);
        else if (mState == STATE_HIDING)
            setState(STATE_HIDING);
    }

    // 隐藏
    public void hide() {
        if (mState == STATE_SHOWN)
            setState(STATE_HIDING);
    }

    private int getSectionByPoint(float y) {
        if (mSections == null || mSections.length == 0)
            return 0;
        if (y < mIndexbarRect.top + mIndexbarMargin)
            return 0;
        if (y >= mIndexbarRect.top + mIndexbarRect.height() - mIndexbarMargin)
            return mSections.length - 1;
        return (int) ((y - mIndexbarRect.top - mIndexbarMargin) / ((mIndexbarRect
                .height() - 2 * mIndexbarMargin) / mSections.length));
    }

}
