package com.example.wwez.geekband.test01.model;

import java.io.Serializable;

public class UserInfo implements Serializable {
    public UserInfo() {
        this.mUserName = "AAAAusername";
        this.mAge = 16;
    }

    private String mUserName;
    private int mAge;

    public String getmUserName() {
        return mUserName;
    }
}
