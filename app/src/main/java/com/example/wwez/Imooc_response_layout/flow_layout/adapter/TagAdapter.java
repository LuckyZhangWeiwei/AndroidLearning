package com.example.wwez.Imooc_response_layout.flow_layout.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class TagAdapter {
    public abstract int getItemCount();

    public abstract View createView(LayoutInflater inflater, ViewGroup parentContainer, int position);

    public abstract void bindView(View view, int position);

    public void onItemViewClick(View view, int position) {

    };

    public void tipForSelectedMax(View v, int mMaxSelectCount) {

    }

    public void onItemSelected(View view, int position) {}

    public void onItemUnSelected(View view, int position) {}

    public void notifyDataSetChanged(){
        // TagFlowLayout.onDataChanged
        if(mListener != null) {
            mListener.onDataChanged();
        }
    }

    private onDataSetChangedListener mListener;

    public void setOnDataSetChangedListener(onDataSetChangedListener listener) {
        mListener = listener;
    }

    public interface onDataSetChangedListener {
        void onDataChanged();
    }
}
