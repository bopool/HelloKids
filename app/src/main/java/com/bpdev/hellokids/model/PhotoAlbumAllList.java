package com.bpdev.hellokids.model;

import java.util.ArrayList;

public class PhotoAlbumAllList {

    // 사진첩 목록 보기 API에서 response용으로 쓴다
    private String result;
    private ArrayList<PhotoAlbumAll> items;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ArrayList<PhotoAlbumAll> getItems() {
        return items;
    }

    public void setItems(ArrayList<PhotoAlbumAll> items) {
        this.items = items;
    }
}
