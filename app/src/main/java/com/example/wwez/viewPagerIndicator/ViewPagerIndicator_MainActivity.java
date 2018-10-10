package com.example.wwez.viewPagerIndicator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Window;

import com.example.wwez.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewPagerIndicator_MainActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private ViewPagerIndicator mIndicator;
    private List<String> mTitles = Arrays.asList("短信1","收藏2","推荐3","短信4","收藏5","推荐6","短信7","收藏8","推荐9");
    private List<VpSimpleFragment> mContents = new ArrayList<VpSimpleFragment>() ;
    private FragmentPagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view_pager_indicator__main);

        initViews();
        initData();

        mIndicator.setVisibleTabCount(4);
        mIndicator.setTabItemTitles(mTitles);
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager, 0);
    }

    private void initData() {
        for(String title:mTitles){
            mContents.add(VpSimpleFragment.newInstance(title));
        }
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mContents.get(position);
            }

            @Override
            public int getCount() {
                return mContents.size();
            }
        };
    }

    private void initViews() {
        mViewPager = findViewById(R.id.id_viewpager);
        mIndicator = findViewById(R.id.id_indicator);
    }
}
