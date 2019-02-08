package com.example.wwez.Imooc_Listview;

import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wwez.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        ListView appListview = findViewById(R.id.app_list_view);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = layoutInflater.inflate(R.layout.header_list_view, null);
        appListview.addHeaderView(headerView);
//        appListview.getOnItemClickListener()
        appListview.setAdapter(new AppListAdapter(getAppInfos()));
    }

    /*
    * 获取app信息
    * */
    private List<ResolveInfo> getAppInfos(){
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        return getPackageManager().queryIntentActivities(intent, 0);
    }

    public class AppListAdapter extends BaseAdapter{

        List<ResolveInfo> mApps;

        public AppListAdapter(List<ResolveInfo> apps){
            mApps = apps;
        }

        @Override
        public int getCount() {
            return mApps.size();
        }

        @Override
        public Object getItem(int position) {
            return mApps.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = new ViewHolder();

            if(convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = layoutInflater.inflate(R.layout.item_app_list_view, null);

                viewHolder.appIcon = convertView.findViewById(R.id.icon_image_view);

                viewHolder.appName= convertView.findViewById(R.id.app_name_text_view);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.appName.setText(mApps.get(position).activityInfo.loadLabel(getPackageManager()));

            viewHolder.appIcon.setImageDrawable(mApps.get(position).activityInfo.loadIcon(getPackageManager()));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                String packageName = mApps.get(position).activityInfo.packageName;

                String classString = mApps.get(position).activityInfo.name;

                ComponentName componentName = new ComponentName(packageName, classString);

                final Intent intent = new Intent();
                intent.setComponent(componentName);
                startActivity(intent);
                }
            });
            return convertView;
        }

        public class ViewHolder {
            public ImageView appIcon;
            public TextView appName;
        }
    }
}
