package com.example.wwez.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.wwez.myapplication.R;

public class Tab02_MainActivity extends FragmentActivity implements View.OnClickListener{

    private LinearLayout mTabWeixin;
    private LinearLayout mTabFrd;
    private LinearLayout mTabAddress;
    private LinearLayout mTabSetttings;

    private ImageButton mImgWeixin;
    private ImageButton mImgFrd;
    private ImageButton mImgAddress;
    private ImageButton mImgSetttings;

    private Fragment mTab01;
    private Fragment mTab02;
    private Fragment mTab03;
    private Fragment mTab04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tab02__main);

        initView();
        initEvent();

        setSelect(0);
    }

    private void initView() {
        mTabWeixin = findViewById(R.id.id_tab_weixin);
        mTabFrd = findViewById(R.id.id_tab_frd);
        mTabAddress = findViewById(R.id.id_tab_address);
        mTabSetttings = findViewById(R.id.id_tab_settings);

        mImgWeixin = findViewById(R.id.id_tab_weixin_img);
        mImgFrd = findViewById(R.id.id_tab_frd_img);
        mImgAddress = findViewById(R.id.id_tab_address_img);
        mImgSetttings = findViewById(R.id.id_tab_settings_img);
    }

    private void initEvent() {
        mTabWeixin.setOnClickListener(this);
        mTabFrd.setOnClickListener(this);
        mTabAddress.setOnClickListener(this);
        mTabSetttings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        resetImg();
        switch (v.getId()){
            case R.id.id_tab_weixin:
                setSelect(0);
                break;
            case R.id.id_tab_frd:
                setSelect(1);
                break;
            case R.id.id_tab_address:
                setSelect(2);
                break;
            case R.id.id_tab_settings:
                setSelect(3);
                break;
        }
    }
    private void resetImg() {
        mImgWeixin.setImageResource(R.drawable.tab_weixin_normal);
        mImgFrd.setImageResource(R.drawable.tab_find_frd_normal);
        mImgAddress.setImageResource(R.drawable.tab_address_normal);
        mImgSetttings.setImageResource(R.drawable.tab_settings_normal);
    }

    private void setSelect(int i){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hidenAllFragment(transaction);
        switch (i){
            case 0:
                if(mTab01 == null) {
                    mTab01 = new WeixinFragment();
                    transaction.add(R.id.id_content, mTab01);
                } else{
                    transaction.show(mTab01);
                }
                mImgWeixin.setImageResource(R.drawable.tab_weixin_pressed);
                break;
            case 1:
                if(mTab02 == null) {
                    mTab02 = new FrdFragment();
                    transaction.add(R.id.id_content, mTab02);
                } else{
                    transaction.show(mTab02);
                }
                mImgFrd.setImageResource(R.drawable.tab_find_frd_pressed);
                break;
            case 2:
                if(mTab03 == null) {
                    mTab03 = new AddressFragment();
                    transaction.add(R.id.id_content, mTab03);
                } else{
                    transaction.show(mTab03);
                }
                mImgAddress.setImageResource(R.drawable.tab_address_pressed);
                break;
            case 3:
                if(mTab04 == null) {
                    mTab04 = new SettingsFragment();
                    transaction.add(R.id.id_content, mTab04);
                } else{
                    transaction.show(mTab04);
                }
                mImgSetttings.setImageResource(R.drawable.tab_settings_pressed);
                break;
        }
        transaction.commit();
    }

    private void hidenAllFragment(FragmentTransaction transaction) {
        if(mTab01 !=null){
            transaction.hide(mTab01);
        }
        if(mTab02 !=null){
            transaction.hide(mTab02);
        }
        if(mTab03 !=null){
            transaction.hide(mTab03);
        }
        if(mTab04 !=null){
            transaction.hide(mTab04);
        }
    }
}
