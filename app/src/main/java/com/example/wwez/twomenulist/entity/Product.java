package com.example.wwez.twomenulist.entity;

import java.io.Serializable;
/**
 * ID : 101
 * foodName : 巧克力
 * foodPrice : 22
 * salesCount : 101
 * imageUrl : 1
 */
public class Product implements Serializable {
    private int ID;
    private int productId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    private String type;
    private String foodName;
    private double foodPrice;
    private int salesCount;
    private String imageUrl;
    private int seleteId;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSeleteId() {
        return seleteId;
    }

    public void setSeleteId(int seleteId) {
        this.seleteId = seleteId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(int salesCount) {
        this.salesCount = salesCount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
