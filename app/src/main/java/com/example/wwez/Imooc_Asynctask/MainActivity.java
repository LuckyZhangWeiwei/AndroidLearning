package com.example.wwez.Imooc_Asynctask;

import android.os.AsyncTask;
import android.os.Environment;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.wwez.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    public static final String APK_URL = "http://download.sj.qq.com/upload/connAssitantDownload/upload/MobileAssistant_1.apk";
    ProgressBar mProgressBar;
    Button mButton;
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        initView();

        setListener();

        setData();

    }

    private void setData() {
        mProgressBar.setProgress(0);
        mButton.setText("点击下载");
        mTextView.setText("准备下载");
    }

    private void setListener() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask();
//                downloadAsyncTask.execute(APK_URL);
                DownloadHelper.download(APK_URL, Environment.getExternalStorageDirectory().toString() + File.separator + "imooc.apk", new DownloadHelper.OnDownloadListener.SimpleDownloadListener() {
                    @Override
                    public void onSuccess(int code, File file) {
                        mButton.setText("下载完成");
                        mTextView.setText("下载完成");
                    }

                    @Override
                    public void onFail(int code, File file, String message) {
                        mButton.setText("下载失败");
                        mTextView.setText("下载失败,"+message);
                    }

                    @Override
                    public void onProgress(int progress) {
                        super.onProgress(progress);
                        mProgressBar.setProgress(progress);
                        mTextView.setText(progress+"%");
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        mButton.setText("下载中");
                        mTextView.setText("下载中");
                        mProgressBar.setProgress(0);
                    }
                });
            }
        });
    }

    private void initView() {
        mProgressBar = findViewById(R.id.progressBar);
        mButton = findViewById(R.id.button);
        mTextView = findViewById(R.id.textView);
    }

    /*
    * String 入参
    * Integer 进度
    * Boolean 返回结果
    * */
    public class DownloadAsyncTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*主线程中， 操作UI*/
            mButton.setText("下载中");
            mTextView.setText("下载中");
            mProgressBar.setProgress(0);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            /*
             * 子线程
             * */
            if(strings !=null && strings.length>0) {
                try {
                    URL url = new URL(strings[0]);
                    URLConnection urlConnection = url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    int contentLength = urlConnection.getContentLength();
                    String mFilePath = Environment.getExternalStorageDirectory().toString() + File.separator + "imooc.apk";
                    File file = new File(mFilePath);
                    if(file.exists()) {
                        boolean result = file.delete();
                        if(!result) {
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
                    return false;
                }
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            /*
            * 主线程 执行结果 处理
            * */
            mButton.setText("下载完成");
            mTextView.setText(aBoolean ? "下载完成":"下载失败");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            /*
            *UI线程
            * */
            if(values !=null && values.length>0){
                mProgressBar.setProgress(values[0]);
                mTextView.setText(values[0] + "%");
            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }

}
