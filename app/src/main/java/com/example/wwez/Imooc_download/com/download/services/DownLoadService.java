package com.example.wwez.Imooc_download.com.download.services;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Switch;

import com.example.wwez.Imooc_download.com.download.entry.FileInfo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.RandomAccess;

public class DownLoadService extends Service {
    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_FINISHED = "ACTION_FINISHED";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/downloads/";
    public static final int MSG_INIT = 0;
    public static final  int MSG_BIND = 2;
    public static final  int MSG_START = 3;
    public static final  int MSG_STOP = 4;
    public static final  int MSG_FINISH = 5;
    public static final  int MSG_UPDATE = 6;
    private Map<Integer, DownLoadTask> mTasks = new LinkedHashMap<>();
    private Messenger mActivityMessenger;

    public DownLoadService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(ACTION_START.equalsIgnoreCase(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            Log.i("zww", "start: "+fileInfo.toString());
            DownLoadTask.sExecutorService.execute(new InitThread(fileInfo));
        } else if(ACTION_STOP.equalsIgnoreCase(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            Log.i("zww", "stop: "+fileInfo.toString());
            DownLoadTask task = mTasks.get(fileInfo.getId());
            if(task != null)
                task.isPause = true;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Messenger messager = new Messenger(mHandler);
        return messager.getBinder();
    }

    Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            FileInfo fileInfo;
            switch (msg.what) {
                case MSG_INIT:
                    fileInfo = (FileInfo) msg.obj;
                    Log.i("zww", "Init:" + fileInfo.toString());
                    DownLoadTask mDownLoadTask = new DownLoadTask(DownLoadService.this, mActivityMessenger, fileInfo, 3);
                    mDownLoadTask.downLoad();

                    mTasks.put(fileInfo.getId(), mDownLoadTask);
                    Message msg1 = new Message();
                    msg1.what = MSG_START;
                    msg1.obj = fileInfo;
                    try {
                        mActivityMessenger.send(msg1);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;

                case MSG_BIND:
                    mActivityMessenger = msg.replyTo;
                    break;
                case MSG_START:
                    fileInfo = (FileInfo) msg.obj;
                    DownLoadTask.sExecutorService.execute(new InitThread(fileInfo));
                    break;
                case MSG_STOP:
                    fileInfo = (FileInfo) msg.obj;
                    DownLoadTask task = mTasks.get(fileInfo.getId());
                    if(task != null)
                        task.isPause = true;
                    break;

            }
        }
    };

    class InitThread extends Thread {
        private FileInfo mFileInfo;
        private RandomAccessFile raf;
        private HttpURLConnection conn;
        public InitThread(FileInfo fileInfo) {
            mFileInfo = fileInfo;
        }
        @Override
        public void run(){
            try {
                URL url = new URL(mFileInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                int length = -1;
                if(conn.getResponseCode() == 200) {
                    length = conn.getContentLength();
                }
                if(length <= 0) return;

                File dir = new File(DOWNLOAD_PATH);
                if(!dir.exists()) {
                    dir.mkdir();
                }
                File file = new File(dir, mFileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");
                raf.setLength(length);
                mFileInfo.setLength(length);
                mHandler.obtainMessage(MSG_INIT, mFileInfo).sendToTarget();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                conn.disconnect();
            }
        }
    }
}
