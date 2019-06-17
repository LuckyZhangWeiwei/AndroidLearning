package com.example.wwez.geekband.test13;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class DragView extends View {
    int mlastX, mLastY;
    public DragView(Context context) {
        this(context, null);
    }

    public DragView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        int x = (int) event.getX();
//        int y = (int) event.getY();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mlastX = x;
//                mLastY = y;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int offsetX = x - mlastX;
//                int offsetY = y - mLastY;
//                layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
//                Log.d("zww", String.valueOf(x) + "-"+ String.valueOf(mlastX) + "-" + String.valueOf(offsetX));
//                break;
//        }
//        return true;

//        int x = (int) event.getRawX();
//        int y = (int) event.getRawY();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mlastX = x;
//                mLastY = y;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int offsetX = x - mlastX;
//                int offsetY = y - mLastY;
//                layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
//                Log.d("zww", String.valueOf(x) + "-"+ String.valueOf(mlastX) + "-" + String.valueOf(offsetX));
//                mlastX = x;
//                mLastY = y;
//                break;
//        }
//        return true;

//        int x = (int) event.getX();
//        int y = (int) event.getY();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mlastX = x;
//                mLastY = y;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int offsetX = x - mlastX;
//                int offsetY = y - mLastY;
//                ((View) getParent()).scrollBy(-offsetX, -offsetY);
//                break;
//        }
//        return true;

        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mlastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - mlastX;
                int offsetY = y - mLastY;
                ((View) getParent()).scrollBy(-offsetX, -offsetY);
                mlastX = x;
                mLastY = y;
                break;
        }
        return true;

    }
}
