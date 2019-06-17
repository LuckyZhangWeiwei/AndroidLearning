package com.example.wwez.geekband.test01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.wwez.geekband.test13.Geekband_Test02_Activity;
import com.example.wwez.geekband.test17.TestFragmentActivity;
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
    }
}
