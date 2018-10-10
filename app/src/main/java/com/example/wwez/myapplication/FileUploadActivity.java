package com.example.wwez.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUploadActivity extends AppCompatActivity {

    public static final int EXTERNAL_STORAGE_REQ_CODE = 10 ;
    private String boundary = "****";
    private String end = "\r\n";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);

        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // 请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    EXTERNAL_STORAGE_REQ_CODE);
        }

    }
    public void onClick(View view) {
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    uploadFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
    private void uploadFile() throws IOException{
            URL url = new URL("https://www.file.io/");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "mutipart/form-data;boundary="+boundary);
            OutputStream output = conn.getOutputStream();
            StringBuilder strBuffer = new StringBuilder();
            strBuffer.append("--"+boundary+end);
            strBuffer.append("Content-Disposition: form-data; name=\"file1\";filename=\"bihu.jpg\""+end);
            strBuffer.append("Content-Type: image/jpeg"+end+end);
            output.write(strBuffer.toString().getBytes());
            writeFile(output);
            output.write(end.getBytes());
            output.write(("--"+boundary).getBytes());
            output.close();
            int code = conn.getResponseCode();
            Log.d("code:", code+"");
            if(code == 200) {
                Log.d("code:", "file uploaded");
            }
    }
    private void writeFile(OutputStream output) throws IOException {
        FileInputStream input = new FileInputStream("/sdcard/bihu.jpg");
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len=input.read(buffer))!=-1){
            output.write(buffer,0,len);
            output.flush();
        }
    }
}
