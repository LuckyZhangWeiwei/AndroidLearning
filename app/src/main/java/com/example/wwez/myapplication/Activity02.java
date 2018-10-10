package com.example.wwez.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wwez.adapter.FriendsAdapter;
import com.example.wwez.com.example.wwez.datamodel.Friend;

import java.util.ArrayList;
import java.util.List;

public class Activity02 extends AppCompatActivity {

    private ListView lv_list;
    private FriendsAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_02);
        lv_list = findViewById(R.id.lv_list_friend);
        mAdapter = new FriendsAdapter(this, getData());
        lv_list.setAdapter(mAdapter);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friend f = (Friend) mAdapter.getItem(position);
                String nickname = f.getNickname();
                Toast.makeText(Activity02.this, nickname,Toast.LENGTH_SHORT).show();
            }
        });
        lv_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Friend f = (Friend) mAdapter.getItem(position);
                String des = f.getDesname();
                Toast.makeText(Activity02.this, des,Toast.LENGTH_SHORT).show();
                return true; //不会传递  false 会传递  给onitemclick 事件
            }
        });
    }

    public List<Friend> getData() {
        List<Friend> list = new ArrayList<Friend>();
        Friend friend1 = new Friend();
        friend1.setDesname("啊打发 手 动 阀手动阀");
        friend1.setAvatar(R.drawable.img1);
        friend1.setNickname("小明");
        list.add(friend1);
        Friend friend2 = new Friend();
        friend2.setDesname("啊打发 手 动 阀手动阀");
        friend2.setAvatar(R.drawable.img2);
        friend2.setNickname("小明");
        list.add(friend2);
        Friend friend3 = new Friend();
        friend3.setDesname("啊打发 手 动 阀手动阀");
        friend3.setAvatar(R.drawable.img3);
        friend3.setNickname("小明");
        list.add(friend3);
        Friend friend4 = new Friend();
        friend4.setDesname("啊打发 手 动 阀手动阀");
        friend4.setAvatar(R.drawable.img4);
        friend4.setNickname("小明");
        list.add(friend4);
        Friend friend5 = new Friend();
        friend5.setDesname("啊打发 手 动 阀手动阀");
        friend5.setAvatar(R.drawable.img5);
        friend5.setNickname("小明");
        list.add(friend5);
        Friend friend6 = new Friend();
        friend6.setDesname("啊打发 手 动 阀手动阀");
        friend6.setAvatar(R.drawable.img6);
        friend6.setNickname("小明");
        list.add(friend6);
        Friend friend11 = new Friend();
        friend11.setDesname("啊打发 手 动 阀手动阀");
        friend11.setAvatar(R.drawable.img1);
        friend11.setNickname("小明");
        list.add(friend11);
        Friend friend12 = new Friend();
        friend12.setDesname("啊打发 手 动 阀手动阀");
        friend12.setAvatar(R.drawable.img2);
        friend12.setNickname("小明");
        list.add(friend12);
        Friend friend13 = new Friend();
        friend13.setDesname("啊打发 手 动 阀手动阀");
        friend13.setAvatar(R.drawable.img3);
        friend13.setNickname("小明");
        list.add(friend13);
        Friend friend14 = new Friend();
        friend14.setDesname("啊打发 手 动 阀手动阀");
        friend14.setAvatar(R.drawable.img4);
        friend14.setNickname("小明");
        list.add(friend14);
        Friend friend15 = new Friend();
        friend15.setDesname("啊打发 手 动 阀手动阀");
        friend15.setAvatar(R.drawable.img5);
        friend15.setNickname("小明");
        list.add(friend15);
        Friend friend16 = new Friend();
        friend16.setDesname("啊打发 手 动 阀手动阀");
        friend16.setAvatar(R.drawable.img6);
        friend16.setNickname("小明");
        list.add(friend16);
        for(int i =0 ; i<500;i++){
            Friend f =new Friend();
            f.setDesname("啊打发 手 动 阀手动阀");
            f.setAvatar(R.drawable.img5);
            f.setNickname("小明");
            list.add(f);
        }
        return list;
    }
}
