package com.example.wwez.Imooc_Listview;

import java.util.ArrayList;
import java.util.List;

public class LessonResult {

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    private String mMessage;

    public int getmStatus() {
        return mStatus;
    }

    public void setmStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    private int mStatus;

    public List<LessonInfo> getmLessInfos() {
        return mLessInfos;
    }

    public void setmLessInfos(List<LessonInfo> mLessInfos) {
        this.mLessInfos = mLessInfos;
    }

    private List<LessonInfo> mLessInfos;

    public LessonResult() {
        mLessInfos = new ArrayList<>();
    }
}
