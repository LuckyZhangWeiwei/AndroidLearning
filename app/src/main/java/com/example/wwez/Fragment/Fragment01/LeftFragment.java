package com.example.wwez.Fragment.Fragment01;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wwez.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeftFragment extends Fragment {


    private EditText et_content;
    private Button btn_send;
    public LeftFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_left, container, false);
        et_content = view.findViewById(R.id.et_content);
        btn_send = view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = et_content.getText().toString().trim();
                // 方式一 可以调用findfragmentById() 方法
                // RightFragment rightFragment = (RightFragment)getFragmentManager().findFragmentById(R.id.rightfragment);
                // rightFragment.setTextView(str);

                // 方式2 获取rightfragment 的view 对象
                // TextView tv = getFragmentManager().findFragmentById(R.id.rightfragment).getView()
                   //     .findViewById(R.id.tv_show);
                // tv.setText(str);

                // 方式3 activity 作为介质
                TextView tv = getActivity().findViewById(R.id.tv_show);
                tv.setText(str);
            }
        });
        return view;
    }

}
