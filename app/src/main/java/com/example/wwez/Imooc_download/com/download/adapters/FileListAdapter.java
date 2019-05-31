package com.example.wwez.Imooc_download.com.download.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.wwez.Imooc_download.com.download.entry.FileInfo;
import com.example.wwez.Imooc_download.com.download.services.DownLoadService;
import com.example.wwez.myapplication.R;

import java.util.List;

public class FileListAdapter extends BaseAdapter {
    private Context mContext;
    private List<FileInfo> mFileList;

    public FileListAdapter(Context mContext, List<FileInfo> mFileList) {
        this.mContext = mContext;
        this.mFileList = mFileList;
    }

    @Override
    public int getCount() {
        return mFileList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFileList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FileInfo fileInfo = mFileList.get(position);
        ViewHolder holder = null;
        if(convertView == null) {
           convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_download_listitem, null);
           holder = new ViewHolder();
            holder.tvFile = convertView.findViewById(R.id.tv_fileName);
            holder.pbProgressBar = convertView.findViewById(R.id.pb_progress);
            holder.BtnStop = convertView.findViewById(R.id.btn_stop);
            holder.BtnStart = convertView.findViewById(R.id.btn_start);

            holder.pbProgressBar.setMax(100);
            holder.BtnStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DownLoadService.class);
                    intent.setAction(DownLoadService.ACTION_STOP);
                    intent.putExtra("fileInfo", fileInfo);
                    mContext.startService(intent);
                }
            });
            holder.BtnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DownLoadService.class);
                    intent.setAction(DownLoadService.ACTION_START);
                    intent.putExtra("fileInfo", fileInfo);
                    mContext.startService(intent);
                }
            });

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvFile.setText(fileInfo.getFileName()+ ":" + fileInfo.getFinished() + "%");

        holder.pbProgressBar.setProgress(fileInfo.getFinished());

        return convertView;
    }

    public void updateProgress(int id, int progress) {
        FileInfo fileInfo = mFileList.get(id);
        fileInfo.setFinished(progress);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView tvFile;
        Button BtnStop;
        Button BtnStart;
        ProgressBar pbProgressBar;
    }
}
