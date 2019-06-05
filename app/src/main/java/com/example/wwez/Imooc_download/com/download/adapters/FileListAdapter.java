package com.example.wwez.Imooc_download.com.download.adapters;

import android.content.Context;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
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
    private static Context mContext;
    private static List<FileInfo> mFileList;
    private static Messenger mMessenger;

    public FileListAdapter(Context mContext, List<FileInfo> mFileList) {
        this.mContext = mContext;
        this.mFileList = mFileList;
    }

    public void setmMessenger(Messenger messenger) {
        this.mMessenger = messenger;
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
        ViewHolder holder = null;
        if(convertView == null) {
           convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_download_listitem, null);
           holder = new ViewHolder();
            holder.tvFile = convertView.findViewById(R.id.tv_fileName);
            holder.pbProgressBar = convertView.findViewById(R.id.pb_progress);
            holder.BtnStop = convertView.findViewById(R.id.btn_stop);
            holder.BtnStart = convertView.findViewById(R.id.btn_start);
            holder.pbProgressBar.setMax(100);
            holder.onClickListener = new MyOnClickListener();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FileInfo fileInfo = mFileList.get(position);

        holder.onClickListener.setPosition(position, holder);
        holder.BtnStart.setOnClickListener(holder.onClickListener);
        holder.BtnStop.setOnClickListener(holder.onClickListener);

        holder.tvFile.setText(fileInfo.getFileName()+ ":" + fileInfo.getFinished() + "%");

        holder.pbProgressBar.setProgress(fileInfo.getFinished());

        return convertView;
    }

    /**
     * 设置viewHolder的数据
     * @param holder
     * @param itemIndex
     */
    private void setData(ViewHolder holder, int itemIndex, int progressbarValue) {
        FileInfo fileInfo = mFileList.get(itemIndex);
        holder.tvFile.setText(fileInfo.getFileName()+ ":" + progressbarValue + "%");
        holder.pbProgressBar.setProgress(progressbarValue);
    }


    /**
     * 局部刷新
     * @param view
     * @param itemIndex
     */
    public void updateView(View view, int itemIndex, int progressbarValue) {
        if(view == null) {
            return;
        }
        //从view中取得holder
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.pbProgressBar = view.findViewById(R.id.pb_progress);
        holder.tvFile = view.findViewById(R.id.tv_fileName);
        setData(holder, itemIndex, progressbarValue);
    }


    static class ViewHolder {
        TextView tvFile;
        Button BtnStop;
        Button BtnStart;
        ProgressBar pbProgressBar;
        MyOnClickListener onClickListener;
    }

    private static class MyOnClickListener implements View.OnClickListener {
        private int mPosition;
        private ViewHolder mHolder;

        public void setPosition(int position, ViewHolder holder) {
            this.mPosition = position;
            this.mHolder = holder;
        }

        @Override
        public void onClick(View v) {
            Button btn = (Button) v;
            FileInfo fileInfo = mFileList.get(mPosition);
            if(R.id.btn_start == btn.getId()) {
                Message msg = new Message();
                msg.what = DownLoadService.MSG_START;
                msg.obj = fileInfo;
                try {
                    mMessenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            } else if(R.id.btn_stop == btn.getId()) {
                Message msg = new Message();
                msg.what = DownLoadService.MSG_STOP;
                msg.obj = fileInfo;
                try {
                    mMessenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
