package com.example.wwez.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wwez.com.example.wwez.datamodel.Friend;
import com.example.wwez.myapplication.R;

import java.util.List;

public class FriendsAdapter extends BaseAdapter{
    private List<Friend> mData;
    private Context context;
    public FriendsAdapter(Context _context, List<Friend> _mdata){
        mData = _mdata;
        context = _context;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       Friend friend = mData.get(position);
       View view;
       Holder holder;
       if(convertView != null){
           view = convertView;
           holder =(Holder) view.getTag();
       }else {
           view = View.inflate(context, R.layout.friend_list_item, null);
           holder = new Holder();
           holder.iv_avatar = view.findViewById(R.id.iv_avatar);
           holder.txt_nickname = view.findViewById(R.id.txt_nickname);
           holder.txt_des = view.findViewById(R.id.txt_des);
           view.setTag(holder);
       }

        holder.iv_avatar.setImageResource(friend.getAvatar());

        holder.txt_nickname.setText(friend.getNickname());

        holder.txt_des.setText(friend.getDesname());
       return view;
    }
    private static class Holder {
        public ImageView iv_avatar;
        public TextView txt_nickname;
        public TextView txt_des;
    }
}
