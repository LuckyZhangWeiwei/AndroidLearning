package com.example.wwez.tab;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Window;

import com.example.wwez.myapplication.R;
import com.viewpagerindicator.TabPageIndicator;


/*
* viewpagerindicator viewpager
* */
public class Tab04_MainActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private TabPageIndicator mTabPageIndicator;;
    private Tab04_Adapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tab04__main);
        initView();
    }

    private void initView() {
        mViewPager = findViewById(R.id.id_viewpager);
        mTabPageIndicator = findViewById(R.id.id_indicator_04);
        mAdapter = new Tab04_Adapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mTabPageIndicator.setViewPager(mViewPager, 0);
    }
}
