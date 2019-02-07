package com.example.wwez.Imooc_Asynctask;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadHelper {
    public static void download(String url, String localPath, OnDownloadListener listener) {
        DownloadAsyncTask task = new DownloadAsyncTask(url, localPath, listener);
        task.execute();
    }

    public static class DownloadAsyncTask extends AsyncTask<String, Integer, Boolean> {

        String mUrl;
        String mFilePath;
        OnDownloadListener mListener;

        public DownloadAsyncTask(String mUrl, String mFilePath, OnDownloadListener mListener) {
            this.mUrl = mUrl;
            this.mFilePath = mFilePath;
            this.mListener = mListener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(mListener != null) {
                mListener.onStart();
            }
        }

        @Override
        protected Boolean doInBackground(String ...strings) {
            /** 子线程**/
                try {
                    URL url = new URL(mUrl);
                    URLConnection urlConnection = url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    int contentLength = urlConnection.getContentLength();
                    File file = new File(mFilePath);
                    if(file.exists()) {
                        boolean result = file.delete();
                        if(!result) {
                            if(mListener != null) {
                                mListener.onFail(-1, new File(mFilePath), "文件删除失败");
                            }
                            return false;
                        }
                    }
                    int downLoadSize = 0;
                    byte[] bytes = new byte[1024];
                    int length = 0;
                    OutputStream outputStream = new FileOutputStream(mFilePath);
                    while ((length = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, length);
                        downLoadSize += length;
                        publishProgress(downLoadSize * 100 /contentLength );
                    }
                    inputStream.close();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    if(mListener != null) {
                        mListener.onFail(-2, new File(mFilePath), e.getMessage());
                    }
                    return false;
                }
//                if(mListener != null) {
//                    mListener.onSuccess(0, new File(mFilePath));
//                }
                return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            /*
             * 主线程 执行结果 处理
             * */
            if(mListener != null) {
                if(aBoolean)
                    mListener.onSuccess(0, new File(mFilePath));
                else
                    mListener.onFail(-1, new File(mFilePath), "下载失败");
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            /*
             *UI线程
             * */
           if(mListener!=null) {
               mListener.onProgress(values[0]);
           }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }


    public interface OnDownloadListener {
        void onStart();

        void onSuccess(int code, File file);

        void onFail(int code, File file, String message);

        void onProgress(int progress);

        abstract class SimpleDownloadListener implements OnDownloadListener{
            @Override
            public void onStart() {

            }

            @Override
            public void onProgress(int progress) {

            }
        }
    }
}
