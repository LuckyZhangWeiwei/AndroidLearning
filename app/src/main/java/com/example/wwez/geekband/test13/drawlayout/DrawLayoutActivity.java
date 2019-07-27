package com.example.wwez.geekband.test13.drawlayout;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.example.wwez.myapplication.R;

public class DrawLayoutActivity extends AppCompatActivity {
//    int mLastX;
//    LinearLayout drawerLayout;
//    int mScreenWidth;
//    LinearLayout temp;
//    ViewGroup.LayoutParams lp;
//    View mask;
//    Scroller mScroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_layout);

//        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics dm = new DisplayMetrics();
//        assert wm != null;
//        wm.getDefaultDisplay().getMetrics(dm);
//        mScreenWidth = dm.widthPixels;
//        drawerLayout =  findViewById(R.id.drawerlayout);
//        lp = drawerLayout.getLayoutParams();
//        temp = (LinearLayout)drawerLayout.getParent();
//        mask = findViewById(R.id.mask);
//        mScroller = new Scroller(getApplicationContext());
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int x = (int) event.getRawX();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mLastX = x;
//                if (x < 50) {
//                    temp.scrollBy(-50, 0);
////                    mask.setVisibility(View.VISIBLE);
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                if (x < 50) {
////                    temp.scrollBy(50, 0);
////                    mask.setVisibility(View.GONE);
//                    mScroller.startScroll(x, 0, 50, 0);
//                } else {
//                    if(-(temp).getScrollX() > lp.width/2) {
////                        temp.scrollTo(-lp.width, 0);
////                        mask.setVisibility(View.VISIBLE);
//                        mScroller.startScroll(x, 0, -lp.width, 0);
//                    } else {
////                        temp.scrollTo(0, 0);
////                        mask.setVisibility(View.GONE);
//                        mScroller.startScroll(x, 0, 0, 0);
//                    }
//                }
//                break;
//            case MotionEvent.ACTION_MOVE:
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                if(-(temp).getScrollX() <= lp.width) {
//                    int offsetX = x - mLastX;
//                    temp.scrollBy(-offsetX, 0);
////                    mask.setVisibility(View.VISIBLE);
//                }
//                else if((x - mLastX) <= 0) {
//                    int offsetX = x - mLastX;
//                    temp.scrollBy(-offsetX, 0);
////                    mask.setVisibility(View.VISIBLE);
//                } else {
////                    mask.setVisibility(View.GONE);
//                }
//                mLastX = x;
//
//                break;
//        }
//        return true;
//    }

}
