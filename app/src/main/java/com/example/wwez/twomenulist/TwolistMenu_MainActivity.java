package com.example.wwez.twomenulist;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wwez.myapplication.R;
import com.example.wwez.twomenulist.fragments.ReviewFragment;
import com.example.wwez.twomenulist.adapter.FragmentAdapter;
import com.example.wwez.twomenulist.fragments.BusinessFragment;
import com.example.wwez.twomenulist.fragments.ProductFragment;

import java.util.ArrayList;

public class TwolistMenu_MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener{

    private TextView tab1Tv, tab2Tv, tab3Tv;
    private View cursor;
    private ViewPager thirdVp;
    private ArrayList<Fragment> fragmentsList;
    private int offset = 0;
    private int screenWidth = 0;
    private int screen1_3;
    private LinearLayout.LayoutParams lp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twolist_menu__main);
        initView();
        initData();
    }

    private void initData() {
        fragmentsList = new ArrayList<>();
        Fragment fragment = new ProductFragment();
        fragmentsList.add(fragment);
        fragment = new ReviewFragment();
        fragmentsList.add(fragment);
        fragment = new BusinessFragment();
        fragmentsList.add(fragment);


        thirdVp.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentsList));
        thirdVp.setCurrentItem(0);
        thirdVp.setOffscreenPageLimit(2);
        thirdVp.addOnPageChangeListener(this);

        tab1Tv.setOnClickListener(this);
        tab2Tv.setOnClickListener(this);
        tab3Tv.setOnClickListener(this);
    }

    private void initView() {
        tab1Tv = findViewById(R.id.tab1_tv);
        tab2Tv = findViewById(R.id.tab2_tv);
        tab3Tv = findViewById(R.id.tab3_tv);
        cursor = findViewById(R.id.cursor);
        thirdVp = findViewById(R.id.third_vp);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screen1_3 = screenWidth / 3;
        lp = (LinearLayout.LayoutParams) cursor.getLayoutParams();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        offset = (screen1_3 - cursor.getLayoutParams().width) / 2;
        if(position == 0) {
            lp.leftMargin = (positionOffsetPixels / 3) + offset;
        } else if(position == 1) {
            lp.leftMargin = (positionOffsetPixels / 3) + screen1_3 +offset;
        }
        cursor.setLayoutParams(lp);
        updateTextColor(position);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab1_tv:
                thirdVp.setCurrentItem(0);
                updateTextColor(0);
                break;
            case R.id.tab2_tv:
                thirdVp.setCurrentItem(1);
                updateTextColor(1);
                break;
            case R.id.tab3_tv:
                thirdVp.setCurrentItem(2);
                updateTextColor(2);
                break;
        }
    }

    private void updateTextColor(int position) {
        if (position==0){
            tab1Tv.setTextColor(Color.parseColor("#2196F3"));
            tab2Tv.setTextColor(Color.parseColor("#666666"));
            tab3Tv.setTextColor(Color.parseColor("#666666"));
        }else if(position==1){
            tab1Tv.setTextColor(Color.parseColor("#666666"));
            tab2Tv.setTextColor(Color.parseColor("#2196F3"));
            tab3Tv.setTextColor(Color.parseColor("#666666"));
        }else if(position==2){
            tab1Tv.setTextColor(Color.parseColor("#666666"));
            tab2Tv.setTextColor(Color.parseColor("#666666"));
            tab3Tv.setTextColor(Color.parseColor("#2196F3"));
        }
    }
}
