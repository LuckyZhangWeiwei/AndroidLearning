package com.example.wwez.myapplication;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.wwez.FragmentTabs.GiftFragment;
import com.example.wwez.FragmentTabs.OrderFragment;
import com.example.wwez.FragmentTabs.ShareFragment;
import com.example.wwez.FragmentTabs.ShopRankFragment;
/*
* Fragment tab 切换
* */
public class FragmentActivity04 extends AppCompatActivity implements View.OnClickListener{

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private RadioButton rb_shoprank, rb_share, rb_gift, rb_order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment04);
        initView();
        manager=getSupportFragmentManager();
        transaction=manager.beginTransaction();
        transaction.add(R.id.content_layout, new ShopRankFragment());
        transaction.commit();
    }
    public void initView(){
        rb_shoprank = findViewById(R.id.rb_shoprank);
        rb_shoprank.setOnClickListener(this);
        rb_share = findViewById(R.id.rb_share);
        rb_share.setOnClickListener(this);
        rb_gift = findViewById(R.id.rb_gift);
        rb_gift.setOnClickListener(this);
        rb_order = findViewById(R.id.rb_order);
        rb_order.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        transaction = manager.beginTransaction();
        switch (v.getId()){
            case R.id.rb_gift:
                transaction.replace(R.id.content_layout, new GiftFragment());
                break;
            case R.id.rb_order:
                transaction.replace(R.id.content_layout, new OrderFragment());
                break;
            case R.id.rb_share:
                transaction.replace(R.id.content_layout, new ShareFragment());
                break;
            case R.id.rb_shoprank:
                transaction.replace(R.id.content_layout, new ShopRankFragment());
                break;
        }
        transaction.commit();
    }
}
