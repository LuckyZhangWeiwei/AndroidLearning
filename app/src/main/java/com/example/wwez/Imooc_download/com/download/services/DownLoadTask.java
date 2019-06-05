package com.example.wwez.Imooc_download.com.download.services;

import android.content.Context;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.example.wwez.Imooc_download.com.download.db.ThreadDAO;
import com.example.wwez.Imooc_download.com.download.entry.FileInfo;
import com.example.wwez.Imooc_download.com.download.entry.ThreadInfo;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownLoadTask {
    private Context mContext;
    private FileInfo mFileInfo;
    private ThreadDAO mDao;
    private long mFinished = 0;
    public boolean isPause = false;
    private int mThreadCount = 1;
    private List<DownLoadThread> mThreadList;
    public static ExecutorService sExecutorService = Executors.newCachedThreadPool();
    private Messenger mMessenger;

    public DownLoadTask(Context mContext, Messenger mMessenger, FileInfo mFileInfo, int mThreadCount) {
        this.mContext = mContext;
        this.mFileInfo = mFileInfo;
        this.mThreadCount = mThreadCount;
        this.mMessenger = mMessenger;
        mDao = new ThreadDAO(mContext);
    }

    public void downLoad() {
       List<ThreadInfo> threadInfos = mDao.getThreads(mFileInfo.getUrl());
       if(threadInfos.size() == 0) {
           int length = mFileInfo.getLength() / mThreadCount;
           for(int i = 0; i < mThreadCount; i++) {
               ThreadInfo threadInfo = new ThreadInfo(i, mFileInfo.getUrl(), i * length, (i + 1) * length - 1, 0);
               if(i == mThreadCount - 1) {
                   threadInfo.setEnd(mFileInfo.getLength());
               }

               threadInfos.add(threadInfo);

               mDao.insertThread(threadInfo);
           }
       }
       mThreadList = new ArrayList<>();

       for(ThreadInfo info : threadInfos) {
           DownLoadThread thread = new DownLoadThread(info);
           DownLoadTask.sExecutorService.execute(thread);
           mThreadList.add(thread);
       }
    }

    private synchronized void checkAllThreadsFinished() throws RemoteException {
        boolean allFinished = true;
        for(DownLoadThread thread : mThreadList) {
            if(!thread.isFinished) {
                allFinished = false;
                break;
            }
        }
        if(allFinished) {
            mDao.deleteThread(mFileInfo.getUrl());
            Message msg = new Message();
            msg.what = DownLoadService.MSG_FINISH;
            msg.obj = mFileInfo;
            mMessenger.send(msg);
        }

    }

    class DownLoadThread extends Thread {
        private ThreadInfo mThreadInfo;
        public boolean isFinished = false;

        public DownLoadThread(ThreadInfo mThreadInfo) {
            this.mThreadInfo = mThreadInfo;
        }

        @Override
        public void run() {

            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream input = null;
            try {
                URL url = new URL(mThreadInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                long start = mThreadInfo.getStart() + mThreadInfo.getFinished();
                conn.setRequestProperty("Range", "bytes="+start+"-"+mThreadInfo.getEnd());
                File file = new File(DownLoadService.DOWNLOAD_PATH, mFileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);

                synchronized(DownLoadTask.this) {
                    mFinished += mThreadInfo.getFinished();
                }

                if(conn.getResponseCode() == 206) {
                    input = conn.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len = -1;
                    long time = System.currentTimeMillis();
                    while ((len = input.read(buffer)) != -1) {
                        raf.write(buffer, 0 , len);
                        mFinished += len;
                        mThreadInfo.setFinished(mThreadInfo.getFinished() + len);

                        if(System.currentTimeMillis() - time > 1000) {
                            time = System.currentTimeMillis();
                            long temp = mFinished * 100 / mFileInfo.getLength();
                            Message msg = new Message();
                            msg.what = DownLoadService.MSG_UPDATE;
                            msg.arg2 = mFileInfo.getId();
                            msg.arg1 = (int) temp;
                            synchronized (DownLoadTask.this) {
                                mMessenger.send(msg);
                            }
                        }
                        if(isPause) {
                            mDao.updateThread(mThreadInfo.getUrl(), mThreadInfo.getId(), mThreadInfo.getFinished());
                            return;
                        }
                    }

                    isFinished = true;

                    checkAllThreadsFinished();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.disconnect();
                    raf.close();
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
