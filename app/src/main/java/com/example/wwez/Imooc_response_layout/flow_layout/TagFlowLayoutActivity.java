package com.example.wwez.Imooc_response_layout.flow_layout;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wwez.Imooc_response_layout.flow_layout.adapter.TagAdapter;
import com.example.wwez.Imooc_response_layout.flow_layout.view.TagFlowLayout;
import com.example.wwez.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TagFlowLayoutActivity extends AppCompatActivity {

    private TagFlowLayout mTagFlowLayout;

//    private List<String> mDataList = Arrays.asList("Android", "Java", "Object C", "IOS", ".net", "C#", "Spring",
//            "Immoc", "ZhangWeiwei","Android", "Java", "Object C", "IOS", ".net", "C#", "Spring",
//            "Immoc", "ZhangWeiwei");

    List<String> mDataList = new ArrayList<>();

    private TagAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_flow_layout);

        mDataList.addAll(new ArrayList<String>(Arrays.asList("GitHub brings together the world largest community of developers to discover share and build better software From open source projects to private team".split(" "))));

        mTagFlowLayout = findViewById(R.id.tag_flow_layout);

        mTagFlowLayout.setMaxSelectCount(4);

        mTagFlowLayout.setAdapter(mAdapter = new TagAdapter() {
            @Override
            public int getItemCount() {
                return mDataList.size();
            }

            @Override
            public View createView(LayoutInflater inflater, ViewGroup parentContainer, int position) {
                return inflater.inflate(R.layout.flow_layout_select_item_tag, parentContainer, false);
            }

            @Override
            public void bindView(View view, int position) {
                final TextView tvTag = view.findViewById(R.id.tv);
                tvTag.setText(mDataList.get(position));
            }

            @Override
            public void tipForSelectedMax(View v, int mMaxSelectCount) {
                super.tipForSelectedMax(v, mMaxSelectCount);
                Toast.makeText(v.getContext(), String.format("最多选择%s个选项", mMaxSelectCount), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onItemSelected(View view, int position) {
                super.onItemSelected(view, position);
                TextView tag = view.findViewById(R.id.tv);
                tag.setTextColor(Color.parseColor("#DB1212"));
            }

            @Override
            public void onItemUnSelected(View view, int position) {
                super.onItemUnSelected(view, position);
                TextView tag = view.findViewById(R.id.tv);
                tag.setTextColor(Color.parseColor("#333333"));
            }
        });
    }

    public void changeData(View view) {
        mDataList.clear();

        mDataList.addAll(new ArrayList<String>(Arrays.asList("CircleCI easily integrates with GitHub and GitHub Enterprise".split(" "))));

        mTagFlowLayout.setMaxSelectCount(1);

        mAdapter.notifyDataSetChanged();
    }
}
