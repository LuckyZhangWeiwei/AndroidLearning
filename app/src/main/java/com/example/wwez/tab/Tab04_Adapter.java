package com.example.wwez.tab;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class Tab04_Adapter extends FragmentPagerAdapter{
    public static String[] TITLES = new String[]{"课程","求课","计划","问答","分享","视频"};
    public Tab04_Adapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        Tab04Fragment fragment = new Tab04Fragment(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
