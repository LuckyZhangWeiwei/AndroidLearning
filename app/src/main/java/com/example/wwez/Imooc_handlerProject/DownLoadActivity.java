package com.example.wwez.Imooc_handlerProject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.wwez.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownLoadActivity extends AppCompatActivity {

    Handler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

        final ProgressBar progressBar = findViewById(R.id.progressBar);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        downLoad("http://download.sj.qq.com/upload/connAssitantDownload/upload/MobileAssistant_1.apk");
                    }
                }).start();
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 100001:
                        progressBar.setProgress((int)msg.obj);
                        break;
                    case 100002:
                        break;
                }
            }
        };
    }

    private void downLoad(String appUrl) {
        try {
            URL url = new URL(appUrl);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            int contentLength = urlConnection.getContentLength();
            String downloadFolderName = Environment.getExternalStorageDirectory().toString();
                  //  + File.separator
            File file = new File(downloadFolderName);
            if(!file.exists()) {
                file.mkdirs();
            }
            String fileName = downloadFolderName + "imooc.apk";
            File apkFile = new File(fileName);
            if(apkFile.exists()) {
                apkFile.delete();
            }
            int downLoadSize = 0;
            byte[] bytes = new byte[1024];
            int length = 0;
            OutputStream outputStream = new FileOutputStream(fileName);
            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
                downLoadSize += length;
                /*
                * update UI
                * */
                Message message = Message.obtain();
                message.obj = downLoadSize * 100 / contentLength;
                message.what = 100001;
                handler.sendMessage(message);
            }
            inputStream.close();
            outputStream.close();
        } catch (MalformedURLException e) {
            Message message = Message.obtain();
            message.what = 100002;
            handler.sendMessage(message);
            e.printStackTrace();
        } catch (IOException e) {
            Message message = Message.obtain();
            message.what = 100002;
            handler.sendMessage(message);
            e.printStackTrace();
        }
    }
}
