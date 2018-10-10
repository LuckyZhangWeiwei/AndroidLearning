package com.example.wwez.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class HttpPost_Activity extends AppCompatActivity {

    private EditText et_token;
    private EditText et_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_post_);

        et_token = findViewById(R.id.post_et_token);
        et_content = findViewById(R.id.post_et_content);
    }
    public void onClick(View view){
        new AsyncTask<String,Void,String>(){
            @Override
            protected String doInBackground(String... params) {
                StringBuffer sb = new StringBuffer();
                    try {
                        URL url = new URL("https://cnodejs.org/api/v1/accesstoken");
//                        String para = new String("accesstoken=f8aa237b-5d1f-4a31-ac54-658dbf25ef09");
                        DTO dto = new DTO();
                        dto.accesstoken = "f8aa237b-5d1f-4a31-ac54-658dbf25ef09";
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(dto);

                        HttpURLConnection conn =(HttpURLConnection)url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setConnectTimeout(5000);
                        conn.setReadTimeout(5000);
                        conn.setDoOutput(true);
                        conn.setDoInput(true);
                        conn.setRequestProperty("Content-Type","application/json; charset=utf-8");
                        conn.getOutputStream().write(jsonString.getBytes());
//                        DataOutputStream output = new DataOutputStream(conn.getOutputStream());
//                        output.writeBytes("accessToken="+et_token.getText());
//                        output.flush();
//                        output.close();
                        try{
                            conn.connect();
                        } catch (SocketTimeoutException e){
                            Log.d("err:",e.getMessage());
                            return e.getMessage();
                        }
                        int code = conn.getResponseCode();
                        Log.d("code:",code+"");
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

                return sb.toString();
            }

            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(HttpPost_Activity.this,s,Toast.LENGTH_LONG).show();
                et_content.setText(s);
            }
        }.execute();
    }

    class DTO {
        public String getAccesstoken() {
            return accesstoken;
        }

        public void setAccesstoken(String accesstoken) {
            this.accesstoken = accesstoken;
        }

        String accesstoken;
    }
}
