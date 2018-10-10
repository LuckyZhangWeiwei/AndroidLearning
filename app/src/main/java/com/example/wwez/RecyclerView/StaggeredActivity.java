package com.example.wwez.RecyclerView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.wwez.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class StaggeredActivity extends AppCompatActivity {

    private RecyclerView mRecycleView;
    private List<String> mDatas;
    private StaggerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initDatas();
        initView();
        mAdapter = new StaggerAdapter(this, mDatas);
        mRecycleView.setAdapter(mAdapter);
        // 设置recycleView 的布局管理
        StaggeredGridLayoutManager staggeredGridManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(staggeredGridManager);


        mAdapter.setmOnItemClickListener(new MySimpleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(StaggeredActivity.this, mDatas.get(position)+"click， position:" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                mAdapter.deleteData(position);
            }
        });
    }

    private void initView() {
        mRecycleView = findViewById(R.id.id_recycleView);
    }

    private void initDatas() {
        mDatas = new ArrayList<String>();
        for(int i='A'; i <='z'; i++){
            mDatas.add(""+(char)i);
        }
    }
}
