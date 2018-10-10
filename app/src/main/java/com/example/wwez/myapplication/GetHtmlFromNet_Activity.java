package com.example.wwez.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class GetHtmlFromNet_Activity extends AppCompatActivity {

    private EditText et_address;
    private EditText et_Content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_html_from_net_);
        et_address = findViewById(R.id.et_url);
        et_Content = findViewById(R.id.et_Content);
    }
    public void onClick(View view){
        String urlStr = et_address.getText().toString();
        new AsyncTask<String,Void,String>(){
            @Override
            protected String doInBackground(String... params) {
                StringBuffer sb = new StringBuffer();
                try {
                    URL url = new URL("https://"+params[0]+"/");
                    try {
                        HttpURLConnection conn =(HttpURLConnection)url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(5000);
                        conn.setReadTimeout(5000);
                        try{
                        conn.connect();
                        } catch (SocketTimeoutException e){
                            return e.getMessage();
                        }
                        int code = conn.getResponseCode();
                        Log.d("aa:", code+"");
                        if(code == 200) {
                            String CharSetName = conn.getHeaderField("Content-Type").split("=")[1];
                            try{
                            InputStreamReader reader = new InputStreamReader(conn.getInputStream(),CharSetName);
                            char[] charArr = new char[1024 * 8];
                            int len = 0;
                            while ( (len = reader.read(charArr)) != -1){
                                Log.d("aa:", charArr.toString());
                                String str = new String(charArr,0,len);
                                sb.append(str);
                            }
                            } catch (SocketTimeoutException e) {
                                return e.getMessage();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return sb.toString();
            }

            @Override
            protected void onPostExecute(String s) {
                et_Content.setText(s);
            }
        }.execute(urlStr);
    }
}
