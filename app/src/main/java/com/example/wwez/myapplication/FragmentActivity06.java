package com.example.wwez.myapplication;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.wwez.Fragment.Fragment01.ResultFragment;

/*
* 演示activity向fragment 传值
* */
public class FragmentActivity06 extends AppCompatActivity {

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment06);
        et= findViewById(R.id.et_content06);

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.content_layout06, new ResultFragment());
        transaction.commit();
    }
    public void sendValue(View view) {
        String info = et.getText().toString().trim();
        ResultFragment rf = new ResultFragment();
        Bundle bundle =new Bundle();
        bundle.putString("info", info);
        rf.setArguments(bundle);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.content_layout06, rf);
        transaction.commit();
    }
}

