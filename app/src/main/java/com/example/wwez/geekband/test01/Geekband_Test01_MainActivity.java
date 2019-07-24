package com.example.wwez.geekband.test01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.wwez.geekband.scrolltest.Main7Activity;
import com.example.wwez.geekband.test13.Geekband_Test02_Activity;
import com.example.wwez.geekband.test13.drawlayout.DrawLayoutActivity;
import com.example.wwez.geekband.test17.TestFragmentActivity;
import com.example.wwez.geekband.test19.Test19Activity;
import com.example.wwez.myapplication.R;

public class Geekband_Test01_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geekband__test01__main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Geekband_Test01_MainActivity.this, Geekband_Test02_Activity.class));
            }
        });

        findViewById(R.id.btn17).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Geekband_Test01_MainActivity.this, TestFragmentActivity.class));
            }
        });

        findViewById(R.id.btn19).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Geekband_Test01_MainActivity.this, Test19Activity.class));
            }
        });

        findViewById(R.id.btn20).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Geekband_Test01_MainActivity.this, Main7Activity.class));
            }
        });

        findViewById(R.id.btn21).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Geekband_Test01_MainActivity.this, DrawLayoutActivity.class));
            }
        });
    }
}