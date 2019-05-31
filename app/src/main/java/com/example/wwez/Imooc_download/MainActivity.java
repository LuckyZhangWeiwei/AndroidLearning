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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.wwez.Imooc_download.com.download.entry.FileInfo;
import com.example.wwez.Imooc_download.com.download.services.DownLoadService;
import com.example.wwez.myapplication.R;

public class MainActivity extends AppCompatActivity {

    private TextView mTvFileName;
    private ProgressBar mPbProgressbar;
    private Button mBtnStop;
    private Button mBtnStart;
    FileInfo fileInfo;
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_main);
        requestAccess();
        bindView();
        initData();
        bindEvent();
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
                mPbProgressbar.setProgress(Integer.parseInt(finished.toString()));
                mTvFileName.setText(fileInfo.getFileName() +":" + finished + "%");
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
        registerReceiver(mReceiver, filter);
    }

    private void bindEvent() {
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DownLoadService.class);
                intent.setAction(DownLoadService.ACTION_START);
                intent.putExtra("fileInfo", fileInfo);
                startService(intent);
            }
        });

        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DownLoadService.class);
                intent.setAction(DownLoadService.ACTION_STOP);
                intent.putExtra("fileInfo", fileInfo);
                startService(intent);
            }
        });
    }

    private void initData() {
        fileInfo = new FileInfo(0,
                "http://download.kugou.com/download/kugou_pc",
                "kugou.exe",
                0,
                0
                );

        mPbProgressbar.setMax(100);
    }

    private void bindView() {
        mTvFileName = findViewById(R.id.tv_fileName);
        mPbProgressbar = findViewById(R.id.pb_progress);
        mBtnStop = findViewById(R.id.btn_stop);
        mBtnStart = findViewById(R.id.btn_start);
    }
}
