package com.example.wwez.twomenulist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wwez.myapplication.R;
import com.example.wwez.twomenulist.entity.Cart;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    private List<Cart> list = null;
    private Context mContext;

    public CartAdapter(Context mContext, List<Cart> list) {
        this.mContext = mContext;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        final Cart mCart = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cart_item, null);
            TextView tvname = convertView.findViewById(R.id.product_name_view);
            TextView countView = convertView.findViewById(R.id.add_count_view);
            TextView totalPrice = convertView.findViewById(R.id.product_totle_price_view);
            ImageView removeProductImage = convertView.findViewById(R.id.remove_product_view);
            ImageView addProductImage = convertView.findViewById(R.id.add_product_view);
            viewHolder = new ViewHolder(tvname, countView, totalPrice, removeProductImage, addProductImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvname.setText(mCart.getName());
        viewHolder.totalPrice.setText("￥" + mCart.getPrice());
        viewHolder.countView.setText(String.valueOf(mCart.getSaleCount()));
        if (Integer.parseInt(viewHolder.countView.getText().toString()) < 1) {
            viewHolder.removeProductImage.setVisibility(View.GONE);
            viewHolder.countView.setVisibility(View.GONE);
        } else {
            viewHolder.removeProductImage.setVisibility(View.VISIBLE);
            viewHolder.countView.setVisibility(View.VISIBLE);
        }
        viewHolder.removeProductImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
        viewHolder.addProductImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }
    private class ViewHolder {
        TextView tvname;
        private final TextView countView;
        private final ImageView removeProductImage;
        private final ImageView addProductImage;
        private final TextView totalPrice;

        private ViewHolder(TextView tvname, TextView countView, TextView totalPrice, ImageView removeProductImage, ImageView addProductImage) {
            this.tvname = tvname;
            this.countView = countView;
            this.totalPrice = totalPrice;
            this.removeProductImage = removeProductImage;
            this.addProductImage = addProductImage;
        }
    }
}
