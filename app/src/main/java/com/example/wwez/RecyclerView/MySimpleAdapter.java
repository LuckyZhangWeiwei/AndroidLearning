package com.example.wwez.RecyclerView;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.wwez.myapplication.R;

import java.util.List;

public class MySimpleAdapter extends RecyclerView.Adapter<MyViewHolder>{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<String> mDatas;
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    private OnItemClickListener mOnItemClickListener;
    public void setmOnItemClickListener(OnItemClickListener listener)
    {
         mOnItemClickListener = listener;
    }

    public MySimpleAdapter(Context context, List<String> datas){
        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }
    public int getScreenWidth() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_single_textview, parent,false);
        itemView.getLayoutParams().width = (int) (getScreenWidth() / 3);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
//        View view = mInflater.inflate(R.layout.item_single_textview, parent,false);
//        MyViewHolder viewHolder = new MyViewHolder(view);
//        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv.setText(mDatas.get(position % mDatas.size()));
        setUpItemEvent(holder);
    }

    protected void setUpItemEvent(final MyViewHolder holder) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null) {
                    final int layoutPosition = holder.getLayoutPosition();
                    //如果用position ， 由于只是局部刷新adapter的数据， 在增加 或 删除数据后， position不准
                    mOnItemClickListener.onItemClick(holder.itemView, layoutPosition);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOnItemClickListener != null) {
                    final int layoutPosition = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, layoutPosition);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
//        return mDatas.size();
        return Integer.MAX_VALUE;
    }

    public void addData(int position) {
        mDatas.add(position, "Insert One");
        notifyItemInserted(position);
    }

    public void deleteData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    TextView tv;
    public MyViewHolder(View arg0) {
        super(arg0);
        tv = arg0.findViewById(R.id.id_tv);
    }
}