package com.example.wwez.twomenulist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.wwez.myapplication.R;
import com.example.wwez.twomenulist.entity.Product;

import java.util.List;

public class RightAdapter extends BaseAdapter implements SectionIndexer {
    private List<Product> list;
    private Context mContext;

    public RightAdapter(Context mContext, List<Product> list) {
        this.mContext = mContext;
        this.list = list;
    }
    @Override
    public int getCount() {
        return this.list.size();
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
        final ViewProductHolder viewProductHolder;
        final Product mProduct = list.get(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.product_item, null);
            TextView tvTag = convertView.findViewById(R.id.cate_name);
            TextView tvSalecount = convertView.findViewById(R.id.product_salecount_view);
            TextView tvTitle = convertView.findViewById(R.id.product_name_view);
            ImageView productImage = convertView.findViewById(R.id.product_imageView);
            TextView countView = convertView.findViewById(R.id.add_count_view);
            TextView tvPrice = convertView.findViewById(R.id.product_price_view);
            ImageView removeProductImage = convertView.findViewById(R.id.delete_product_view);
            ImageView addProductImage = convertView.findViewById(R.id.add_product_view);
            viewProductHolder = new ViewProductHolder(tvTag, tvSalecount, tvTitle, productImage, countView, tvPrice, removeProductImage, addProductImage);

            convertView.setTag(viewProductHolder);
        } else {
            viewProductHolder = (ViewProductHolder) convertView.getTag();
        }
        //如果有该类型，则隐藏
        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            viewProductHolder.tvTag.setVisibility(View.VISIBLE);
            viewProductHolder.tvTag.setText(mProduct.getType());
        } else {
            viewProductHolder.tvTag.setVisibility(View.GONE);
        }
        viewProductHolder.tvTitle.setText(mProduct.getFoodName());
        viewProductHolder.tvPrice.setText("￥" + mProduct.getFoodPrice());
        viewProductHolder.tvSalecount.setText("已售" + mProduct.getSalesCount() + "份");

//        int salecount = UIHelper.saleCount(getActivity(), mProduct.getProductId(), businessId);
//        if (salecount > 0) {
//            viewProductHolder.countView.setText(salecount + "");
//            viewProductHolder.countView.setVisibility(View.VISIBLE);
//            viewProductHolder.removeProductImage.setVisibility(View.VISIBLE);
//        } else {
//            viewProductHolder.countView.setText(0 + "");
//            viewProductHolder.countView.setVisibility(View.GONE);
//            viewProductHolder.removeProductImage.setVisibility(View.GONE);
//        }


        //此处可以设置商品的图片========请在使用的过程中自行替换
//        String url = mProduct.getImageUrl();
        viewProductHolder.productImage.setImageResource(R.mipmap.default_icon);

        viewProductHolder.removeProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewProductHolder.addProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    private class ViewProductHolder {
        TextView tvTag, tvSalecount, tvTitle;//,tvPrice,countView
        ImageView productImage;//,removeProductImage,addProductImage
        private final TextView countView;
        private final TextView tvPrice;
        private final ImageView removeProductImage;
        private final ImageView addProductImage;

        private ViewProductHolder(
                TextView tvTag,
                TextView tvSalecount,
                TextView tvTitle,
                ImageView productImage,
                TextView countView,
                TextView tvPrice,
                ImageView removeProductImage,
                ImageView addProductImage) {
            this.tvTag = tvTag;
            this.tvSalecount = tvSalecount;
            this.tvTitle = tvTitle;
            this.productImage = productImage;
            this.countView = countView;
            this.tvPrice = tvPrice;
            this.removeProductImage = removeProductImage;
            this.addProductImage = addProductImage;
        }
    }
    /**
    * 根据分类列的索引号获得该序列的首个位置
    **/
    @Override
    public int getPositionForSection(int sectionIndex) {
        for(int i=0; i < getCount(); i++) {
            int section = list.get(i).getSeleteId();
            if(section == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getSeleteId();
    }
}
