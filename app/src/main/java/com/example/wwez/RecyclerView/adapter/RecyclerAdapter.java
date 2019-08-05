package com.example.wwez.RecyclerView.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wwez.myapplication.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 2;

    private static final int TYPE_ITEM = 1;

    private List<String> mItemList;

    boolean mHasHeader;

    public RecyclerAdapter(List<String> itemList, boolean hasHeader) {
        mItemList = itemList;
        mHasHeader = hasHeader;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mHasHeader) {
            if(viewType == TYPE_ITEM) {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_scroll_show_hidden, parent, false);
                return RecyclerItemViewHolder.newInstance(view);
            } else if(viewType == TYPE_HEADER) {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_header_scroll_show_hidden, parent, false);
                return new RecyclerHeaderViewHolder(view);
            }
        } else {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_scroll_show_hidden, parent, false);
            return RecyclerItemViewHolder.newInstance(view);
        }

        throw new RuntimeException("There is no type that matches the type " + viewType + " + make sure your using types    correctly");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if(mHasHeader){
            if(!isPositionHeader(position)) {
                RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;
                String itemText = mItemList.get(position - 1);
                holder.setItemText(itemText);
            }
        } else {
            RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;
            String itemText = mItemList.get(position);
            holder.setItemText(itemText);
        }


    }

    @Override
    public int getItemViewType(int position) {
        if(mHasHeader) {
            if(isPositionHeader(position)) {
                return TYPE_HEADER;
            }
            return TYPE_ITEM;
        } else {
            return TYPE_ITEM;
        }
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    public int getBasicItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    @Override
    public int getItemCount() {
        if(mHasHeader) {
            return getBasicItemCount() + 1;
        } else {
            return getBasicItemCount();
        }

    }
}
