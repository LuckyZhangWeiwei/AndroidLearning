package com.example.wwez.Imooc_response_layout.flow_layout.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.wwez.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {
    private List<List<View>> mAllView = new ArrayList<>();

    private List<Integer> mLineHeight = new ArrayList<>();

    private int mMaxLines;

    public FlowLayout(Context context) {  // 对应 new 方法
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) { //对应xml中加载
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        mMaxLines= array.getInt(R.styleable.FlowLayout_maxLines, Integer.MAX_VALUE);
        array.recycle();
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mAllView.clear();
        mLineHeight.clear();

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int cCount = getChildCount();

        List<View> lineViews = new ArrayList<>();

        int lineWidth = 0;
        int lineHeight = 0;
        int height = 0;


        for (int i = 0; i < cCount; i++) {
            View Child = getChildAt(i);
            if(Child.getVisibility() == View.GONE) continue;

            measureChild(Child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) Child.getLayoutParams();

            final int cWidth = Child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            final int cHeigth = Child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if(lineWidth + cWidth > sizeWidth - (getPaddingLeft() + getPaddingRight())) {  // 当前宽度 大于 行宽  换行
                height += lineHeight;

                mLineHeight.add(lineHeight);

                mAllView.add(lineViews);
                lineViews = new ArrayList<>();
                lineViews.add(Child);

                // reset
                lineWidth = cWidth;
                lineHeight = cHeigth;

            } else {
                lineWidth += cWidth;
                lineHeight = Math.max(lineHeight, cHeigth);

                lineViews.add(Child);
            }

            if(i == cCount - 1) {
                height += lineHeight;

                mLineHeight.add(lineHeight);
                mAllView.add(lineViews);

            }

            // maxline 矫正

            if(mMaxLines <= mLineHeight.size()) {
                break;
            }
        }

        if(heightMode == MeasureSpec.EXACTLY) {
            height = sizeHeight;
        } else if(heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(sizeHeight,height);
            height = height + getPaddingBottom() + getPaddingTop();
        } else if(heightMode == MeasureSpec.UNSPECIFIED) {
            height = height + getPaddingBottom() + getPaddingTop();
        }
        setMeasuredDimension(sizeWidth, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = getPaddingLeft();
        int top = getPaddingTop();

        int lineNums = mAllView.size();

        for (int i = 0; i < lineNums; i++) {
            final List<View> lineViews = mAllView.get(i);
            final Integer lineHeight = mLineHeight.get(i);

            for (int j = 0; j < lineViews.size(); j++) {
                final View child = lineViews.get(j);
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;

            }

            left = getPaddingLeft();
            top += lineHeight;
        }
    }

    // child 没有设置layout params
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    // inflater  xml
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    // addview
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    // addview
    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }
}
