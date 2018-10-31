package com.example.wwez.webview;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class HttpThread extends Thread{
    private String mUrl;
    public HttpThread(String url){
        this.mUrl = url;
    }
    @Override
    public void run() {
        try {
            URL httpUrl = new URL(mUrl);
            URLConnection conn =  httpUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            InputStream in =conn.getInputStream();
            FileOutputStream out = null;
            File downloadFile;
            File sdFile;
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                downloadFile = Environment.getExternalStorageDirectory();
                sdFile = new File(downloadFile, "test.apk");
                out = new FileOutputStream(sdFile);
            }
            byte[] b = new byte[6*1024];
            int len;
            while((len = in.read(b)) != -1){
               if(out != null) {
                    out.write(b,0,len);
               }
            }
            if(out != null) {
                out.close();
            }
            if(in != null){
                in.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
