package com.example.wwez.RecyclerView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.wwez.myapplication.MainActivity;
import com.example.wwez.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private RecyclerView mRecycleView;
    private List<String> mDatas;
    private MySimpleAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initDatas();
        initView();
        mAdapter = new MySimpleAdapter(this, mDatas);
        mRecycleView.setAdapter(mAdapter);
        // 设置recycleView 的布局管理
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycleView.setLayoutManager(linearLayoutManager);
        // 设置recycleView的Item 间分割线
        // mRecycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        // 设置动画
        mRecycleView.setItemAnimator(new DefaultItemAnimator());

        mAdapter.setmOnItemClickListener(new MySimpleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(Main2Activity.this, mDatas.get(position)+"click， position:" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(Main2Activity.this, mDatas.get(position)+" long click, position:" + position, Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_gridview:
                mRecycleView.setLayoutManager(new GridLayoutManager(this, 3));
                break;
            case R.id.action_hor_gridview:
                mRecycleView.setLayoutManager(
                        new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL )
                );
                break;
            case R.id.action_listview:
                mRecycleView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.action_staggered:
                Intent intent = new Intent(this, StaggeredActivity.class);
                startActivity(intent);
                break;
            case R.id.action_Add:
                mAdapter.addData(1);
                break;
            case R.id.action_Delete:
                mAdapter.deleteData(1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
