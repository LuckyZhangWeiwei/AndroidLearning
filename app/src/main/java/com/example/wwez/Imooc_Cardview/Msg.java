package com.example.wwez.Imooc_Cardview;

public class Msg {
    private int id;
    private int imgResId;

    public Msg() {
    }

    public Msg(int id, int imgResId, String title, String content) {
        this.id = id;
        this.imgResId = imgResId;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String title;
    private String content;
}
