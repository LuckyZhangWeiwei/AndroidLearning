package com.example.wwez.geekband.test13;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.wwez.myapplication.R;

public class RedCycleView extends View implements View.OnClickListener{
    private Paint mPaint;
    private Rect mRect;
    private int mNumber;
    private int mBackgroundColor;
    private float mTextSize;
    private int mTextColor;

    public RedCycleView(Context context) {
        this(context, null);
    }

    public RedCycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RedCycleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();


        mRect = new Rect();

        setOnClickListener(this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RedCycleButton);

        mBackgroundColor = typedArray.getColor(R.styleable.RedCycleButton_backgroundColor, Color.RED);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.RedCycleButton_textSize, 18);
        mTextColor = typedArray.getColor(R.styleable.RedCycleButton_textColor, Color.RED);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        定大小
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        定位置
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        绘制
        mPaint.setColor(mBackgroundColor);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mPaint);
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);

        String text = String.valueOf(mNumber);
        mPaint.getTextBounds(text, 0, text.length(), mRect);

        int textWidth = mRect.width();
        int textHeight = mRect.height();

        canvas.drawText(text, getWidth() /2 - textWidth /2, getHeight()/2 + textHeight/2, mPaint);

    }

    @Override
    public void invalidate() {
        super.invalidate();
//        刷新
    }

    @Override
    public void onClick(View v) {
        if(mNumber > 0) {
            mNumber--;
        } else {
            mNumber = 20;
        }
        invalidate();
    }
}
