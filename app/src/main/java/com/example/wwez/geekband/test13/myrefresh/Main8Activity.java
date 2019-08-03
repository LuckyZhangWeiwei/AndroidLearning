package com.example.wwez.geekband.test13.myrefresh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.wwez.RecyclerView.MySimpleAdapter;
import com.example.wwez.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Main8Activity extends AppCompatActivity {

    private RecyclerView mRecycleView;
    private List<String> mDatas;
    private MySimpleAdapter mAdapter;
    private RefreshWithHeader mRefreshWithHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        initDatas();
        mRefreshWithHeader = findViewById(R.id.refreshWithHeader);

        mRefreshWithHeader.setRefreshListener(new RefreshWithHeader.RefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(), "Refresh", Toast.LENGTH_SHORT).show();
            }
        });

        mRecycleView = findViewById(R.id.id_recycleView2222);
        mAdapter = new MySimpleAdapter(this, mDatas);
        mRecycleView.setAdapter(mAdapter);
        // 设置recycleView 的布局管理
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        mRecycleView.setLayoutManager(linearLayoutManager);
    }

    private void initDatas() {
        mDatas = new ArrayList();
        for(int i='A'; i <='K'; i++){
            mDatas.add(""+(char)i);
        }
    }
}
