package com.example.wwez.Fragment.Fragment01;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wwez.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {

   private TextView tv_show;

    public ResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        tv_show = view.findViewById(R.id.tv_show);
        Bundle bundle = getArguments();
        if(bundle !=null) {
            String info = bundle.getString("info");
            tv_show.setText(info);
        }
        return view;
    }

}
