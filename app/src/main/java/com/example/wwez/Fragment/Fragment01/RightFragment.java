package com.example.wwez.Fragment.Fragment01;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wwez.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RightFragment extends Fragment {


    private TextView tv_show;
    public RightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_right, container, false);
        tv_show = view.findViewById(R.id.tv_show);
        return view;
    }

    public void setTextView(String text) {
        if(text != null && !"".equals(text)) {
            tv_show.setText(text);
        }
    }
}
