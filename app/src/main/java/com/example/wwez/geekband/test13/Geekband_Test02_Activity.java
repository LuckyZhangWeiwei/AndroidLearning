package com.example.wwez.geekband.test13;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.wwez.geekband.test13.viewpagerindicator.VpSimpleFragment;
import com.example.wwez.geekband.test13.viewpagerindicator.view.ViewPagerIndicator;
import com.example.wwez.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Geekband_Test02_Activity extends AppCompatActivity {

    private ViewPager mViewPager;

    private ViewPagerIndicator mIndicator;

    private List<String> mTitles = Arrays.asList("短信1", "收藏2", "推荐3", "短信4", "收藏5", "推荐6", "短信7", "收藏8", "推荐9");

    private List<VpSimpleFragment> mContents = new ArrayList<>();

    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.geekbrand_activity_draw);
        setContentView(R.layout.viewpagerindicator);

        initViews();

        initData();

        mIndicator.setVisibleTabCount(3);

        mIndicator.setTabItemTitles(mTitles);


        mViewPager.setAdapter(mAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // tabwidth * positionOffset + position * tabwidth
                mIndicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViews() {
        mViewPager = findViewById(R.id.id_viewpager_geek);

        mIndicator = findViewById(R.id.id_indicator_geek);
    }

    private void initData() {
        for(String title: mTitles ) {
            VpSimpleFragment fragment = VpSimpleFragment.newInstance(title);
            mContents.add(fragment);
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mContents.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mContents.get(position);
            }
        };

    }
}
