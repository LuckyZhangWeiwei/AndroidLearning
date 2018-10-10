package com.example.wwez.RecyclerView;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class StaggerAdapter extends MySimpleAdapter{
    private List<String> mDatas;
    private List<Integer> mHeight;
    public StaggerAdapter(Context context, List<String> datas){
        super(context, datas);
        mDatas = datas;
        mHeight = new ArrayList<Integer>();
        for(int i=0; i<mDatas.size(); i++ ){
            mHeight.add((int)(100 + Math.random()* 300));
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = mHeight.get(position);
        holder.itemView.setLayoutParams(layoutParams);
        holder.tv.setText(mDatas.get(position));

        setUpItemEvent(holder);
    }
}

