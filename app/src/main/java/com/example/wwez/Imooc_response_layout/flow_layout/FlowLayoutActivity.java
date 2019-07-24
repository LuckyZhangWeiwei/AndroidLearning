package com.example.wwez.Imooc_response_layout.flow_layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wwez.Imooc_response_layout.flow_layout.view.FlowLayout;
import com.example.wwez.myapplication.R;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class FlowLayoutActivity extends AppCompatActivity {

    private FlowLayout mFlowLayout;
    private LinearLayout mLlRoot;
    private static final List<String> mDataList =Arrays.asList("Android", "Java", "Object C", "IOS", ".net", "C#", "Spring",
            "Immoc", "ZhangWeiwei");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);

        mLlRoot = findViewById(R.id.root);
        mFlowLayout = findViewById(R.id.flowlayout);

//        Button button = new Button(this);
//        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        button.setLayoutParams(lp);
//        button.setText("add");
//
//        mFlowLayout.addView(button);

    }

    public void addtag(View view) {
       TextView tag = (TextView) LayoutInflater.from(this)
                       .inflate(R.layout.flow_layout_item_tag, mFlowLayout, false);
        tag.setText(mDataList.get((int) (Math.random() * (mDataList.size() - 1))));
        mFlowLayout.addView(tag);
    }
}
