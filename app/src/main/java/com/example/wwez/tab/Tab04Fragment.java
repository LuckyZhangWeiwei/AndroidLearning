package com.example.wwez.tab;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wwez.myapplication.R;


@SuppressLint("ValidFragment")
public class Tab04Fragment extends Fragment {
    private int pos;
    @SuppressLint("ValidFragment")
    public Tab04Fragment(int mpos){
        pos = mpos;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag04, container, false);
        TextView tv = view.findViewById(R.id.id_tv);
        tv.setText(Tab04_Adapter.TITLES[pos]);
        return view;
    }
}
