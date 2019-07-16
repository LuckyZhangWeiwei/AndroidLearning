package com.example.wwez.Imooc_response_layout.flow_layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.wwez.Imooc_response_layout.flow_layout.view.FlowLayout;
import com.example.wwez.myapplication.R;

public class FlowLayoutActivity extends AppCompatActivity {

    private FlowLayout mFlowLayout;
    private LinearLayout mLlRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);

        mLlRoot = findViewById(R.id.root);
        mFlowLayout = findViewById(R.id.flowlayout);

        Button button = new Button(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(lp);
        button.setText("add");

        mFlowLayout.addView(button);

    }
}
