package com.example.wwez.InflaterTest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.wwez.myapplication.R;

//https://www.jb51.net/article/112145.htm

public class InflaterActivity extends AppCompatActivity {
    LinearLayout mLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in);

//        mLinearLayout = this.findViewById(R.id.mLinearLayout);
//
//        View inflatedView = LayoutInflater.from(this).inflate(R.layout.item_text, mLinearLayout,true);
//        Log.d("TAG", "inflated view is " + inflatedView);
//        mLinearLayout.addView(inflatedView);

//        View inflatedView2 = LayoutInflater.from(this).inflate(R.layout.item_text, mLinearLayout,false);
//        Log.d("TAG", "inflated view is " + inflatedView2);
//        mLinearLayout.addView(inflatedView2);

        mLinearLayout = (LinearLayout) findViewById(R.id.mLinearLayout);
        View inflatedView = LayoutInflater.from(this).inflate(R.layout.item_text, null);
        Log.d("TAG", "inflated view is " + inflatedView);
        mLinearLayout.addView(inflatedView);


    }
}
