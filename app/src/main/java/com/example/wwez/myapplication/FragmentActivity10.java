package com.example.wwez.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.wwez.Fragment.Fragment01.MyDialogFragment;

/*
* dialogfragment 演示
* */
public class FragmentActivity10 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment10);
    }

    public void onClick(View v) {
        MyDialogFragment fragment = new MyDialogFragment();
        fragment.show(getFragmentManager(),"");
    }
}
