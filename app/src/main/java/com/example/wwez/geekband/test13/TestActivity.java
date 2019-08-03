package com.example.wwez.geekband.test13;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.wwez.geekband.test13.myviewpager.MyViewPager;
import com.example.wwez.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private MyViewPager myViewPager;

    private LinearLayout llPointList;

    private List<Integer> mData = new ArrayList<>();

    private LinearLayout.LayoutParams params;

    private View viewDot;

    private int dotDistance = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_test);

        myViewPager = findViewById(R.id.myviewpager);

        viewDot = findViewById(R.id.view_dot);

        llPointList = findViewById(R.id.ll_point_list);

        initCirclePoint();

        myViewPager.setonPageScrollListener(new MyViewPager.OnPageScrollListener() {
            @Override
            public void onPageScrolled(float offsetPercent, int position) {
//                //效果一：滑动页面过程中小圆点跟随移动
//                //offsetPercent:0-0.5-1-1.5-...
                float leftMargin = offsetPercent * dotDistance + 10;
                //如果使用系统的ViewPager也可以使用这种方法添加指示器，只需修改成如下即可：
                //float leftMargin = positionOffset * dotDistance + position * dotDistance;
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewDot.getLayoutParams();
                params.leftMargin = (int) leftMargin; //滑动后更新距离
//                Elog.e("Offset", "params.leftMargin=" + params.leftMargin);
                viewDot.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                //效果二：滑动页面过程中小圆点不跟随移动，到某个指定位置才切换小圆点
//                Log.e("TAG", "position=" + position);
//                float leftMargin = position * dotDistance;
//                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewDot.getLayoutParams();
//                params.leftMargin = (int) leftMargin; //滑动后更新距离
////                Elog.e("Offset", "params.leftMargin=" + params.leftMargin);
//                viewDot.setLayoutParams(params);
            }
        });

    }

    private void initCirclePoint() {
        for (int i = 0; i < 5 ; i++) {
            mData.add(i);
        }

        for (int i = 0; i < mData.size(); i++) {
            View point = new View(this);
            point.setBackgroundResource(R.drawable.bg_point_selector);
            params = new LinearLayout.LayoutParams(20, 20);
            if (i != 0) {
                params.leftMargin = 10;
            }
            point.setEnabled(false);
            point.setLayoutParams(params);
            llPointList.addView(point);
        }
    }
}
