package com.example.wwez.scrolltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.wwez.myapplication.R;

public class ScrollTest_MainActivity extends AppCompatActivity {

    private Button Btn;
    private LinearLayout Ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_test__main);
        Btn = findViewById(R.id.btn1);
        Ll = findViewById(R.id.group1);
    }

    public void onClick(View view) {
//        ((View)Btn.getParent()).scrollBy(-20, -10);
        Ll.scrollBy(0,10);
//        ((View)Ll.getParent()).scrollBy(50, 50);
    }
}
