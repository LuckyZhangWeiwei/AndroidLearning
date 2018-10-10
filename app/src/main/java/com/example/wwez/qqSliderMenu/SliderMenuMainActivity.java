package com.example.wwez.qqSliderMenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;


import com.example.wwez.myapplication.R;

public class SliderMenuMainActivity extends AppCompatActivity {

    SlidingMenu sm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_slider_menu_main);
        sm = findViewById(R.id.sliderMenu);
    }

    public void onClick(View view){
        sm.toggleMenu();
    }
}
