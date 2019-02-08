package com.example.wwez.Imooc_Listview;

import android.graphics.Bitmap;

public class LessonInfo {
    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    String mName;

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    String mDescription;

    public Bitmap getmIconUrl() {
        return mIconUrl;
    }

    public void setmIconUrl(Bitmap mIconUrl) {
        this.mIconUrl = mIconUrl;
    }

    Bitmap mIconUrl;
}