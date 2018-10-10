package com.example.wwez.Fragment.Fragment01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.wwez.myapplication.R;

public class TitleFragment extends android.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_title, null, false);
        RelativeLayout layout = view.findViewById(R.id.rl_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "我是标题", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
