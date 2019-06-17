package com.example.wwez.geekband.test01;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wwez.geekband.test01.model.UserInfo;
import com.example.wwez.myapplication.MainActivity;
import com.example.wwez.myapplication.R;

import java.io.Serializable;

public class SplashActivity extends AppCompatActivity {

    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, Geekband_Test01_MainActivity.class);
                intent.putExtra("TITLE", ((TextView)findViewById(R.id.tv_splash)).getText().toString());
                UserInfo userInfo = new UserInfo();
                intent.putExtra("USERINFO", userInfo);
                startActivityForResult(intent, 100);
            }
        }, 3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("onActivityResult", "requestCode:"+ requestCode + " resultCode:" + resultCode);
        if(requestCode == 100 && resultCode == 501) {
            if(data == null) return;
            int result = data.getIntExtra("MAINACTIVITY", -1);
            Toast.makeText(this, String.valueOf(result), Toast.LENGTH_SHORT).show();
        }
    }
}
