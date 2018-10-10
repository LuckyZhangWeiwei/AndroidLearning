package com.example.wwez.tab;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.wwez.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Tab01_MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    private List<View> mViews = new ArrayList<View>();


    private LinearLayout mTabWeixin;
    private LinearLayout mTabFrd;
    private LinearLayout mTabAddress;
    private LinearLayout mTabSetttings;

    private ImageButton mImgWeixin;
    private ImageButton mImgFrd;
    private ImageButton mImgAddress;
    private ImageButton mImgSetttings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tab01__main);

        initView();
        initEvents();
    }

    private void initEvents() {
        mTabWeixin.setOnClickListener(this);
        mTabFrd.setOnClickListener(this);
        mTabAddress.setOnClickListener(this);
        mTabSetttings.setOnClickListener(this);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int currentItem = mViewPager.getCurrentItem();
                resetImg();
                switch (currentItem){
                    case 0:
                        mImgWeixin.setImageResource(R.drawable.tab_weixin_pressed);
                        break;
                    case 1:
                        mImgFrd.setImageResource(R.drawable.tab_find_frd_pressed);
                        break;
                    case 2:
                        mImgAddress.setImageResource(R.drawable.tab_address_pressed);
                        break;
                    case 3:
                        mImgSetttings.setImageResource(R.drawable.tab_settings_pressed);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        mViewPager = findViewById(R.id.id_viewpager);

        mTabWeixin = findViewById(R.id.id_tab_weixin);
        mTabFrd = findViewById(R.id.id_tab_frd);
        mTabAddress = findViewById(R.id.id_tab_address);
        mTabSetttings = findViewById(R.id.id_tab_settings);

        mImgWeixin = findViewById(R.id.id_tab_weixin_img);
        mImgFrd = findViewById(R.id.id_tab_frd_img);
        mImgAddress = findViewById(R.id.id_tab_address_img);
        mImgSetttings = findViewById(R.id.id_tab_settings_img);

        LayoutInflater mInflater = LayoutInflater.from(this);
        View tab01 = mInflater.inflate(R.layout.tab01, null);
        View tab02 = mInflater.inflate(R.layout.tab02, null);
        View tab03 = mInflater.inflate(R.layout.tab03, null);
        View tab04 = mInflater.inflate(R.layout.tab04, null);

        mViews.add(tab01);
        mViews.add(tab02);
        mViews.add(tab03);
        mViews.add(tab04);

        mAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view = mViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(mViews.get(position));
            }
        };
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
      resetImg();
      switch (v.getId()){
          case R.id.id_tab_weixin:
              mViewPager.setCurrentItem(0);
              mImgWeixin.setImageResource(R.drawable.tab_weixin_pressed);
              break;
          case R.id.id_tab_frd:
              mViewPager.setCurrentItem(1);
              mImgFrd.setImageResource(R.drawable.tab_find_frd_pressed);
              break;
          case R.id.id_tab_address:
              mViewPager.setCurrentItem(2);
              mImgAddress.setImageResource(R.drawable.tab_address_pressed);
              break;
          case R.id.id_tab_settings:
              mImgSetttings.setImageResource(R.drawable.tab_settings_pressed);
              mViewPager.setCurrentItem(3);
              break;
      }
    }

    private void resetImg() {
        mImgWeixin.setImageResource(R.drawable.tab_weixin_normal);
        mImgFrd.setImageResource(R.drawable.tab_find_frd_normal);
        mImgAddress.setImageResource(R.drawable.tab_address_normal);
        mImgSetttings.setImageResource(R.drawable.tab_settings_normal);
    }
}
