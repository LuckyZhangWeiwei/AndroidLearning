package com.example.wwez.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //存储所有的view
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    //每一行的高度
    private List<Integer> mLineHeight = new ArrayList<>();
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();

        //当前viewgroup的宽度
        int width = getWidth();

        int lineWidth=0; int lineHeight=0;
        List<View> lineViews = new ArrayList<>();
        int cCount = getChildCount();
        for(int i=0; i < cCount; i++ ){
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            if(childWidth + lineWidth +lp.leftMargin + lp.rightMargin > width - getPaddingLeft()-getPaddingRight()){
                mLineHeight.add(lineHeight);
                mAllViews.add(lineViews);

                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                lineViews = new ArrayList<>();

            }

            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight+lp.topMargin+lp.bottomMargin);
            lineViews.add(child);
        }
        //处理最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        //设置子view的位置
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int lineNum = mAllViews.size();
        for(int i=0; i < lineNum; i++){
            //当前行所有的view
            lineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);
            for(int j = 0; j < lineViews.size(); j++){
                View child = lineViews.get(j);
                if(child.getVisibility() == View.GONE){
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int lc = left+ lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            left = getPaddingLeft();
            top +=lineHeight;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        //wrap content
        int width = 0;
        int height = 0;
        //每一行的宽度和高度
        int lineWidth = 0;
        int lineHeight = 0;
        //得到内部元素的个数
        int cCount = getChildCount();
        for(int i=0; i < cCount; i++ ){
            View child = getChildAt(i);
            //测量子view的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            //子view占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            //子view占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            //换行
            if(lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()){
                //对比得到最大的宽度
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;
                height += lineHeight;

                lineHeight = childHeight;
            } else {  //未换行
                //叠加行宽
                lineWidth += childWidth;
                //得到当前行的最大高度
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //最后一个控件
            if(i == cCount - 1){
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }

        setMeasuredDimension(
                modeWidth==MeasureSpec.EXACTLY ? sizeWidth: width + getPaddingRight() + getPaddingLeft(),
                modeHeight==MeasureSpec.EXACTLY ? sizeHeight: height + getPaddingBottom()+ getPaddingTop()
        );
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext() ,attrs);
    }
}
