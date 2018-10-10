package com.example.wwez.myapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.wwez.Fragment.Fragment01.ContentFragment;
import com.example.wwez.Fragment.Fragment01.TitleFragment;


public class FragmentActivity02 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fragment02);

        FragmentManager manager =getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.title_layout, new TitleFragment());
        transaction.add(R.id.content_layout, new ContentFragment());
        transaction.commit();
    }
}
