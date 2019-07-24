package com.example.wwez.Imooc_response_layout.flow_layout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.example.wwez.Imooc_response_layout.flow_layout.adapter.TagAdapter;

public class TagFlowLayout extends FlowLayout implements TagAdapter.onDataSetChangedListener {

    private TagAdapter mAdapter;

    private int mMaxSelectCount;



    public TagFlowLayout(Context context) {
        super(context);
    }

    public TagFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAdapter(TagAdapter adapter) {
        mAdapter = adapter;

        mAdapter.setOnDataSetChangedListener(this);

        onDataChanged();
    }

    @Override
    public void onDataChanged() {
        removeAllViews();

        TagAdapter adapter = mAdapter;

        for (int i = 0; i < adapter.getItemCount(); i++) {
            final View view = adapter.createView(LayoutInflater.from(getContext()), this, i);
            adapter.bindView(view, i);
            addView(view);
            if(view.isSelected()) {
                adapter.onItemSelected(view, i);
            } else {
                adapter.onItemUnSelected(view, i);
            }

            bindViewMethod(i, view);
        }
    }

    /*
    *
    * */
    private void bindViewMethod(final int position, View view) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.onItemViewClick(v, position);

                if(mMaxSelectCount <= 0) {
                    return;
                }
                // view selected 状态
                if(!v.isSelected()) {
                    if(getSelectedViewCount() >= mMaxSelectCount ) {
                        if(getSelectedViewCount() == 1) {  // 单选情况
                           View selectedView = getSelectedView();
                           if(selectedView !=null) {
                                selectedView.setSelected(false);
                                mAdapter.onItemUnSelected(selectedView, getPositionForChild(selectedView));
                           }

                        } else {
                            mAdapter.tipForSelectedMax(v, mMaxSelectCount);
                            return;
                        }
                    }
                }

                if(v.isSelected()) {  // selected
                    v.setSelected(false);
                    mAdapter.onItemUnSelected(v, position);
                } else {
                    v.setSelected(true);
                    mAdapter.onItemSelected(v, position);
                }
            }
        });
    }

    private int getPositionForChild(View selectedView) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if(view == selectedView) {
                return i;
            }
        }
        return -1;
    }

    private View getSelectedView() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if(view.isSelected()) {
                return view;
            }
        }
        return null;
    }

    private int getSelectedViewCount() {
        int result = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if(view.isSelected()) {
                result ++;
            }
        }
        return result;
    }

    public void setMaxSelectCount(int mMaxSelectCount) {
        this.mMaxSelectCount = mMaxSelectCount;
    }
}
