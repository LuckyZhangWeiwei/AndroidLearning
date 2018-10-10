package com.example.wwez.Fragment.Fragment01;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.wwez.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResourceFragment extends Fragment {


    private EditText et_content;
    private Button btn_pass;
    private MyListener listener;
    public ResourceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = (MyListener)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resource, container, false);
        et_content = view.findViewById(R.id.et_Content);
        btn_pass = view.findViewById(R.id.btn_Pass);
        btn_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = et_content.getText().toString().trim();
                listener.sendMessage(info);
            }
        });
        return view;
    }

    public interface MyListener {
        public abstract void sendMessage(String str);
    }

}
