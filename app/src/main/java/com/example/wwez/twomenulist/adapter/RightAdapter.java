package com.example.wwez.twomenulist.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.wwez.myapplication.R;
import com.example.wwez.twomenulist.entity.Cart;
import com.example.wwez.twomenulist.entity.Product;
import com.example.wwez.twomenulist.entity.ViewBus;
import com.example.wwez.twomenulist.utils.UIHelper;

import java.util.List;

public class RightAdapter extends BaseAdapter implements SectionIndexer{
    private List<Product> list;
    private Context mContext;

    private ImageView shopCart;
    private Activity FragmentActivity;
    private int count;
    private  double priceSum;
    private int buyNum;
    private TextView buyNumView;//购物车上的数量标签
    private TextView mPriceSumView;
    private TextView selectedView;
    private View cartFrame;
    private PopupWindow popupWindow;
    private ViewBus viewBus;

    public RightAdapter(Context mContext, List<Product> list, ViewBus mViewBus) {
        this.mContext = mContext;
        this.list = list;
        viewBus = mViewBus;
        initView();
    }
    private void initView() {
        cartFrame = viewBus.getCartFrame();
        buyNumView =viewBus.getBuyNumView();//购物车上的数量标签
        mPriceSumView =viewBus.getmPriceSumView();
        selectedView =viewBus.getSelectedView();
        shopCart =viewBus.getmShopCart();
        FragmentActivity = viewBus.getmFragmentActivity();
        popupWindow = viewBus.getPopupWindow();
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

        int salecount = UIHelper.saleCount(FragmentActivity, mProduct.getProductId(), 0);
        if (salecount > 0) {
            viewProductHolder.countView.setText(salecount + "");
            viewProductHolder.countView.setVisibility(View.VISIBLE);
            viewProductHolder.removeProductImage.setVisibility(View.VISIBLE);
        } else {
            viewProductHolder.countView.setText(0 + "");
            viewProductHolder.countView.setVisibility(View.GONE);
            viewProductHolder.removeProductImage.setVisibility(View.GONE);
        }


        //此处可以设置商品的图片========请在使用的过程中自行替换
//        String url = mProduct.getImageUrl();
        viewProductHolder.productImage.setImageResource(R.mipmap.default_icon);

        viewProductHolder.removeProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.parseInt(viewProductHolder.countView.getText().toString());
                count--;
                priceSum = priceSum - mProduct.getFoodPrice();
                if(count < 1) {
                    viewProductHolder.removeProductImage.setVisibility(View.GONE);
                    viewProductHolder.countView.setVisibility(View.GONE);
                }
                    viewProductHolder.countView.setText(count + "");
                    mProduct.setCount(count);
                    if (count == 0) {
                        deleteProductId(mProduct.getProductId());
                    } else {
                        updateCart(mProduct);
                    }
                    removeCart();
            }
        });
        viewProductHolder.addProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.parseInt(viewProductHolder.countView.getText().toString());
                count++;
                priceSum = priceSum + mProduct.getFoodPrice();
                viewProductHolder.countView.setVisibility(View.VISIBLE);
                viewProductHolder.removeProductImage.setVisibility(View.VISIBLE);
                viewProductHolder.countView.setText(count + "");
                mProduct.setCount(count);

                int[] startLoaction = new int[2];
                v.getLocationInWindow(startLoaction);
                ImageView ball = new ImageView(mContext);
                ball.setImageResource(R.mipmap.icon_fansnum_like_on);
                updateCart(mProduct);
                setAnim(ball, startLoaction);
            }
        });
        return convertView;
    }

    private void updateCart(Product mProduct) {
        int productId = mProduct.getProductId();
        String name = mProduct.getFoodName();
        double price = mProduct.getFoodPrice();
        Cart cart = new Cart();
        cart.setName(name);
        cart.setProductId(productId);
        cart.setSaleCount(count);
        cart.setPrice(price);
        UIHelper.getQueryIdCart(FragmentActivity, cart, productId,0);
    }

    private void deleteProductId(int productId) {
        UIHelper.deleteCart(FragmentActivity, productId);
    }

    private void addCart() {
        buyNum++;//让购买数量+1
        showSeleted();
    }

    public void removeCart() {
        buyNum--;//让购买数量-1
        showSeleted();
    }

    static double convert(double value) {
        long l1 = Math.round(value * 100); //四舍五入
        double ret = l1 / 100.0; //注意：使用 100.0 而不是 100
        return ret;
    }

    private void showSeleted() {
        if (buyNum > 0) {
            buyNumView.setVisibility(View.VISIBLE);
            buyNumView.setText(buyNum + "");
            mPriceSumView.setText("共￥:" + convert(priceSum));
            mPriceSumView.setTextColor(Color.parseColor("#FF4081"));
            if (priceSum > 12) {
                selectedView.setEnabled(true);
                selectedView.setText("选好了");
                selectedView.setBackgroundResource(R.drawable.bg_choice_press_round);
            } else {
                selectedView.setEnabled(false);
                selectedView.setText("￥15起送");
                selectedView.setBackgroundResource(R.drawable.bg_choice_round);
            }
            shopCart.setImageResource(R.mipmap.cart13);
            cartFrame.setEnabled(true);
        } else {
            mPriceSumView.setText("请点餐~");
            mPriceSumView.setTextColor(Color.parseColor("#b4b4b4"));
            selectedView.setEnabled(false);
            selectedView.setText("￥15起送");
            selectedView.setBackgroundResource(R.drawable.bg_choice_round);
            shopCart.setImageResource(R.mipmap.cart12);
            buyNumView.setVisibility(View.GONE);
            if (popupWindow != null) {
                popupWindow.dismiss();
            }
            cartFrame.setEnabled(false);
        }
    }

    private void setAnim(final ImageView ball, int[] startLocation) {
        ViewGroup anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(ball);
        View view = addViewToAnimLayout(ball, startLocation);
        int[] endLocation = new int[2];
        shopCart.getLocationInWindow(endLocation);
        // 计算位移
        int endX = 0 - startLocation[0]+65;// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标
        TranslateAnimation translateAnimationX = new TranslateAnimation(0,
                endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator()); //让动画已均匀的速度改变
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true); //执行完毕，利用视图setLayoutParams来重新定位

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
                0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(800);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                ball.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                ball.setVisibility(View.GONE);
                addCart();
            }}
            );
    }

    private ViewGroup createAnimLayout() {
        ViewGroup rootView =(ViewGroup) FragmentActivity.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(FragmentActivity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE - 1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(final View view, int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
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
