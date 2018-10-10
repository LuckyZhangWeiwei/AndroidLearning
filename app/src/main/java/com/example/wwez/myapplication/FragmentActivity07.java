package com.example.wwez.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.wwez.Fragment.Fragment01.ResourceFragment;

/*
* fragment 向activity 传值
* */
public class FragmentActivity07 extends AppCompatActivity implements ResourceFragment.MyListener{

    private TextView tv_show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment07);
        tv_show = findViewById(R.id.tv_show);
    }

    @Override
    public void sendMessage(String str) {
        if(str != null && ! "".equals(str)){
        tv_show.setText(str);
        }
    }
}
