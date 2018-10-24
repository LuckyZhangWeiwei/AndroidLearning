package com.example.wwez.flowlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wwez.myapplication.R;

public class FlowLayoutMainActivity extends AppCompatActivity {

    private String[] mVals = new String[]{
            "Hello","Android","IOS",".net","c#","Java","Php",
            "webpack","react","vue","cloudy","front end","webservice",
            "erp","JD Edward", "BI","sqlserver"
    };

    private FlowLayout mFlowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout_main);
        mFlowLayout = findViewById(R.id.flowlayout);
        initData();
    }
//    public void initData(){
//        for(int i=0; i< mVals.length; i++){
//            Button btn = new Button(this);
//            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
//                    ViewGroup.LayoutParams.WRAP_CONTENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT
//            );
//            btn.setText(mVals[i]);
//            mFlowLayout.addView(btn, lp);
//        }
//    }

    public void initData(){
        LayoutInflater mInflater = LayoutInflater.from(this);
        for(int i=0; i< mVals.length; i++){
           TextView tv = (TextView) mInflater.inflate(R.layout.tag, mFlowLayout, false);
           tv.setText(mVals[i]);
           mFlowLayout.addView(tv);
        }
    }
}
