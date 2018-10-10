package com.example.wwez.ListView;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wwez.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewMainActivity extends AppCompatActivity implements MyListView.LoadMoreListener, MyListView.IRefreshListener {

    private List<String> mData;
    private MyListView mList;
    private MyAdapter mMyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_main);
        mList = findViewById(R.id.list);
        mList.setLoadMoreListener(this);
        mList.setRefreshListener(this);
        initData();
        mMyAdapter = new MyAdapter();
        mList.setAdapter(mMyAdapter);
    }

    private void initData() {
        mData = new ArrayList<String>();
        for(int i=0;i<20;i++){
            mData.add("我是初始的"+i+"界面");
        }
    }

    @Override
    public void loadMore() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initLoadData();
                mMyAdapter.notifyDataSetChanged();
                mList.setLastItemHiden();
            }
        },1000);
    }
    private void initLoadData() {
        for (int i = 0; i <20 ; i++) {
            mData.add("我是加载更多的"+i+"界面");
        }
    }

    private void setRefreshData(){
        for(int i=0;i<5;i++){
            mData.add(0,"我是刷新数据的"+i+"界面");
        }
    }

    @Override
    public void onRefresh() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setRefreshData();
                mMyAdapter.notifyDataSetChanged();
                mList.refreshComplete();
            }
        },1000);
    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                convertView = View.inflate(ListViewMainActivity.this, R.layout.listviewitem,null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            String s = mData.get(position);
            holder.tv.setText(s);
            return convertView;
        }
    }

    private class ViewHolder {
        TextView tv;
        public ViewHolder(View v){
            tv = v.findViewById(R.id.tv);
        }
    }
}
