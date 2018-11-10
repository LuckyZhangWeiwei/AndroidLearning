package com.example.wwez.twomenulist.entity;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ViewBus {
    public TextView getBuyNumView() {
        return buyNumView;
    }

    public void setBuyNumView(TextView buyNumView) {
        this.buyNumView = buyNumView;
    }

    public TextView getmPriceSumView() {
        return mPriceSumView;
    }

    public void setmPriceSumView(TextView mPriceSumView) {
        this.mPriceSumView = mPriceSumView;
    }

    public TextView getSelectedView() {
        return selectedView;
    }

    public void setSelectedView(TextView selectedView) {
        this.selectedView = selectedView;
    }

    public View getCartFrame() {
        return cartFrame;
    }

    public void setCartFrame(View cartFrame) {
        this.cartFrame = cartFrame;
    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    public void setPopupWindow(PopupWindow popupWindow) {
        this.popupWindow = popupWindow;
    }

    public ImageView getmShopCart() {
        return mShopCart;
    }

    public void setmShopCart(ImageView mShopCart) {
        this.mShopCart = mShopCart;
    }

    public Activity getmFragmentActivity() {
        return mFragmentActivity;
    }

    public void setmFragmentActivity(Activity mFragmentActivity) {
        this.mFragmentActivity = mFragmentActivity;
    }

    private TextView buyNumView;//购物车上的数量标签
    private TextView mPriceSumView;
    private TextView selectedView;
    private View cartFrame;
    private PopupWindow popupWindow;
    private ImageView mShopCart;
    private Activity mFragmentActivity;

}
