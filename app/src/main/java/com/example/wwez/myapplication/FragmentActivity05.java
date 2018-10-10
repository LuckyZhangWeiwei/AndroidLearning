package com.example.wwez.myapplication;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.wwez.FragmentTabs.HotSpotFragment;
import com.example.wwez.FragmentTabs.TopLineFragment;
/*
* Fragment生命周期
* */
public class FragmentActivity05 extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_hotspot, tv_topline;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment05);
        Log.i("fragment","----FragmentActivity05-------onCreate");
        tv_hotspot = findViewById(R.id.hotSpot);
        tv_hotspot.setOnClickListener(this);
        tv_topline = findViewById(R.id.topLine);
        tv_topline.setOnClickListener(this);

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.contentLayout05, new HotSpotFragment());
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        transaction = manager.beginTransaction();
        switch (v.getId()){
            case R.id.topLine:
                transaction.replace(R.id.contentLayout05, new TopLineFragment());
                break;
            case R.id.hotSpot:
                transaction.replace(R.id.contentLayout05, new HotSpotFragment());
                break;
        }
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("fragment","----FragmentActivity05-------onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("fragment","----FragmentActivity05-------onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("fragment","----FragmentActivity05-------onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("fragment","----FragmentActivity05-------onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("fragment","----FragmentActivity05-------onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("fragment","----FragmentActivity05-------onDestroy");
    }
}
