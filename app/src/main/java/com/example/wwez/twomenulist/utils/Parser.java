package com.example.wwez.twomenulist.utils;

import android.content.Context;

import com.example.wwez.myapplication.R;
import com.example.wwez.twomenulist.entity.DataResult;
import com.example.wwez.twomenulist.entity.Product;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static List<Product> getCateProductList(Context ctx) {
        InputStream is = ctx.getResources().openRawResource(R.raw.food_json);
        try {
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            String json = new String(buffer,"utf-8");
            //Gson解析
            DataResult dataResult = new Gson().fromJson(json, DataResult.class);
            List<Product> products=new ArrayList<Product>();
            for (int i=0;i<dataResult.getResults().size();i++){
                for (int j=0;j<dataResult.getResults().get(i).getFoodList().size();j++){
                    DataResult.ResultsBean.FoodListBean food=dataResult.getResults().get(i).getFoodList().get(j);
                    Product item=new Product();
                    item.setProductId(food.getID());
                    item.setType(dataResult.getResults().get(i).getTitle());
                    item.setFoodName(food.getFoodName());
                    item.setFoodPrice(food.getFoodPrice());
                    item.setSalesCount(food.getSalesCount());
                    item.setImageUrl(food.getImageUrl());
                    item.setSeleteId(i);
                    products.add(item);
                }
            }
            return products;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
