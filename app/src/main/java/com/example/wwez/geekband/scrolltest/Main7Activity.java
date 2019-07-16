package com.example.wwez.geekband.scrolltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wwez.myapplication.R;

public class Main7Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        final TextView tv = findViewById(R.id.txt);
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                tv.scrollTo(-200, -100);
//                tv.scrollBy(-200, -100);
                ((LinearLayout)tv.getParent()).scrollBy(-200, -100);
            }
        });
    }
}
