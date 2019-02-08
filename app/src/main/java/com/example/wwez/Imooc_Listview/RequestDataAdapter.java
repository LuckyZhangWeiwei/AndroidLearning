package com.example.wwez.Imooc_Listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wwez.myapplication.R;

import java.util.List;

public class RequestDataAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    List<LessonInfo> mLessonInfos;

    Context mContext;

    public RequestDataAdapter(Context context , List<LessonInfo> infos) {
        mLessonInfos = infos;
        mContext = context;
    }

    public void appendData(List<LessonInfo> infos) {
        mLessonInfos.addAll(infos);
    }
    @Override
    public int getCount() {
        return mLessonInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mLessonInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_app_list_view, null);
            viewHolder.mAppIcon  = convertView.findViewById(R.id.icon_image_view);
            viewHolder.mAppName = convertView.findViewById(R.id.app_name_text_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mAppName.setText(mLessonInfos.get(position).getmName());
        viewHolder.mAppIcon.setImageBitmap(mLessonInfos.get(position).getmIconUrl());
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    public class ViewHolder {
        public ImageView mAppIcon;
        public TextView mAppName;
    }
}
