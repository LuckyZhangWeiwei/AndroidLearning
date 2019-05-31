package com.example.wwez.Imooc_download.com.download.services;

import android.content.Context;
import android.content.Intent;

import com.example.wwez.Imooc_download.com.download.db.ThreadDAO;
import com.example.wwez.Imooc_download.com.download.entry.FileInfo;
import com.example.wwez.Imooc_download.com.download.entry.ThreadInfo;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DownLoadTask {
    private Context mContext;
    private FileInfo mFileInfo;
    private ThreadDAO mDao;
    private int mFinished = 0;
    public boolean isPause = false;

    public DownLoadTask(Context mContext, FileInfo mFileInfo) {
        this.mContext = mContext;
        this.mFileInfo = mFileInfo;
        mDao = new ThreadDAO(mContext);
    }

    public void downLoad() {
       List<ThreadInfo> threadInfos = mDao.getThreads(mFileInfo.getUrl());
       ThreadInfo threadInfo;
       if(threadInfos.size() == 0) {
           threadInfo = new ThreadInfo(0, mFileInfo.getUrl(), 0, mFileInfo.getLength(), 0);
       } else {
           threadInfo = threadInfos.get(0);
       }
       new DownLoadThread(threadInfo).start();
    }

    class DownLoadThread extends Thread {
        private ThreadInfo mThreadInfo;

        public DownLoadThread(ThreadInfo mThreadInfo) {
            this.mThreadInfo = mThreadInfo;
        }

        @Override
        public void run() {
            if(!mDao.isExists(mThreadInfo.getUrl(), mThreadInfo.getId())) {
                mDao.insertThread(mThreadInfo);
            }
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream input = null;
            try {
                URL url = new URL(mThreadInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                int start = mThreadInfo.getStart() + mThreadInfo.getFinished();
                conn.setRequestProperty("Range", "bytes="+start+"-"+mThreadInfo.getEnd());
                File file = new File(DownLoadService.DOWNLOAD_PATH, mFileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);

                Intent intent = new Intent(DownLoadService.ACTION_UPDATE);

                mFinished += mThreadInfo.getFinished();

                if(conn.getResponseCode() == 206) {
                    input = conn.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len = -1;
                    long time = System.currentTimeMillis();
                    while ((len = input.read(buffer)) != -1) {
                        raf.write(buffer, 0 , len);
                        mFinished += len;
                        if(System.currentTimeMillis() - time > 200) {
                            time = System.currentTimeMillis();
                            int temp = mFinished * 100 / mFileInfo.getLength();
                            intent.putExtra("finished", temp);
                            mContext.sendBroadcast(intent);
                        }
                        if(isPause) {
                            mDao.updateThread(mThreadInfo.getUrl(), mThreadInfo.getId(), mFinished);
                            return;
                        }
                    }

                    mDao.deleteThread(mThreadInfo.getUrl(), mThreadInfo.getId());
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
