package com.example.wwez.Imooc_response_layout.flow_layout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.wwez.myapplication.R;

public class Main6Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
    }

    public void goFlowLayout(View view) {
        Intent i = new Intent(this, FlowLayoutActivity.class);
        startActivity(i);

    }
}
