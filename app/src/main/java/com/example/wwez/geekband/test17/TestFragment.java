package com.example.wwez.geekband.test17;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wwez.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {


    public TestFragment() {
        // Required empty public constructor
        Log.d("zww", "TestFragment - constructor");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("zww", "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("zww", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        TextView tv = view.findViewById(R.id.tv_text);
        tv.setText("a");
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("zww", "onPause");
    }

    @Override
    public void onDestroy() {
        Log.d("zww", "onDestroy");
        super.onDestroy();
    }
}
