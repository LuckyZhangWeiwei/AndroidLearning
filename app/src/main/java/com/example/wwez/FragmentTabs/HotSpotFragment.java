package com.example.wwez.FragmentTabs;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wwez.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotSpotFragment extends Fragment {


    public HotSpotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("fragment","----HotSpotFragment-------onCreateView");
        return inflater.inflate(R.layout.fragment_hot_spot, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("fragment","----HotSpotFragment-------onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("fragment","----HotSpotFragment-------onCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("fragment","----HotSpotFragment-------onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("fragment","----HotSpotFragment-------onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("fragment","----HotSpotFragment-------onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("fragment","----HotSpotFragment-------onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("fragment","----HotSpotFragment-------onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("fragment","----HotSpotFragment-------onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("fragment","----HotSpotFragment-------onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("fragment","----HotSpotFragment-------onDetach");
    }
}
