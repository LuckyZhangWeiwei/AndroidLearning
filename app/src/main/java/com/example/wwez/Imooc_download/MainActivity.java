package com.example.wwez.Imooc_download;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wwez.Imooc_download.com.download.adapters.FileListAdapter;
import com.example.wwez.Imooc_download.com.download.entry.FileInfo;
import com.example.wwez.Imooc_download.com.download.services.DownLoadService;
import com.example.wwez.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mLvFile;
    private List<FileInfo> mFileList;
    private FileListAdapter mAdapter;

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_main);
        requestAccess();
        bindView();
        initData();
        initBroadCast();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(DownLoadService.ACTION_UPDATE.equalsIgnoreCase(intent.getAction())) {
                Long finished = intent.getLongExtra("finished", 0);
                int id = intent.getIntExtra("id", 0);
                updateView(id, Integer.parseInt(finished.toString()));
            } else if(DownLoadService.ACTION_FINISHED.equalsIgnoreCase(intent.getAction())) {
                FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
                updateView(fileInfo.getId(), 0);
                Toast.makeText(MainActivity.this, fileInfo.getFileName() + "下载结束", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestAccess() {
        this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    private void initBroadCast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownLoadService.ACTION_UPDATE);
        filter.addAction(DownLoadService.ACTION_FINISHED);
        registerReceiver(mReceiver, filter);
    }


    private void initData() {
        mFileList = new ArrayList<>();

        FileInfo fileInfo = new FileInfo(0,
                "http://download.kugou.com/download/kugou_pc",
                "kugou.exe",
                0,
                0
                );
        FileInfo fileInfo2 = new FileInfo(1,
                "http://www.imooc.com/download/Activator.exe",
                "Activator.exe",
                0,
                0
        );
        FileInfo fileInfo3 = new FileInfo(2,
                "http://www.imooc.com/download/iTunes64Setup.exe",
                "iTunes64Setup.exe",
                0,
                0
        );
        FileInfo fileInfo4 = new FileInfo(3,
                "http://www.imooc.com/download/BaiduPlayerNetSetup_100.exe",
                "BaiduPlayerNetSetup_100.exe",
                0,
                0
        );
        FileInfo fileInfo5 = new FileInfo(4,
                "http://www.imooc.com/mobile/appdown",
                "imooc.apk",
                0,
                0
        );

        mFileList.add(fileInfo);
        mFileList.add(fileInfo2);
        mFileList.add(fileInfo3);
        mFileList.add(fileInfo4);
        mFileList.add(fileInfo5);

        mAdapter = new FileListAdapter(this, mFileList);
        mLvFile.setAdapter(mAdapter);
    }

    private void bindView() {
        mLvFile = findViewById(R.id.lv_file);
    }

    private void updateView(int itemIndex, int progressbarValue) {
        //得到第一个可显示控件的位置，
        int visiblePosition = mLvFile.getFirstVisiblePosition();
        //只有当要更新的view在可见的位置时才更新，不可见时，跳过不更新
        if (itemIndex - visiblePosition >= 0) {
            //得到要更新的item的view
            View view = mLvFile.getChildAt(itemIndex - visiblePosition);
            //调用adapter更新界面
            mAdapter.updateView(view, itemIndex, progressbarValue);
        }
    }

}
